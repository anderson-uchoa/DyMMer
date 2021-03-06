package br.ufc.lps.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.ufc.lps.controller.browser.BrowserController;
import br.ufc.lps.controller.export.ExportOfficeExcel;
import br.ufc.lps.model.context.SplotContextModel;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.normal.SplotModel;
import br.ufc.lps.model.xml.ModelID;
import br.ufc.lps.model.xml.XMLFamiliarModel;
import br.ufc.lps.model.xml.XMLSplotModel;
import br.ufc.lps.repository.SchemeXml;
import br.ufc.lps.view.panels.CreatorPanel;
import br.ufc.lps.view.panels.EditorPanel;
import br.ufc.lps.view.panels.ViewerPanel;
import br.ufc.lps.view.panels.ViewerPanelResultFeatures;
import br.ufc.lps.view.panels.ViewerPanelResultMeasures;

public class Main extends JFrame {

	private JTabbedPane tabbedPane;
	private ViewerPanel currentViewer;
	private ViewerPanel currentViewerPreview;
	private JMenu mnMeasures_1;
	private JMenu mnVisualizations;
	private ViewerPanelResultFeatures viewMain;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				    
					NimbusLookAndFeel laf = new NimbusLookAndFeel();
					
					UIDefaults defs = laf.getDefaults();
					defs.put("Tree.drawHorizontalLines", true);
					defs.put("Tree.drawVerticalLines", true);
					defs.put("Tree.linesStyle", "Angled");
					
					Main frame = new Main();
					frame.setVisible(true);

