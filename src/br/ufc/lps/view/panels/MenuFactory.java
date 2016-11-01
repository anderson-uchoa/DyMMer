package br.ufc.lps.view.panels;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.ufc.lps.controller.features.TypeFeature;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;

public class MenuFactory {

	private static ActionListener editorPanel;
	private static FeatureTreeNode featureTreeNode;
	private static MenuFactory instancia;
	private static JPopupMenu jPopupMenu;
	
	private final String NOT_COMPLETELY_SATISFIES= "not completely satisfies \"--\"";
	private final String COMPLETELY_SATISFIES= "completely satisfies \"++\"";
	private final String POSITIVE_INFLUENCE= "positive influence  \"+\"";
	private final String NEGATIVE_INFLUENCE= "negative influence \"−\"";

	public static MenuFactory getIntance(ActionListener editor, FeatureTreeNode feature) {
		featureTreeNode = feature;
		editorPanel = editor;

		if (instancia == null) {
			instancia = new MenuFactory(editorPanel, featureTreeNode);
		}
		return instancia;
	}

	public MenuFactory(ActionListener editorPanel, FeatureTreeNode featureTreeNode) {

		this.editorPanel = editorPanel;
		this.featureTreeNode = featureTreeNode;

	}

	public JPopupMenu verificarMenuDeSelecao(TypeFeature typeFeature) {

		switch (typeFeature) {

		case ROOT:
			return showMenuRootFeature();

		case GROUP_XOR:
			return showMenuXORGroup();

		case GROUP_OR:
			return showMenuORGroup();

		case MANDATORY:
			return showMenuMandatoryFeature();

		case OPTIONAL:
			return showMenuOptionalFeature();

		case GROUPED_FEATURE:
			return showMenuGroupedFeature();

		default:

		}
		return null;

	}

	public JPopupMenu showGenericMenu() {

		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();

		JMenuItem setActive = new JMenuItem("Set as active node");
		JMenuItem setDeactive = new JMenuItem("Set as deactive node");

		JMenuItem takeOffContext = new JMenuItem("Take it off from context");
		JMenuItem addConstraintPositive = new JMenuItem("Add to Constraint as Positive");
		JMenuItem addConstraintNegative = new JMenuItem("Add to Constraint as Negative");

		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");
		JMenuItem remove = new JMenuItem("Remove");

		setActive.setActionCommand("setActive");
		setDeactive.setActionCommand("setDeactive");
		takeOffContext.setActionCommand("takeOffContext");
		addConstraintPositive.setActionCommand("addConstraintPositive");
		addConstraintNegative.setActionCommand("addConstraintNegative");

		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addXORGroup.setActionCommand("addXORGroup");
		addORGroup.setActionCommand("addORGroup");
		remove.setActionCommand("remove");

		setActive.addActionListener(editorPanel);
		setDeactive.addActionListener(editorPanel);
		takeOffContext.addActionListener(editorPanel);
		addConstraintPositive.addActionListener(editorPanel);
		addConstraintNegative.addActionListener(editorPanel);

		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);
		remove.addActionListener(editorPanel);

		menu.add(setActive);
		menu.add(setDeactive);
		menu.add(takeOffContext);

		menu.add(separator);
		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(addXORGroup);
		menu.add(addORGroup);
		menu.add(remove);

		menu.add(addConstraintNegative);
		menu.add(addConstraintPositive);

