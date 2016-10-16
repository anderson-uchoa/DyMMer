package br.ufc.lps.view.trees;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Illustrates usage of the check box tree in {@link org.scijava.swing.checkboxtree}.
 * <p>
 * Thanks to John Zukowski for the <a
 * href="http://www.java2s.com/Code/Java/Swing-JFC/CheckBoxNodeTreeSample.htm"
 * >sample code</a> upon which this is based.
 * </p>
 * 
 * @author Curtis Rueden
 */
public class CheckBoxTreeSample {

	public static void main(final String args[]) {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

		final DefaultMutableTreeNode accessibility = add(root, "Accessibility", true);
		add(accessibility, "Move system caret with focus/selection changes", false);
		add(accessibility, "Always expand alt text for images", true);
		root.add(accessibility);

		final DefaultMutableTreeNode browsing = new DefaultMutableTreeNode("Browsing");
		add(browsing, "Notify when downloads complete", true);
		add(browsing, "Disable script debugging", true);
		add(browsing, "Use AutoComplete", true);
		add(browsing, "Browse in a new process", false);
		root.add(browsing);

		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		final JTree tree = new JTree(treeModel);

		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
		tree.setCellEditor(editor);
		tree.setEditable(true);

		// listen for changes in the selection
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				System.out.println(System.currentTimeMillis() + ": selection changed");
			}
		});

		// listen for changes in the model (including check box toggles)
		treeModel.addTreeModelListener(new TreeModelListener() {

			@Override
			public void treeNodesChanged(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes changed");
			}

			@Override
			public void treeNodesInserted(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes inserted");
			}

			@Override
			public void treeNodesRemoved(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": nodes removed");
			}

			@Override
			public void treeStructureChanged(final TreeModelEvent e) {
				System.out.println(System.currentTimeMillis() + ": structure changed");
			}
		});

		// show the tree onscreen
		final JFrame frame = new JFrame("CheckBox Tree");
		final JScrollPane scrollPane = new JScrollPane(tree);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setVisible(true);
	}

	private static DefaultMutableTreeNode add(
		final DefaultMutableTreeNode parent, final String text, final boolean checked)
	{
		final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
		final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
		parent.add(node);
		return node;
	}
}