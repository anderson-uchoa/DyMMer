package br.ufc.lps.view.trees.adaptation;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxNodeRenderer implements TreeCellRenderer {

	private final CheckBoxNodePanel panel = new CheckBoxNodePanel();
	private Icon contexto = new ImageIcon("images/contexto.png");

	private final DefaultTreeCellRenderer defaultRenderer =
		new DefaultTreeCellRenderer();

	private final Color selectionForeground, selectionBackground;
	private final Color textForeground, textBackground;

	protected CheckBoxNodePanel getPanel() {
		return panel;
	}

	public CheckBoxNodeRenderer() {
		final Font fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) panel.label.setFont(fontValue);

		final Boolean focusPainted =
			(Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		panel.check.setFocusPainted(focusPainted != null && focusPainted);

		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}

	// -- TreeCellRenderer methods --

	@Override
	public Component getTreeCellRendererComponent(final JTree tree,
		final Object value, final boolean selected, final boolean expanded,
		final boolean leaf, final int row, final boolean hasFocus)
	{
		CheckBoxNodeData data = null;
		if (value instanceof DefaultMutableTreeNode) {
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			final Object userObject = node.getUserObject();
			if (userObject instanceof CheckBoxNodeData) {
				data = (CheckBoxNodeData) userObject;
			}
		}

		final String stringValue =
			tree.convertValueToText(value, selected, expanded, leaf, row, false);
		panel.label.setText(stringValue);
		panel.check.setSelected(false);

		panel.setEnabled(tree.isEnabled());

		if (selected) {
			panel.setForeground(selectionForeground);
			panel.setBackground(selectionBackground);
			panel.label.setForeground(selectionForeground);
			panel.label.setBackground(selectionBackground);
		}
		else {
			panel.setForeground(textForeground);
			panel.setBackground(textBackground);
			panel.label.setForeground(textForeground);
			panel.label.setBackground(textBackground);
		}

		if (data == null) {
			defaultRenderer.setIcon(contexto);
			return defaultRenderer.getTreeCellRendererComponent(tree, value,
				selected, expanded, leaf, row, hasFocus);
		}

		panel.label.setText(data.getText());
		panel.check.setSelected(data.isChecked());
		panel.quantification = data.getValueQuantification();

		return panel;
	}

}