					try {
					    UIManager.setLookAndFeel(laf);
					} catch (UnsupportedLookAndFeelException e) {
					    //Error handling code
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		
		initXMLmodels();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		setLocationRelativeTo(null);
		setTitle("DyMMer");
		setIconImage(new ImageIcon("images/icone.png").getImage());
		
		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        if(index != -1) {
					Component componentAt = sourceTabbedPane.getComponentAt(index);
					if(componentAt instanceof ViewerPanel){
						currentViewer = (ViewerPanel) componentAt;
						mnMeasures_1.setEnabled(true);
					}else{
						mnMeasures_1.setEnabled(false);
					}
		        }
				
			}
		});
		
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnMeasures_1 = new JMenu("Measures");
		final JMenu mnFile = new JMenu("File");
		mnVisualizations = new JMenu("Visualizations");
		
		menuBar.add(mnFile);

		JMenuItem mntmOpenSplotxml = new JMenuItem("Open SPLOT .xml");
		mntmOpenSplotxml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return null;
					}
					
					@Override
					public boolean accept(File f) {
						
						if(f.isDirectory())
							return true;
						
						String fileName = f.getName();
						
						int firstIdentifier = fileName.lastIndexOf('.');
						
						if( firstIdentifier > -1 && firstIdentifier < fileName.length() - 1)
							if(fileName.substring(firstIdentifier+1).equalsIgnoreCase("xml"))
								return true;
						
						return false;
					}
				});
				
				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){
					
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					
					final ViewerPanel viewer = new ViewerPanel(new SplotContextModel(path), fileChooser.getSelectedFile(), null, Main.this, false);
					
					currentViewer = viewer;
					createTab(viewer, viewer.getModelName());
				}
			}
		});
		
		
		mnFile.add(mntmOpenSplotxml);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenu mnNewMenu = new JMenu("Export");
		mnFile.add(mnNewMenu);
		
		JMenuItem mntmExportToOffice = new JMenuItem("Export to Office Excel Without Contexts");
		mntmExportToOffice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public boolean accept(File f) {
						
						if(f.isDirectory())
							return true;
						
						String fileName = f.getName();
						
						int firstIdentifier = fileName.lastIndexOf('.');
						
						if( firstIdentifier > -1 && firstIdentifier < fileName.length() - 1)
							if(fileName.substring(firstIdentifier+1).equalsIgnoreCase("xml"))
								return true;
						
						return false;
					}
				});
				
				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){
					
					File files[] = fileChooser.getSelectedFiles();
					if(files.length > 500){
						JOptionPane.showMessageDialog(Main.this, "Please, do not choose more than 50 model files.", "Exceeded!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					ExportOfficeExcel exportExcel = new ExportOfficeExcel(files);
					
					String excelFileName = (String)JOptionPane.showInputDialog("Type the file name");
					
					if(excelFileName == null || excelFileName.isEmpty())
						excelFileName = "exportedExcelFile_" + System.currentTimeMillis();
					
					exportExcel.standardExport(excelFileName);
					
					
				}
				
			}
		});
		mnNewMenu.add(mntmExportToOffice);
		
		JMenuItem mntmExportToOffice_1 = new JMenuItem("Export to Office Excel With Contexts");
		mntmExportToOffice_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public boolean accept(File f) {
						
						if(f.isDirectory())
							return true;
						
						String fileName = f.getName();
						
						int firstIdentifier = fileName.lastIndexOf('.');
						
						if( firstIdentifier > -1 && firstIdentifier < fileName.length() - 1)
							if(fileName.substring(firstIdentifier+1).equalsIgnoreCase("xml"))
								return true;
						
						return false;
					}
				});
				
				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){
					
					String filePath = fileChooser.getSelectedFile().getAbsolutePath();
													
					ExportOfficeExcel exportExcel = new ExportOfficeExcel(new File[]{new File(filePath)});
					
					String excelFileName = (String)JOptionPane.showInputDialog("Type the file name");
					
					if(excelFileName == null || excelFileName.isEmpty())
						excelFileName = "exportedExcelFile_" + System.currentTimeMillis();
					
					exportExcel.exportWithContexts(excelFileName);
					
					
				}
				
			}
		});
		mnNewMenu.add(mntmExportToOffice_1);

		
		menuBar.add(mnMeasures_1);
		mnMeasures_1.setEnabled(false);

		constructMeasuresMenuItem(mnMeasures_1);

		JMenu mnEditor = new JMenu("Editor");
		menuBar.add(mnEditor);
		
		menuBar.add(mnVisualizations);
		constructVisualizationsMenu(mnVisualizations);
		
		JMenuItem mntmEditSplotModel = new JMenuItem("Open SPLOT's xml");
		mnEditor.add(mntmEditSplotModel);
		
		mntmEditSplotModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return null;
					}
					
					@Override
					public boolean accept(File f) {
						
						if(f.isDirectory())
							return true;
						
						String fileName = f.getName();
						
						int firstIdentifier = fileName.lastIndexOf('.');
						
						if( firstIdentifier > -1 && firstIdentifier < fileName.length() - 1)
							if(fileName.substring(firstIdentifier+1).equalsIgnoreCase("xml"))
								return true;
						
						return false;
					}
				});
				
				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION){
					
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					IModel model = new SplotModel(path);
					final EditorPanel editor = new EditorPanel(model, ModelID.SPLOT_MODEL.getId(), path, null, Main.this);
					
					createTab(editor, editor.getModelName());
				}
			}
		});
		
		iniciarMainViewPanel();
		
	}
	
	public void abrirArquivosDoRepositorio(SchemeXml schemeXml){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				viewMain.isShowLoader(true);
				
				String path = schemeXml.getFile().getAbsolutePath();
				final ViewerPanel viewer = new ViewerPanel(new SplotContextModel(path), null, schemeXml, Main.this, false);
				
				currentViewer = viewer;
				
				createTab(viewer, schemeXml.getNameXml());
				
				viewMain.isShowLoader(false);
			}
		}).start();
	
		
	}
	
	public void abrirPreview(String path){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				viewMain.isShowLoader(true);
				
				File file = new File(path);
				
				final ViewerPanel viewer = new ViewerPanel(new SplotContextModel(path), file, null, Main.this, true);
				currentViewerPreview = viewer;
				
				createTab(currentViewerPreview, "Preview");
				viewMain.isShowLoader(false);
			}
		}).start();
	}
	
	public void createModelFeature(String nameRoot){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				viewMain.isShowLoader(true);
				final CreatorPanel viewer = new CreatorPanel(ModelID.SPLOT_MODEL.getId(), Main.this, nameRoot);
				
				createTab(viewer,  "Creating new Model");
				viewMain.isShowLoader(false);
			}
		}).start();
	}
	
	private void createTab(Component panel, String nameTab){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mnMeasures_1.setEnabled(true);
			
				long time = System.currentTimeMillis();
				
				tabbedPane.addTab(nameTab+time, panel);	
				
				int index = tabbedPane.indexOfTab(nameTab+time);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(nameTab);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				tabbedPane.setTabComponentAt(index, pnlTab);

				btnClose.addActionListener(new MyCloseActionHandler(nameTab+time));
				
				Main.this.repaint();
			}
		});
	}
	
	public void editarArquivosDoRepositorio(SchemeXml schemeXml){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				viewMain.isShowLoader(true);
				String path = schemeXml.getFile().getAbsolutePath();
				final EditorPanel viewer = new EditorPanel(new SplotModel(path), ModelID.SPLOT_MODEL.getId(), path, schemeXml, Main.this);
				
				createTab(viewer, schemeXml.getNameXml());				
				viewMain.isShowLoader(false);
			}
		}).start();
		
		
	}
	
	public void recarregarListaFeatures(){
		if(viewMain!=null){
			viewMain.carregarItens();
		}
	}
	
	private void iniciarMainViewPanel(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mnMeasures_1.setEnabled(true);
				String tabName = "Models Repository";
				long time = System.currentTimeMillis();
				
				ViewerPanelResultFeatures a = new ViewerPanelResultFeatures(Main.this);
				viewMain = a;
				tabbedPane.addTab(tabName+time, a);	
				
				int index = tabbedPane.indexOfTab(tabName+time);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;

				tabbedPane.setTabComponentAt(index, pnlTab);
			}
		});

	}
	
	public void numeroDeFeatures( List<SchemeXml>  lista){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getPie(lista);
				createTab(a, "Número de Features");
			}
		}).start();
	}

	public void bubble( List<SchemeXml>  lista){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getBubble(lista);
				createTab(a, "RoV x NVC (Variabilidade estática)");
			}
		}).start();
	}

	public void ComparacaoContextos( SchemeXml  schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getBar(schema);
				createTab(a, "Comparação Contextos Radar");
			}
		}).start();
	}

	public void comparacaoContextosLine( SchemeXml schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getLine(schema);
				createTab(a, "Comparação Contextos Line");
			}
		}).start();
	}
	
	public void variabilidadeDinamica( SchemeXml schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getD3TreeDynamicVariabilidade(schema);
					createTab(a, "Dynamic Variability");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getRadarVariabilidadeAdaptativa( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getRadarVariabilidadeAdaptativa(schema);
					createTab(a, "Structural Complexity");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void abrirMedidas( SchemeXml schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = new ViewerPanelResultMeasures(Main.this, schema);
				createTab(a, "Medidas das Features");
			}
		}).start();
	}
	
	public void abrirD3( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getD3(schema);
				createTab(a, "Nleaf x DTMax (largura x profundidade)");
			}
		}).start();
	}
	
	public void abrirD3Tree( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getD3Tree(schema);
				createTab(a, "Nleaf x DTMax (largura x profundidade) Tree");
			}
		}).start();
	}
	
	public void abrirD3Bubble( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getD3Bubble(schema);
				createTab(a, "Nleaf x DTMax (largura x profundidade)");
			}
		}).start();
	}
	
	public void abrirD3BubbleRoVxNVC( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getD3BubbleRoVxNVC(schema);
				createTab(a, "RoV x NVC (Variabilidade estática)");
			}
		}).start();
	}
	
	public void abrirD3FoC( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getD3FoC(schema);
				createTab(a, "FoC (Flexibilidade)");
			}
		}).start();
	}
	
	public void abrirD3FEX( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getD3FEX(schema);
					createTab(a, "FEX (Extensibility of Model)");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getD3TreeMapEvolucao( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getD3TreeMapEvolucao(schema);
					createTab(a, "NF / NA / NO / NM (tamanho do modelo)");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getLineStaticVariability( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getLineStaticVariability(schema);
					createTab(a, "Static Variability");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getImpactoLinha( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getBarNumberImpactoLinha(schema);
					createTab(a, "Number of Configurations");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getRadarImpactoQ2( List<SchemeXml> schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getRadarImpactoQ2(schema);
					createTab(a, "Impact on Structural Complexity");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getRadarComplexity( SchemeXml schema){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(schema!=null){
					viewMain.isShowLoader(true);
					Component a = BrowserController.getBarComplexity(schema);
					createTab(a, "Structural Complexity of Model");
					viewMain.isShowLoader(false);
				}
			}
		}).start();
	}
	
	public void getAnd(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Component a = BrowserController.getAnd();
				createTab(a, "Arvore And");
			}
		}).start();
	}

	private void initXMLmodels() {
		new XMLSplotModel();
		new XMLFamiliarModel();
	}
	
	public void expandAllNodes(JTree tree, int startingIndex, int rowCount){
		for(int i=startingIndex;i<rowCount;++i)
	        tree.expandRow(i);

	    if(tree.getRowCount()!=rowCount)
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	}
	
	public class MyCloseActionHandler implements ActionListener {

	    private String tabName;

	    public MyCloseActionHandler(String tabName) {
	        this.tabName = tabName;
	    }

	    public String getTabName() {
	        return tabName;
	    }

	    public void actionPerformed(ActionEvent evt) {

	        int index = tabbedPane.indexOfTab(getTabName());
	        if (index >= 0) {
	            tabbedPane.removeTabAt(index);
	        }
	    }
	}   
	
	private void constructVisualizationsMenu(JMenu visualization){
		
		JMenu oneModel = new JMenu("For Single Model");
		JMenu selectedModels = new JMenu("For Selected Models");
		JMenu allModels = new JMenu("For All Models");
		
		visualization.add(oneModel);
		visualization.add(selectedModels);
		visualization.add(allModels);
		
		JMenuItem CompararContextosDeUmModelo = new JMenuItem("Comparar Contextos de um modelo");
		
		CompararContextosDeUmModelo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				System.out.println(s);
				if(s!=null){
					Main.this.ComparacaoContextos(s);
				}
			}
		});
		
		//visualization.add(CompararContextosDeUmModelo);
		
		JMenuItem CompararFeaturesEntreModelos = new JMenuItem("Comparar Features entre modelos");
		
		CompararFeaturesEntreModelos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.numeroDeFeatures(s);
			}
		});
		
		//visualization.add(CompararFeaturesEntreModelos);
		
		JMenuItem EvolucaoDeContextos = new JMenuItem("Evolução entre contextos");
		
		EvolucaoDeContextos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				System.out.println(s);
				if(s!=null){
					Main.this.comparacaoContextosLine(s);
				}
			}
		});
		
		//visualization.add(EvolucaoDeContextos);
		
		JMenuItem RoVxNVC = new JMenuItem("RoV x NVC (Variabilidade estática)");
		
		RoVxNVC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.bubble(s);
			}
		});
		
		//visualization.add(RoVxNVC);

		/*
	    JMenuItem NLeafxDTMaxTree = new JMenuItem("Nleaf x DTMax (largura x profundidade) Tree");
		
		NLeafxDTMaxTree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.abrirD3Tree(s);
			}
		});
		
		
		visualization.add(NLeafxDTMaxTree);
		*/
		
		JMenuItem NLeafxDTMax = new JMenuItem("Nleaf x DTMax (largura x profundidade)");
		
		NLeafxDTMax.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.abrirD3Bubble(s);
			}
		});
		
		//visualization.add(NLeafxDTMax);
		
		/*JMenuItem FoC = new JMenuItem("FoC");
		
		FoC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.abrirD3FoC(s);
			}
		});
		
		visualization.add(FoC);
		*/
		
		/*JMenuItem FEX = new JMenuItem("FEX");
		
		FEX.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.abrirD3FEX(s);
			}
		});
		
		visualization.add(FEX);
		*/
		
		JMenuItem TamanhoDoModelo = new JMenuItem("NF / NA / NO / NM (tamanho do modelo)");
		
		TamanhoDoModelo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllSelectedItensList();
				System.out.println(s);
				if(s!=null){
					Main.this.getD3TreeMapEvolucao(s);
				}
			}
		});
		
		//visualization.add(TamanhoDoModelo);
		
		JMenuItem VariabilidadeAdaptativa = new JMenuItem("Variabilidade Adaptativa");
		
		VariabilidadeAdaptativa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				System.out.println(s);
				if(s!=null){
					Main.this.variabilidadeDinamica(s);
				}
			}
		});
		
		//visualization.add(VariabilidadeAdaptativa);
		
		JMenuItem And = new JMenuItem("And");
		
		And.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.getAnd();
			}
		});
		
		visualization.add(And);
	
		
		/*JMenuItem novc = new JMenuItem("Number of Configurations");
		
		novc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllSelectedItensList();
				Main.this.getD3TreeNumberOfConfigurations(s);
			}
		});
		
		visualization.add(novc);
		*/
		
		/*JMenuItem cofm = new JMenuItem("Complexity of Features Model");
		
		cofm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				Main.this.getRadarComplexity(s);
			}
		});
		
		visualization.add(cofm);
		*/
		
		JMenuItem p2 = new JMenuItem("Qual o impacto a linha de produtos sofrerá quando uma mudança ocorre no modelo de features?");
		
		p2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listItens list = new listItens() {
					
					@Override
					public void ready(List<SchemeXml> lista) {
						Main.this.getRadarImpactoQ2(lista);
					}
				};
				viewMain.getAllSelectedItensPriorityList(list);
			}
		});
		
		selectedModels.add(p2);
		
		JMenuItem p9 = new JMenuItem("Qual o impacto que a linha sofreria ao extender uma determinada feature?");
		
		p9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listItens list = new listItens() {
					
					@Override
					public void ready(List<SchemeXml> lista) {
						Main.this.getRadarImpactoQ2(lista);
					}
				};
				
				viewMain.getAllSelectedItensPriorityList(list);
			}
		});
		
		selectedModels.add(p9);
		
		JMenuItem p10 = new JMenuItem("Qual o aumento no número de configurações a partir da inclusão de novas features?");
		
		p10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listItens list = new listItens() {
					
					@Override
					public void ready(List<SchemeXml> lista) {
						Main.this.getLineStaticVariability(lista);
					}
				};
				viewMain.getAllSelectedItensPriorityList(list);
			}
		});
		
		selectedModels.add(p10);
		
		JMenuItem p11 = new JMenuItem("Qual a evolução da complexidade do modelo de features quando uma feature é adicionada ou removida?");
		
		p11.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listItens list = new listItens() {
					
					@Override
					public void ready(List<SchemeXml> lista) {
						Main.this.getRadarImpactoQ2(lista);
					}
				};
				viewMain.getAllSelectedItensPriorityList(list);
			}
		});
		
		selectedModels.add(p11);
		
		JMenuItem p15 = new JMenuItem("Qual dos contextos presentes em um modelo de features, possui mais dinamicidade em termos de ativação e desativação?");
		
		p15.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				Main.this.variabilidadeDinamica(s);
			}
		});
		
		oneModel.add(p15);
		
		JMenuItem p16 = new JMenuItem("Qual a complexidade do modelo de features?");
		
		p16.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				Main.this.getRadarComplexity(s);
			}
		});
		
		oneModel.add(p16);
		
		JMenuItem p17 = new JMenuItem("Qual a variabilidade dinâmica do modelo de features?");
		
		p17.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SchemeXml s = viewMain.getOneItemList();
				Main.this.variabilidadeDinamica(s);
			}
		});
		
		oneModel.add(p17);
		
		JMenuItem p18 = new JMenuItem("O modelo de features é de fácil extensibilidade?");
		
		p18.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SchemeXml> s = viewMain.getAllItensList();
				Main.this.abrirD3FEX(s);
			}
		});
		
		allModels.add(p18);
	}


	private void constructMeasuresMenuItem(final JMenu mnMeasures) {
		
		JMenu mnNas = new JMenu("No specific to context");
		mnMeasures_1.add(mnNas);
		
		// Itens for the label "No specific to context" 
		JMenuItem mntmnumberOfFeatures = new JMenuItem("Number of Features");
		mnNas.add(mntmnumberOfFeatures);
		
		JMenuItem mntmNumberofOptionalFeatures = new JMenuItem("Number of Optional Features");
		mnNas.add(mntmNumberofOptionalFeatures);
		
		JMenuItem mntmNumberOfMandatoryFeatures = new JMenuItem("Number of Mandatory Features");
		mnNas.add(mntmNumberOfMandatoryFeatures);
		
		JMenuItem mntmNumberOfTop = new JMenuItem("Number of Top features");
		mnNas.add(mntmNumberOfTop);
		
		JMenuItem mntmNumberOfLeaf = new JMenuItem("Number of Leaf Features");
		mnNas.add(mntmNumberOfLeaf);
		
		JMenuItem mntmDepthOfTree = new JMenuItem("Depth of tree Max");
		mnNas.add(mntmDepthOfTree);
		
		JMenuItem mntmMedianDepthOfTree = new JMenuItem("Depth of tree Median");
		mnNas.add(mntmMedianDepthOfTree);
		
		JMenuItem mntmCognitiveComplexityOf = new JMenuItem("Cognitive Complexity of a Feature Model");
		mnNas.add(mntmCognitiveComplexityOf);
	
		JMenuItem mntmFlexibilityOfConguration = new JMenuItem("Flexibility of con\uFB01guration");
		mnNas.add(mntmFlexibilityOfConguration);
		
		JMenuItem mntmSingleCyclicDependent = new JMenuItem("Single Cyclic Dependent Features");
		mnNas.add(mntmSingleCyclicDependent);
	
		JMenuItem mntmMultipleCyclicDependent = new JMenuItem("Multiple Cyclic Dependent Features");
		mnNas.add(mntmMultipleCyclicDependent);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Feature Extendibility");
		mnNas.add(mntmNewMenuItem);
	
		JMenuItem mntmCyclomaticComplexity = new JMenuItem("Cyclomatic complexity");
		mnNas.add(mntmCyclomaticComplexity);
	
		JMenuItem mntmVariableCrosstreeConstraintsRate = new JMenuItem("Variable Cross tree Constraints");
		mnNas.add(mntmVariableCrosstreeConstraintsRate);
		
		JMenuItem mntmCompoundComplexity = new JMenuItem("Compound Complexity");
		mnNas.add(mntmCompoundComplexity);
		
		JMenuItem mntmGroupingFeatures = new JMenuItem("Number of Grouping Features");
		mnNas.add(mntmGroupingFeatures);
		
		JMenuItem mntmCrosstreeConstraintsRate = new JMenuItem("Cross-tree constraints Rate");
		mnNas.add(mntmCrosstreeConstraintsRate);
		
		JMenuItem mntmCoefcientOfConnectivitydensity = new JMenuItem("Coeficient of Connectivity-Density");
		mnNas.add(mntmCoefcientOfConnectivitydensity);
		
		JMenuItem mntmNumberOfVariable = new JMenuItem("Number of variable features");
		mnNas.add(mntmNumberOfVariable);
		
		JMenuItem mntmSingleHotspotFeatures = new JMenuItem("Single Variation Points Features");
		mnNas.add(mntmSingleHotspotFeatures);
	
		JMenuItem mntmMultipleHotspotFeatures = new JMenuItem("Multiple Variation Points Features");
		mnNas.add(mntmMultipleHotspotFeatures);
	
		JMenuItem mntmRigidNoVariation = new JMenuItem("Rigid No Variation Points Features");
		mnNas.add(mntmRigidNoVariation);
	
		JMenuItem mntmNumberOfValid = new JMenuItem("Number of valid Configurations");
		mnNas.add(mntmNumberOfValid);
	
		JMenuItem mntmBranchingFactorMax = new JMenuItem("Branching Factors Max");
		mnNas.add(mntmBranchingFactorMax);
		
		JMenuItem mntmBranchingFactorMedian = new JMenuItem("Branching Factors Median");
		mnNas.add(mntmBranchingFactorMedian);
		
		JMenuItem mntmOrRate = new JMenuItem("Or Rate");
		mnNas.add(mntmOrRate);
		
		JMenuItem mntmXorRate = new JMenuItem("Xor Rate");
		mnNas.add(mntmXorRate);
		
		JMenuItem mntmProductLineTotal = new JMenuItem("Ratio of Variability");
		mnNas.add(mntmProductLineTotal);
		
		JMenuItem mntmNonfunctionalCommonality = new JMenuItem("Non-Functional Commonality");
		mnNas.add(mntmNonfunctionalCommonality);
		
		mntmnumberOfFeatures.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Number of Features: " + currentViewer.getModel().numberOfFeatures());				
			}
		});
		
		mntmNumberofOptionalFeatures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Number of Optional Features: " + currentViewer.getModel().numberOfOptionalFeatures());
			}
		});
		
		mntmNumberOfMandatoryFeatures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Number of Mandatory Features: " + currentViewer.getModel().numberOfMandatoryFeatures());
			}
		});
		
		mntmNumberOfTop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Number of Top Features: " + currentViewer.getModel().numberOfTopFeatures());		
			}
		});
		
		mntmNumberOfLeaf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Number of Leaf Features: " + currentViewer.getModel().numberOfLeafFeatures());
			}
		});
		
		mntmDepthOfTree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Depth of tree: " + currentViewer.getModel().depthOfTreeMax());
			}
		});
		
		mntmMedianDepthOfTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Depth of tree Median: " + currentViewer.getModel().depthOfTreeMedian());
			}
		});
		
		mntmCognitiveComplexityOf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Cognitive Complexity of a Feature Model: " + currentViewer.getModel().cognitiveComplexityOfFeatureModel());
			}
		});
		
		mntmFlexibilityOfConguration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Flexibility of Configuration: " + currentViewer.getModel().flexibilityOfConfiguration());
			}
		});
		
		mntmSingleCyclicDependent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Single Cyclic Dependent Features: " + currentViewer.getModel().singleCyclicDependentFeatures());	
			}
		});
		
		mntmMultipleCyclicDependent.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Multiple Cyclic Dependent Features: " + currentViewer.getModel().multipleCyclicDependentFeatures());			
			}
		});
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Feature Extendibility: " + currentViewer.getModel().featureExtendibility());
			}
		});
	
		mntmCyclomaticComplexity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				currentViewer.getLblResultReasoning().setText("Cyclomatic complexity: " + currentViewer.getModel().cyclomaticComplexity());
			}
		});
		
		mntmVariableCrosstreeConstraintsRate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				currentViewer.getLblResultReasoning().setText("Variable Cross tree Constraints: " + currentViewer.getModel().crossTreeConstraintsVariables());
			}
		});
		
		
		mntmCompoundComplexity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					currentViewer.getLblResultReasoning().setText("Compound complexity: " + currentViewer.getModel().compoundComplexity());
				}
		});
		
		mntmGroupingFeatures.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					currentViewer.getLblResultReasoning().setText("Grouping Features: " + currentViewer.getModel().groupingFeatures());
				}
		});
		
		mntmCrosstreeConstraintsRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Cross-tree constraints Rate: " + currentViewer.getModel().crossTreeConstraintsRate());			
			}
		});
		
		mntmCoefcientOfConnectivitydensity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {		
				currentViewer.getLblResultReasoning().setText("Coeficient of Connectivity-Density: " + currentViewer.getModel().coefficientOfConnectivityDensity());
			}
		});
		
		mntmNumberOfVariable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Number of variable features: " + currentViewer.getModel().numberOfVariableFeatures());
			}
		});
		
		mntmSingleHotspotFeatures.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Single Variation Points Features: " + currentViewer.getModel().singleVariationPointsFeatures());
			}
		});
		
		mntmMultipleHotspotFeatures.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Multiple Variation Points Features" + ": " + currentViewer.getModel().multipleVariationPointsFeatures());
			}
		});
		
		mntmRigidNoVariation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Rigid No Variation Points Features: " + currentViewer.getModel().rigidNotVariationPointsFeatures());		
			}
		});
		
		mntmProductLineTotal.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {		
				currentViewer.getLblResultReasoning().setText("Ratio of Variability: " + currentViewer.getModel().ratioVariability());
			}
		});
		
		mntmNumberOfValid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				currentViewer.getLblResultReasoning().setText("Number of valid configurations: " + currentViewer.getModel().numberOfValidConfigurations());
			}
		});
		
		mntmBranchingFactorMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Branching Factors Max: " + currentViewer.getModel().branchingFactorsMax());
			}
		});
		
		mntmOrRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				currentViewer.getLblResultReasoning().setText("Or Rate: " + currentViewer.getModel().orRate());
			}
		});
			
		mntmXorRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				currentViewer.getLblResultReasoning().setText("Xor Rate: " + currentViewer.getModel().xorRate());
			}
		});
		
		mntmBranchingFactorMedian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				currentViewer.getLblResultReasoning().setText("Branching Factors Median: " + currentViewer.getModel().branchingFactorsMedian());
			}
		});
		
		mntmNonfunctionalCommonality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				currentViewer.getLblResultReasoning().setText("Non-Functional Commonality: " + currentViewer.getModel().nonFunctionCommonality());
			}
		});
		
		//ESPECIFICO PARA O CONTEXTO
	
		JMenu mnWithoutContext = new JMenu("Specific to context");
		mnMeasures_1.add(mnWithoutContext);
		
		JMenuItem mntmNumberOfContexts = new JMenuItem("Number of Contexts");
		mnWithoutContext.add(mntmNumberOfContexts);
		
		JMenuItem mntmNumberOfActivated = new JMenuItem("Number of Activated Features");
		mnWithoutContext.add(mntmNumberOfActivated);
		
		JMenuItem mntmNumberOfDeactivated = new JMenuItem("Number of Deactivated Features");
		mnWithoutContext.add(mntmNumberOfDeactivated);
		
		JMenuItem mntmNumberOfContextConstraints = new JMenuItem("Number of Context Constraints");
		mnWithoutContext.add(mntmNumberOfContextConstraints);
		
		JMenuItem mntmactivatedFeaturesByContextAdaptation = new JMenuItem("Activated features by context adaptation");
		mnWithoutContext.add(mntmactivatedFeaturesByContextAdaptation);
		
		JMenuItem mntmdesactivatedFeaturesByContextAdaptation = new JMenuItem("Desactivated features by context adaptation");
		mnWithoutContext.add(mntmdesactivatedFeaturesByContextAdaptation);
		
		JMenuItem mntmContextFeatures = new JMenuItem("Context Features");
		mnWithoutContext.add(mntmContextFeatures);
		
		JMenuItem mntmContextFeaturesContraints = new JMenuItem("Context Features in Conntraints");
		mnWithoutContext.add(mntmContextFeaturesContraints);
		
		mntmNumberOfContextConstraints.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {		
					currentViewer.getLblResultReasoning().setText("Number of Context Constraints: " + currentViewer.getModel().numberContextConstraints());
			}
		});
		
		mntmNumberOfDeactivated.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				currentViewer.getLblResultReasoning().setText("Number of Deactivated Features: " + currentViewer.getModel().numberDeactivatedFeatures());
			}
		});
		
		mntmNumberOfActivated.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {				
				currentViewer.getLblResultReasoning().setText("Number of Activated Features: " + currentViewer.getModel().numberActivatedFeatures());
			}
		});
		
		mntmNumberOfContexts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				currentViewer.getLblResultReasoning().setText("Number of Contexts: " + currentViewer.getModel().numberOfContexts());				
			}
		});
		
		mntmactivatedFeaturesByContextAdaptation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				currentViewer.getLblResultReasoning().setText("AFCA: " + currentViewer.getModel().activatedFeaturesByContextAdaptation());				
			}
		});
		
		mntmdesactivatedFeaturesByContextAdaptation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				currentViewer.getLblResultReasoning().setText("DFCA: " + currentViewer.getModel().desactivatedFeaturesByContextAdaptation());				
			}
		});
		
		mntmContextFeatures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				currentViewer.getLblResultReasoning().setText("CF: " + currentViewer.getModel().contextFeatures());				
			}
		});
		
		mntmContextFeaturesContraints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				currentViewer.getLblResultReasoning().setText("CFC: " + currentViewer.getModel().contextFeaturesContraints());				
			}
		});
	}
	
	public interface listItens{
		public void ready(List<SchemeXml> lista);
	}
	
	

}
