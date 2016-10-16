package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
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
	private JButton edit;
	private JButton create;
	private JButton delete;
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
		
		open = new JButton("Open");
		
		painelBotaoOpen.add(open);
		
		edit = new JButton("Edit");
		
		painelBotaoOpen.add(edit);
		
		create = new JButton("Create New Model");
		
		painelBotaoOpen.add(create);
		
		delete = new JButton("Delete");
		
		painelBotaoOpen.add(delete);

		listarMedidas = new JButton("Comparar Contextos de um modelo");
		
		painelMensagens.add(listarMedidas);
		
		JButton button = new JButton("Comparar Features entre modelos");
		
		painelMensagens.add(button);
		
		JButton button2 = new JButton("Evolução entre contextos");
		
		painelMensagens.add(button2);
		
		JButton button3 = new JButton("Profundidade Máxima da Árvore e Número de Features Folhas dos modelos");
		
		painelMensagens.add(button3);
		
		JButton button4 = new JButton("d3");
		
		painelMensagens.add(button4);

		JButton button5 = new JButton("d3 Tree");
		
		painelMensagens.add(button5);
		
		JButton button6 = new JButton("Nleaf x DTMax (largura x profundidade)");
		
		painelMensagens.add(button6);
		
		JButton button7 = new JButton("FoC");
		
		painelMensagens.add(button7);
		
		JButton button8 = new JButton("FEX");
		
		painelMensagens.add(button8);
		
		medidas = new JButton("Measures");
		
		painelBotaoOpen.add(medidas);
		
		refresh = new JButton("Refresh");
		
		painelBotaoOpen.add(refresh);
		
		mDefaultTableModel = new DefaultTableModel(new String[][]{}, colunas);
		
		tabela = new JTable(mDefaultTableModel);
		JScrollPane barraRolagem = new JScrollPane(tabela);
		
		painelTabela.add(barraRolagem);
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse modelo?");
					if(resp == JOptionPane.YES_OPTION){
						SchemeXml selecionado = listaItens.get(selecao);
						if(controladorXml.delete(selecionado)){
							System.out.println("deleted successful!");
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
		
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.abrirD3(listaItens);
			}
		});
		
		button5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.abrirD3Tree(listaItens);
			}
		});
		
		button6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.abrirD3Bubble(listaItens);
			}
		});
		
		button7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.abrirD3FoC(listaItens);
			}
		});

		button8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewerPanelResultFeatures.this.main.abrirD3FEX(listaItens);
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
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = JOptionPane.showInputDialog("Type the name of feature root:");
				
				if(name== null || name.equals("")){
					JOptionPane.showMessageDialog(null, "Type a name valid");
					return;
				}
					
				main.createModelFeature(name);
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						carregarItens();		
					}
				}).start();	
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
		
		edit.addActionListener(new ActionListener() {
			
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				carregarItens();		
			}
		}).start();	
	}
	
	private void setBotoes(boolean status){
		open.setEnabled(status);
		listarMedidas.setEnabled(status);
		edit.setEnabled(status);
		delete.setEnabled(status);
	}
	
	public synchronized void carregarItens(){
		listaItens = controladorXml.getXml();
		mDefaultTableModel.setRowCount(0);
		if(listaItens!=null){
			if(listaItens.size() > 0){
				for(SchemeXml sc : listaItens){
					mDefaultTableModel.addRow(new String[]{sc.getNameXml()});
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
