package br.ufc.lps.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXlsDataSource;
import br.ufc.lps.gui.export.ExportOfficeExcel;
import br.ufc.lps.model.context.FamiliarContextModel;
import br.ufc.lps.model.context.SplotContextModel;
import br.ufc.lps.model.normal.FamiliarModel;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.normal.SplotModel;
import br.ufc.lps.model.xml.ModelID;
import br.ufc.lps.model.xml.XMLFamiliarModel;
import br.ufc.lps.model.xml.XMLSplotModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.util.ReportUtils;

public class Main extends JFrame {

	
	private JTabbedPane tabbedPane;
	//Identifica o viewer atual para definir o modelo em quest�o a ser utilizado nas metricas
	private ViewerPanel currentViewer;
	private JMenu mnMeasures_1;
	
	
	/**
	 * Launch the application.
	 * @throws JRException 
	 */
	public static void main(String[] args) throws JRException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
										
					Main frame = new Main();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//ReportUtils report = new ReportUtils();
		//report.fill1();
	}

	 
	/**
	 * Create the frame.
	 */
	public Main() {
		
		initXMLmodels();
	

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);

		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        if(index != -1) {
					Component componentAt = sourceTabbedPane.getComponentAt(index);
					if(componentAt instanceof ViewerPanel)
						currentViewer = (ViewerPanel) componentAt;
		        }
				
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnMeasures_1 = new JMenu("Measures");
		final JMenu mnFile = new JMenu("File");
		
		menuBar.add(mnFile);

		JMenuItem mntmOpenSplotxml = new JMenuItem("Open SPLOT .xml");
		mntmOpenSplotxml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
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
					
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					final ViewerPanel viewer = new ViewerPanel(new SplotContextModel(path));
					currentViewer = viewer;
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							mnMeasures_1.setEnabled(true);
							
							String tabName = viewer.getModelName();
							long time = System.currentTimeMillis();
							
							tabbedPane.addTab(tabName+time, viewer);	
							
							int index = tabbedPane.indexOfTab(tabName+time);
							JPanel pnlTab = new JPanel(new GridBagLayout());
							pnlTab.setOpaque(false);
							JLabel lblTitle = new JLabel(tabName);
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

							btnClose.addActionListener(new MyCloseActionHandler(tabName+time));
							
							Main.this.repaint();
						}
					});
					
					
					
					
					
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
		
		JMenuItem mntmEditSplotModel = new JMenuItem("Open SPLOT's xml");
		mnEditor.add(mntmEditSplotModel);
		
		mntmEditSplotModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
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
					
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					IModel model = new SplotModel(path);
					final EditorPanel editor = new EditorPanel(model, ModelID.SPLOT_MODEL.getId(), path);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							mnMeasures_1.setEnabled(true);
							String tabName = editor.getModelName();
							long time = System.currentTimeMillis();
							
							tabbedPane.addTab(tabName+time, editor);	
							
							int index = tabbedPane.indexOfTab(tabName+time);
							JPanel pnlTab = new JPanel(new GridBagLayout());
							pnlTab.setOpaque(false);
							JLabel lblTitle = new JLabel(tabName);
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

							btnClose.addActionListener(new MyCloseActionHandler(tabName+time));
						}
					});
					
					
					
					
					
				}
			}
		});
		
		
		
		
	}

	private void initXMLmodels() {
		new XMLSplotModel();
		new XMLFamiliarModel();
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
	            // It would probably be worthwhile getting the source
	            // casting it back to a JButton and removing
	            // the action handler reference ;)

	        }

	    }

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
		/*JMenuItem mntmCrosstreeConstraints = new JMenuItem(
		"Cross-tree constraints");
		mnNas.add(mntmCrosstreeConstraints);*/
	
		/*JMenuItem mntmNumberOfVariation = new JMenuItem(
		"Number of variation points");
		mnNas.add(mntmNumberOfVariation);*/
	
		
	
		/*JMenuItem mntmBranchingFactorsMean = new JMenuItem("Branching Factors Mean");
		mntmBranchingFactorsMean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				currentViewer.getLblResultReasoning().setText("Branching Factors Mean: " + currentViewer.getModel().branchingFactorsMean());
			}
		});
		mnNas.add(mntmBranchingFactorsMean);*/
	
	
		/*JMenuItem mntmOrNumber = new JMenuItem("Or Number");
		mntmOrNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				currentViewer.getLblResultReasoning().setText("Or Number: " + currentViewer.getModel().orNumber());
			}
		});
		mnNas.add(mntmOrNumber);
		
		JMenuItem mntmXorNumber = new JMenuItem("Xor Number");
		mntmXorNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				currentViewer.getLblResultReasoning().setText("Xor Number: " + currentViewer.getModel().xorNumber());
			}
		});
		mnNas.add(mntmXorNumber);*/
	
		/*JMenuItem mntmNumberOfAlternativeFeatures = new JMenuItem("Number of Alternative Features");
		mntmNumberOfAlternativeFeatures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				currentViewer.getLblResultReasoning().setText("Number of Alternative Features: " + currentViewer.getModel().numberOfAlternativeFeatures());
			}
		});
		mnNas.add(mntmNumberOfAlternativeFeatures);*/

		/*mntmNumberOfVariation.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {			
				currentViewer.getLblResultReasoning().setText("Number of variation points: " + currentViewer.getModel().numberOfVariationPoints());
			}
		});*/

		/*mntmCrosstreeConstraints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				currentViewer.getLblResultReasoning().setText("Cross-tree Constraints: " + currentViewer.getModel().crossTreeConstraints());
			}
		});*/
	
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
	}
	

}
