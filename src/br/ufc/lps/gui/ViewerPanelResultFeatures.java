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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.ufc.lps.conexao.ClientHttp;
import br.ufc.lps.conexao.ControladorXml;
import br.ufc.lps.conexao.SchemeXml;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.normal.SplotModel;
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
		
		//BOTAO ABRIR
		JPanel painelOpcoes = new JPanel();
		add(painelOpcoes, BorderLayout.EAST);
		painelOpcoes.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel painelBotaoOpen = new JPanel();
		painelOpcoes.add(painelBotaoOpen, BorderLayout.NORTH);
		painelBotaoOpen.setLayout(new GridLayout(3, 0, 0, 0));
		
		JButton open = new JButton("Abrir");
		
		painelBotaoOpen.add(open);
		
		JButton editar = new JButton("Editar");
		
		painelBotaoOpen.add(editar);
	
		JButton refresh = new JButton("Recarregar");
		
		painelBotaoOpen.add(refresh);
		
		mDefaultTableModel = new DefaultTableModel(new String[][]{}, colunas);
		
		tabela = new JTable(mDefaultTableModel);
		JScrollPane barraRolagem = new JScrollPane(tabela);
		
		painelTabela.add(barraRolagem);
		
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
	
		//salvarItem(null);
		carregarItens();			
	}
	
	private void carregarItens(){
		listaItens = controladorXml.get();
		mDefaultTableModel.setRowCount(0);
		if(listaItens!=null)
			for(SchemeXml sc : listaItens){
				File file = ControladorXml.createFileFromXml(sc.getXml());
				IModel model = new SplotModel(file.getAbsolutePath());
				mDefaultTableModel.addRow(new String[]{model.getModelName()});
			}
		
	}
		
		/*
		String xml = modelos.getLista().get(0).getXml();
		
		File file = File.createTempFile("feature", ".xml");
	    
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	    bw.write(xml);
	    bw.close();
	    
	    file.getAbsolutePath();
		*/
	
	
	public static void main(String[] args) {
		
		JFrame a = new JFrame();
		a.setLocationRelativeTo(null);
		a.setSize(500, 500);
		a.add(new ViewerPanelResultFeatures(null, null));
		a.setVisible(true);
		
	}

}
