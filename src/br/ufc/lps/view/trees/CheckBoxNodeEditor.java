package br.ufc.lps.view.trees;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

/**
 * A {@link TreeCellEditor} for check box tree nodes.
 * <p>
 * Thanks to John Zukowski for the <a
 * href="http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm"
 * >sample code</a> upon which this is based.
 * </p>
 * 
 * @author Curtis Rueden
 */
public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {

	private final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();

	private final JTree theTree;

	public CheckBoxNodeEditor(final JTree tree) {
		theTree = tree;
	}

	@Override
	public Object getCellEditorValue() {
		final CheckBoxNodePanel panel = renderer.getPanel();
		final CheckBoxNodeData checkBoxNode =
			new CheckBoxNodeData(panel.label.getText(), panel.check.isSelected());
		return checkBoxNode;
	}

	@Override
	public boolean isCellEditable(final EventObject event) {
		if (!(event instanceof MouseEvent)) return false;
		final MouseEvent mouseEvent = (MouseEvent) event;

		final TreePath path =
			theTree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
		if (path == null) return false;

		final Object node = path.getLastPathComponent();
		if (!(node instanceof DefaultMutableTreeNode)) return false;
		final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;

		final Object userObject = treeNode.getUserObject();
		return userObject instanceof CheckBoxNodeData;
	}

	@Override
	public Component getTreeCellEditorComponent(final JTree tree,
		final Object value, final boolean selected, final boolean expanded,
		final boolean leaf, final int row)
	{

		final Component editor =
			renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf,
				row, true);

		// editor always selected / focused
		final ItemListener itemListener = new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent itemEvent) {
				if (stopCellEditing()) {
					fireEditingStopped();
				}
			}
		};
		if (editor instanceof CheckBoxNodePanel) {
			final CheckBoxNodePanel panel = (CheckBoxNodePanel) editor;
			panel.check.addItemListener(itemListener);
		}

		return editor;
	}
}