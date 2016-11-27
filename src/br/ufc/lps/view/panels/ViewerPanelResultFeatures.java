package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.ufc.lps.controller.xml.ControladorXml;
import br.ufc.lps.repository.SchemeXml;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.view.Main;

public class ViewerPanelResultFeatures extends JPanel {
	
	private static String colunas[] = {"N°","Nome"};
	private DefaultTableModel mDefaultTableModel;
	private ControladorXml controladorXml;
	private List<SchemeXml> listaItens;
	private Main main;
	private JTable tabela;
	private JButton open;
	private JButton edit;
	private JButton create;
	private JButton delete;
	private JButton refresh;
	private JButton medidas;
	private JButton download;
	private JLabel labelMensagens;
	private JLabel loader;
	
	public ViewerPanelResultFeatures(final Main main) {
		
		loader = new JLabel(new ImageIcon("images/ajax-loader.gif"), JLabel.CENTER);
		isShowLoader(false);
		
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
		painelBotaoOpen.setLayout(new GridLayout(8, 0, 0, 0));
		
		open = new JButton("Open");
		
		painelBotaoOpen.add(open);
		
		edit = new JButton("Edit");
		
		painelBotaoOpen.add(edit);
		
		create = new JButton("Create New Model");
		
		painelBotaoOpen.add(create);
		
		delete = new JButton("Delete");
		
		painelBotaoOpen.add(delete);
		
		medidas = new JButton("Measures");
		
		painelBotaoOpen.add(medidas);
		
		refresh = new JButton("Refresh");

		download = new JButton("Download");
		
		painelBotaoOpen.add(download);

		painelBotaoOpen.add(refresh);
		
		painelBotaoOpen.add(loader);
		
		loader.setEnabled(true);

		mDefaultTableModel = new DefaultTableModel(new String[][]{}, colunas);
		
		tabela = new JTable(mDefaultTableModel);
		JScrollPane barraRolagem = new JScrollPane(tabela);
		
		painelTabela.add(barraRolagem);
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					int resp = JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete this model?");
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
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						int [] selecao = tabela.getSelectedRows();
						if(selecao.length > 0 && selecao.length < 41){
							for(int i=0; i < selecao.length; i++){
								SchemeXml selecionado = listaItens.get(selecao[i]);
								File file = ControladorXml.createFileFromXml(selecionado.getXml());
								selecionado.setFile(file);
								main.abrirArquivosDoRepositorio(selecionado);
							}
						}else
							JOptionPane.showMessageDialog(null, "Select an appropriate range of models in the table (Up to 40 at a time)");
					}
				}).start();
			
			}
		});
		
		download.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					File file = ControladorXml.createFileFromXml(selecionado.getXml());
					
						JFileChooser chooser = new JFileChooser(); 
					   	chooser.setCurrentDirectory(new java.io.File("."));
					    chooser.setDialogTitle("Select the path");
					    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					    
					    chooser.setAcceptAllFileFilterUsed(false);
					    
					    if (chooser.showOpenDialog(ViewerPanelResultFeatures.this) == JFileChooser.APPROVE_OPTION) { 
					      System.out.println("getCurrentDirectory(): " 
					         +  chooser.getCurrentDirectory());
					      System.out.println("getSelectedFile() : " 
					         +  chooser.getSelectedFile());
					      
					    
					    	String nomeArquivo = JOptionPane.showInputDialog("Type the name of File", selecionado.getNameXml());
					    
					    	File file2 = new File(chooser.getSelectedFile()+"/"+nomeArquivo+".xml");
					    	

							saveInLocalFile( file.getAbsolutePath(), file2);
					    
						}else {
					      System.out.println("No Selection ");
					    }
					
					
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
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						int [] selecao = tabela.getSelectedRows();
						if(selecao.length > 0 && selecao.length < 41){
							for(int i=0; i < selecao.length; i++){
								SchemeXml selecionado = listaItens.get(selecao[i]);
								File file = ControladorXml.createFileFromXml(selecionado.getXml());
								selecionado.setFile(file);
								main.editarArquivosDoRepositorio(selecionado);
							}
						}else
							JOptionPane.showMessageDialog(null, "Select an appropriate range of models in the table (Up to 40 at a time)");
					}
				}).start();
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
		edit.setEnabled(status);
		delete.setEnabled(status);
		medidas.setEnabled(status);
	}
	
	private int getWidthByNumber(Integer count){
		String numero = count.toString();
		System.out.println(numero.length());
		return numero.length()*18;
	}
	
	public void isShowLoader(boolean val){
		loader.setVisible(val);
	}
	
	public synchronized void carregarItens(){
		isShowLoader(true);
		listaItens = controladorXml.getXml();
		mDefaultTableModel.setRowCount(0);
		int count = 1;
		if(listaItens!=null){
			if(listaItens.size() > 0){
				for(SchemeXml sc : listaItens){
					mDefaultTableModel.addRow(new String[]{(count++)+"-", sc.getNameXml()});
				}
					setBotoes(true);
			} else {
				setBotoes(false);
			}
			tabela.getColumnModel().getColumn(0).setMaxWidth(getWidthByNumber(count));
		}else{
			labelMensagens.setText("There was a problem connecting");
			JOptionPane.showMessageDialog(null, "There was a problem connecting");
		}
		isShowLoader(false);
	}
	
	public List<SchemeXml> getAllSelectedItensList(){
		
		int [] selecao = tabela.getSelectedRows();
		
		if(selecao.length >= 2){
			List<SchemeXml> list = new ArrayList<>();
			for(int i=0; i < selecao.length; i++)
				list.add(listaItens.get(selecao[i]));
			return list;
		}
		
		return null;
	}
	
	public List<SchemeXml> getAllSelectedItensPriorityList(){
		
		int [] selecao = tabela.getSelectedRows();
		
		if(selecao.length >= 2){
			List<SchemeXml> list = new ArrayList<>();
			for(int i=0; i < selecao.length; i++)
				list.add(listaItens.get(selecao[i]));
			return list;
		}
		return null;
	}
	
	public List<SchemeXml> getAllItensList(){
		return listaItens;
	}
	
	public SchemeXml getOneItemList(){
		int selecao = tabela.getSelectedRow();
		if(selecao > -1){
			SchemeXml selecionado = listaItens.get(selecao);
			return selecionado;
		}
		
		return null;
	}
	
	private void mensagemSelecionarLinha(){
		JOptionPane.showMessageDialog(null, "Select a model in the table");
	}
	
	private void saveInLocalFile(String path, File file){
		
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(path);
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			
			StreamResult console = new StreamResult(new FileOutputStream(file));
			transformer.transform(source, console);

			JOptionPane.showMessageDialog(ViewerPanelResultFeatures.this,
					"Save Successfuly");

		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerFactoryConfigurationError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