		return menu;

	}

	public JPopupMenu showMenuOptionalFeature() {

		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();

		JMenuItem setActive = new JMenuItem("Set as active node");
		JMenuItem setDeactive = new JMenuItem("Set as deactive node");

		JMenuItem takeOffContext = new JMenuItem("Take it off from context");
		JMenuItem addConstraintPositive = new JMenuItem("Add to Constraint as Positive");
		JMenuItem addConstraintNegative = new JMenuItem("Add to Constraint as Negative");
		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");
		

		JMenuItem addConstraintRnfPositive2 = new JMenuItem(COMPLETELY_SATISFIES);
		JMenuItem addConstraintRnfPositive1 = new JMenuItem(POSITIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative1 = new JMenuItem(NEGATIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative2 = new JMenuItem(NOT_COMPLETELY_SATISFIES);

		addConstraintRnfNegative1.setActionCommand("AddtoConstraintRNF-");
		addConstraintRnfNegative1.addActionListener(editorPanel);
		addConstraintRnfNegative2.setActionCommand("AddtoConstraintRNF--");
		addConstraintRnfNegative2.addActionListener(editorPanel);
		addConstraintRnfPositive1.setActionCommand("AddtoConstraintRNF+");
		addConstraintRnfPositive1.addActionListener(editorPanel);
		addConstraintRnfPositive2.setActionCommand("AddtoConstraintRNF++");
		addConstraintRnfPositive2.addActionListener(editorPanel);
		
		JMenuItem remove = new JMenuItem("Remove");

		setActive.setActionCommand("setActive");
		setDeactive.setActionCommand("setDeactive");
		takeOffContext.setActionCommand("takeOffContext");
		addConstraintPositive.setActionCommand("addConstraintPositive");
		addConstraintNegative.setActionCommand("addConstraintNegative");
		addXORGroup.setActionCommand("addXORGroup");
		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addORGroup.setActionCommand("addORGroup");

		remove.setActionCommand("remove");

		setActive.addActionListener(editorPanel);
		setDeactive.addActionListener(editorPanel);
		takeOffContext.addActionListener(editorPanel);
		addConstraintPositive.addActionListener(editorPanel);
		addConstraintNegative.addActionListener(editorPanel);

		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);

		remove.addActionListener(editorPanel);

		menu.add(setActive);
		menu.add(setDeactive);
		menu.add(takeOffContext);

		menu.add(separator);
		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(addXORGroup);
		menu.add(addORGroup);
		menu.add(remove);

		menu.add(separator);

		menu.add(addConstraintNegative);
		menu.add(addConstraintPositive);
		
		menu.add(separator);
		
		menu.add(addConstraintRnfPositive2);
		menu.add(addConstraintRnfPositive1);
		menu.add(addConstraintRnfNegative1);
		menu.add(addConstraintRnfNegative2);

		return menu;

	}

	public JPopupMenu showMenuMandatoryFeature() {
		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();

		JMenuItem setActive = new JMenuItem("Set as active node");
		JMenuItem setDeactive = new JMenuItem("Set as deactive node");

		JMenuItem takeOffContext = new JMenuItem("Take it off from context");
		JMenuItem addConstraintPositive = new JMenuItem("Add to Constraint as Positive");
		JMenuItem addConstraintNegative = new JMenuItem("Add to Constraint as Negative");
		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");
		
		JMenuItem addConstraintRnfPositive2 = new JMenuItem(COMPLETELY_SATISFIES);
		JMenuItem addConstraintRnfPositive1 = new JMenuItem(POSITIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative1 = new JMenuItem(NEGATIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative2 = new JMenuItem(NOT_COMPLETELY_SATISFIES);

		addConstraintRnfNegative1.setActionCommand("AddtoConstraintRNF-");
		addConstraintRnfNegative1.addActionListener(editorPanel);
		addConstraintRnfNegative2.setActionCommand("AddtoConstraintRNF--");
		addConstraintRnfNegative2.addActionListener(editorPanel);
		addConstraintRnfPositive1.setActionCommand("AddtoConstraintRNF+");
		addConstraintRnfPositive1.addActionListener(editorPanel);
		addConstraintRnfPositive2.setActionCommand("AddtoConstraintRNF++");
		addConstraintRnfPositive2.addActionListener(editorPanel);
		
		JMenuItem remove = new JMenuItem("Remove");

		setActive.setActionCommand("setActive");
		setDeactive.setActionCommand("setDeactive");
		takeOffContext.setActionCommand("takeOffContext");
		addConstraintPositive.setActionCommand("addConstraintPositive");
		addConstraintNegative.setActionCommand("addConstraintNegative");
		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addXORGroup.setActionCommand("addXORGroup");
		addORGroup.setActionCommand("addORGroup");

		remove.setActionCommand("remove");

		setActive.addActionListener(editorPanel);
		setDeactive.addActionListener(editorPanel);
		takeOffContext.addActionListener(editorPanel);
		addConstraintPositive.addActionListener(editorPanel);
		addConstraintNegative.addActionListener(editorPanel);

		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);

		remove.addActionListener(editorPanel);

		menu.add(setActive);
		menu.add(setDeactive);
		menu.add(takeOffContext);

		menu.add(separator);
		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(addXORGroup);
		menu.add(addORGroup);
		menu.add(remove);

		menu.add(separator);

		menu.add(addConstraintNegative);
		menu.add(addConstraintPositive);
		
		menu.add(separator);
		
		menu.add(addConstraintRnfPositive2);
		menu.add(addConstraintRnfPositive1);
		menu.add(addConstraintRnfNegative1);
		menu.add(addConstraintRnfNegative2);

		return menu;

	}

	public JPopupMenu showMenuRootFeature() {

		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();

		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");

		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addXORGroup.setActionCommand("addXORGroup");
		addORGroup.setActionCommand("addORGroup");
		
		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);
		
		
		
		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(separator);

		menu.add(addXORGroup);
		menu.add(addORGroup);

		return menu;

	}

	public JPopupMenu showMenuXORGroup() {

		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();

		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");
		
		JMenuItem addConstraintRnfPositive2 = new JMenuItem(COMPLETELY_SATISFIES);
		JMenuItem addConstraintRnfPositive1 = new JMenuItem(POSITIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative1 = new JMenuItem(NEGATIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative2 = new JMenuItem(NOT_COMPLETELY_SATISFIES);

		addConstraintRnfNegative1.setActionCommand("AddtoConstraintRNF-");
		addConstraintRnfNegative1.addActionListener(editorPanel);
		addConstraintRnfNegative2.setActionCommand("AddtoConstraintRNF--");
		addConstraintRnfNegative2.addActionListener(editorPanel);
		addConstraintRnfPositive1.setActionCommand("AddtoConstraintRNF+");
		addConstraintRnfPositive1.addActionListener(editorPanel);
		addConstraintRnfPositive2.setActionCommand("AddtoConstraintRNF++");
		addConstraintRnfPositive2.addActionListener(editorPanel);
		
		JMenuItem remove = new JMenuItem("Remove");
		
		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addXORGroup.setActionCommand("addXORGroup");
		addORGroup.setActionCommand("addORGroup");
		remove.setActionCommand("remove");
		
		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);
		remove.addActionListener(editorPanel);
		

		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(separator);

		menu.add(addXORGroup);
		menu.add(addORGroup);
		menu.add(remove);
		
		menu.add(separator);
		
		menu.add(addConstraintRnfPositive2);
		menu.add(addConstraintRnfPositive1);
		menu.add(addConstraintRnfNegative1);
		menu.add(addConstraintRnfNegative2);

		return menu;

	}

	public JPopupMenu showMenuORGroup() {

		JPopupMenu menu = new JPopupMenu();

		JSeparator separator = new JSeparator();

		JMenuItem addOptionalFeature = new JMenuItem("Add a Optional Feature");
		JMenuItem addMandatoryFeature = new JMenuItem("Add a Mandatory Feature");
		JMenuItem addXORGroup = new JMenuItem("Add a XOR Group");
		JMenuItem addORGroup = new JMenuItem("Add a OR Group");
		
		JMenuItem addConstraintRnfPositive2 = new JMenuItem(COMPLETELY_SATISFIES);
		JMenuItem addConstraintRnfPositive1 = new JMenuItem(POSITIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative1 = new JMenuItem(NEGATIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative2 = new JMenuItem(NOT_COMPLETELY_SATISFIES);

		JMenuItem remove = new JMenuItem("Remove");
		
		addConstraintRnfNegative1.setActionCommand("AddtoConstraintRNF-");
		addConstraintRnfNegative1.addActionListener(editorPanel);
		addConstraintRnfNegative2.setActionCommand("AddtoConstraintRNF--");
		addConstraintRnfNegative2.addActionListener(editorPanel);
		addConstraintRnfPositive1.setActionCommand("AddtoConstraintRNF+");
		addConstraintRnfPositive1.addActionListener(editorPanel);
		addConstraintRnfPositive2.setActionCommand("AddtoConstraintRNF++");
		addConstraintRnfPositive2.addActionListener(editorPanel);
		
		addOptionalFeature.setActionCommand("addOptionalFeature");
		addMandatoryFeature.setActionCommand("addMandatoryFeature");
		addXORGroup.setActionCommand("addXORGroup");
		addORGroup.setActionCommand("addORGroup");
		remove.setActionCommand("remove");
		
		addOptionalFeature.addActionListener(editorPanel);
		addMandatoryFeature.addActionListener(editorPanel);
		addXORGroup.addActionListener(editorPanel);
		addORGroup.addActionListener(editorPanel);
		

		menu.add(addOptionalFeature);
		menu.add(addMandatoryFeature);
		menu.add(separator);

		menu.add(addXORGroup);
		menu.add(addORGroup);
		menu.add(remove);
		
		menu.add(separator);
		
		menu.add(addConstraintRnfPositive2);
		menu.add(addConstraintRnfPositive1);
		menu.add(addConstraintRnfNegative1);
		menu.add(addConstraintRnfNegative2);
		
		
		return menu;

	}

	public JPopupMenu showMenuGroupedFeature() {

		JPopupMenu menu = new JPopupMenu();

		JMenuItem setActive = new JMenuItem("Set as active node");
		JMenuItem setDeactive = new JMenuItem("Set as deactive node");
		
		JSeparator separator = new JSeparator();

		JMenuItem takeOffContext = new JMenuItem("Take it off from context");
		JMenuItem addConstraintPositive = new JMenuItem("Add to Constraint as Positive");
		JMenuItem addConstraintNegative = new JMenuItem("Add to Constraint as Negative");

		JMenuItem remove = new JMenuItem("Remove");

		setActive.setActionCommand("setActive");
		setDeactive.setActionCommand("setDeactive");
		takeOffContext.setActionCommand("takeOffContext");
		addConstraintPositive.setActionCommand("addConstraintPositive");
		addConstraintNegative.setActionCommand("addConstraintNegative");

		JMenuItem addConstraintRnfPositive2 = new JMenuItem(COMPLETELY_SATISFIES);
		JMenuItem addConstraintRnfPositive1 = new JMenuItem(POSITIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative1 = new JMenuItem(NEGATIVE_INFLUENCE);
		JMenuItem addConstraintRnfNegative2 = new JMenuItem(NOT_COMPLETELY_SATISFIES);
		
		addConstraintRnfNegative1.setActionCommand("AddtoConstraintRNF-");
		addConstraintRnfNegative1.addActionListener(editorPanel);
		addConstraintRnfNegative2.setActionCommand("AddtoConstraintRNF--");
		addConstraintRnfNegative2.addActionListener(editorPanel);
		addConstraintRnfPositive1.setActionCommand("AddtoConstraintRNF+");
		addConstraintRnfPositive1.addActionListener(editorPanel);
		addConstraintRnfPositive2.setActionCommand("AddtoConstraintRNF++");
		addConstraintRnfPositive2.addActionListener(editorPanel);
		
		remove.setActionCommand("remove");

		setActive.addActionListener(editorPanel);
		setDeactive.addActionListener(editorPanel);
		takeOffContext.addActionListener(editorPanel);
		addConstraintPositive.addActionListener(editorPanel);
		addConstraintNegative.addActionListener(editorPanel);

		remove.addActionListener(editorPanel);

		menu.add(setActive);
		menu.add(setDeactive);
		menu.add(takeOffContext);

		menu.add(separator);

		menu.add(remove);

		menu.add(addConstraintNegative);
		menu.add(addConstraintPositive);
		
		menu.add(separator);
		
		menu.add(addConstraintRnfPositive2);
		menu.add(addConstraintRnfPositive1);
		menu.add(addConstraintRnfNegative1);
		menu.add(addConstraintRnfNegative2);

		return menu;
	}

}
