package br.ufc.lps.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.normal.SplotModel;
import br.ufc.lps.repositorio.ClientHttp;
import br.ufc.lps.repositorio.ControladorXml;
import br.ufc.lps.repositorio.SchemeXml;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ViewerPanelResultFeatures extends JPanel {
	
	private List<SchemeXml> modelos;
	private ContextModel model;
	private static String colunas[] = {"Nome"};
	private DefaultTableModel mDefaultTableModel;
	private ControladorXml controladorXml;
	private List<SchemeXml> listaItens;
	private Main main;
	private JTable tabela;
	private JButton open;
	private JButton editar;
	private JButton deletar;
	private JButton listarMedidas;
	private JButton refresh;
	private JLabel labelMensagens;
	
	public ViewerPanelResultFeatures(final ContextModel model, final Main main) {
		
		controladorXml = new ControladorXml();
		this.model = model;
		this.main = main;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		//TABELA
		JPanel painelTabela = new JPanel();
		add(painelTabela, BorderLayout.CENTER);
		painelTabela.setLayout(new GridLayout(0, 1, 0, 0));
		
		//TABELA
		JPanel painelMensagens = new JPanel();
		add(painelMensagens, BorderLayout.SOUTH);
		painelMensagens.setLayout(new GridLayout(0, 1, 0, 0));
		
		labelMensagens = new JLabel();
		painelMensagens.add(labelMensagens);
		
		//Painel de opções
		JPanel painelOpcoes = new JPanel();
		add(painelOpcoes, BorderLayout.EAST);
		painelOpcoes.setLayout(new GridLayout(0, 1, 0, 0));

		//BOTAO ABRIR
		JPanel painelBotaoOpen = new JPanel();
		painelOpcoes.add(painelBotaoOpen, BorderLayout.NORTH);
		painelBotaoOpen.setLayout(new GridLayout(7, 0, 0, 0));
		
		open = new JButton("Abrir");
		
		painelBotaoOpen.add(open);
		
		editar = new JButton("Editar");
		
		painelBotaoOpen.add(editar);
		
		deletar = new JButton("Deletar");
		
		painelBotaoOpen.add(deletar);

		listarMedidas = new JButton("Comparar Contextos de um modelo");
		
		painelBotaoOpen.add(listarMedidas);
		
		JButton button = new JButton("Comparar Features entre modelos");
		
		painelBotaoOpen.add(button);
		
		JButton button2 = new JButton("Evolucao entre contextos");
		
		painelBotaoOpen.add(button2);
		
		refresh = new JButton("Recarregar");
		
		painelBotaoOpen.add(refresh);
		
		mDefaultTableModel = new DefaultTableModel(new String[][]{}, colunas);
		
		tabela = new JTable(mDefaultTableModel);
		JScrollPane barraRolagem = new JScrollPane(tabela);
		
		painelTabela.add(barraRolagem);
		
		deletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					if(controladorXml.delete(selecionado)){
						System.out.println("deletado com sucesso!");
						carregarItens();
					}
				}
			}
		});
		
		listarMedidas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					ViewerPanelResultFeatures.this.main.iniciarCampos3(selecionado);
				}
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.iniciarCampos2(listaItens);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					ViewerPanelResultFeatures.this.main.iniciarCampos4(selecionado);
				}
			}
		});
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					File file = ControladorXml.createFileFromXml(selecionado.getXml());
					selecionado.setFile(file);
					main.abrirArquivosDoRepositorio(selecionado);
				}
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				carregarItens();
			}
		});
		
		editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					File file = ControladorXml.createFileFromXml(selecionado.getXml());
					selecionado.setFile(file);
					main.editarArquivosDoRepositorio(selecionado);
				}
			}
		});
	
		carregarItens();			
	}
	
	private void setBotoes(boolean status){
		open.setEnabled(status);
		listarMedidas.setEnabled(status);
		editar.setEnabled(status);
		deletar.setEnabled(status);
	}
	
	private void carregarItens(){
		listaItens = controladorXml.get();
		mDefaultTableModel.setRowCount(0);
		if(listaItens!=null){
			if(listaItens.size() > 0){
				for(SchemeXml sc : listaItens){
					File file = ControladorXml.createFileFromXml(sc.getXml());
					IModel model = new SplotModel(file.getAbsolutePath());
					mDefaultTableModel.addRow(new String[]{model.getModelName()});
				}
					setBotoes(true);
			} else {
				setBotoes(false);
			}
		}else{
			labelMensagens.setText("Ocorreu algum problema na conexão");
		}
				
	}
}
