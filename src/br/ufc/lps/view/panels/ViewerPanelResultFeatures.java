package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.ufc.lps.controller.xml.ControladorXml;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.normal.SplotModel;
import br.ufc.lps.repositorio.SchemeXml;
import br.ufc.lps.view.Main;

public class ViewerPanelResultFeatures extends JPanel {
	
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
	private JButton medidas;
	private JLabel labelMensagens;
	
	public ViewerPanelResultFeatures(final Main main) {
		
		controladorXml = new ControladorXml();
		this.main = main;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		//TABELA
		JPanel painelTabela = new JPanel();
		add(painelTabela, BorderLayout.CENTER);
		painelTabela.setLayout(new GridLayout(0, 1, 0, 0));
		
		//MENSAGENS
		JPanel painelMensagens = new JPanel();
		add(painelMensagens, BorderLayout.SOUTH);
		painelMensagens.setLayout(new GridLayout(2, 1, 0, 0));
		
		labelMensagens = new JLabel();
		//painelMensagens.add(labelMensagens);
		
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
		
		painelMensagens.add(listarMedidas);
		
		JButton button = new JButton("Comparar Features entre modelos");
		
		painelMensagens.add(button);
		
		JButton button2 = new JButton("Evolução entre contextos");
		
		painelMensagens.add(button2);
		
		JButton button3 = new JButton("Profundidade Máxima da Árvore e Número de Features Folhas dos modelos");
		
		painelMensagens.add(button3);
		
		medidas = new JButton("Medidas");
		
		painelBotaoOpen.add(medidas);
		
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
					int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse modelo?");
					if(resp == JOptionPane.YES_OPTION){
						SchemeXml selecionado = listaItens.get(selecao);
						if(controladorXml.delete(selecionado)){
							System.out.println("deletado com sucesso!");
							carregarItens();
						}
					}
				}else
					mensagemSelecionarLinha();
			}
		});
		
		listarMedidas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					ViewerPanelResultFeatures.this.main.ComparacaoContextos(selecionado);
				}else
					mensagemSelecionarLinha();
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.numeroDeFeatures(listaItens);
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					ViewerPanelResultFeatures.this.main.comparacaoContextosLine(selecionado);
				}else
					mensagemSelecionarLinha();
			}
		});
		
		button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.bubble(listaItens);
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
				}else
					mensagemSelecionarLinha();
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				carregarItens();
			}
		});
		
		medidas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					main.abrirMedidas(selecionado);
				}else
					mensagemSelecionarLinha();
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
				}else
					mensagemSelecionarLinha();
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
	
	public void carregarItens(){
		listaItens = controladorXml.getXml();
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
			JOptionPane.showMessageDialog(null, "Ocorreu algum problema na conexão");
		}
				
	}
	
	private void mensagemSelecionarLinha(){
		JOptionPane.showMessageDialog(null, "Selecione um modelo na tabela");
	}
}
