package br.ufc.lps.view.trees.adaptation;
import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufc.lps.model.adaptation.ValueQuantification;

public class CheckBoxNodePanel extends JPanel {

	public final JLabel label = new JLabel();
	public final JCheckBox check = new JCheckBox();
	public ValueQuantification quantification;

	public CheckBoxNodePanel() {
		this.check.setMargin(new Insets(0, 0, 0, 0));
		setLayout(new BorderLayout());
		add(check, BorderLayout.WEST);
		add(label, BorderLayout.CENTER);
	}

}