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
	
	public ViewerPanelResultFeatures(final ContextModel model, final Main main) {
		
		controladorXml = new ControladorXml();
		this.model = model;
		this.main = main;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel painelTabela = new JPanel();
		add(painelTabela, BorderLayout.CENTER);
		painelTabela.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel painelOpcoes = new JPanel();
		add(painelOpcoes, BorderLayout.EAST);
		painelOpcoes.setLayout(new GridLayout(0, 1, 0, 0));
		JButton open = new JButton("Abrir");
	
		painelOpcoes.add(open);
		
		mDefaultTableModel = new DefaultTableModel(new String[][]{}, colunas);
		
		JTable tabela = new JTable(mDefaultTableModel);
		JScrollPane barraRolagem = new JScrollPane(tabela);
		
		painelTabela.add(barraRolagem);
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selecao = tabela.getSelectedRow();
				if(selecao > -1){
					SchemeXml selecionado = listaItens.get(selecao);
					File file = ControladorXml.createFileFromXml(selecionado.getXml());
					main.abrirArquivosDoRepositorio(file);
				}
			}
		});
	
		//salvarItem(null);
		carregarItens();			
	}
	
	private void carregarItens(){
		listaItens = controladorXml.get();
		
		if(listaItens!=null)
			for(SchemeXml sc : listaItens){
				mDefaultTableModel.addRow(new String[]{sc.get_id()});
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
	
	private void salvarItem(File file){
		SchemeXml schemeXml = new SchemeXml();
		controladorXml.save(schemeXml);
	}
	
	public static void main(String[] args) {
		
		JFrame a = new JFrame();
		a.setLocationRelativeTo(null);
		a.setSize(500, 500);
		a.add(new ViewerPanelResultFeatures(null, null));
		a.setVisible(true);
		
	}

}
