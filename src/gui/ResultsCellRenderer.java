package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import parser.Article;

public class ResultsCellRenderer implements TreeCellRenderer {
	
	private JLabel titleLabel = new JLabel(" ");
	private JPanel renderer = new JPanel();
	private DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
	private Color backgroundSelectionColor;
	private Color backgroundNonSelectionColor;
	
	public ResultsCellRenderer() {
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.CENTER);
	    titleLabel.setForeground(Color.BLACK);
	    renderer.add(titleLabel);
	    renderer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    backgroundSelectionColor = defaultRenderer.getBackgroundSelectionColor();
	    backgroundNonSelectionColor = defaultRenderer.getBackgroundNonSelectionColor();
	    
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		   Component returnValue = null;
		    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
		      Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
		      if (userObject instanceof Article) {
		        Article a = (Article) userObject;
		        String temp = a.getHeadLine().replaceAll("\\n", "<br>");
		        titleLabel.setText("<html><p style='font-size:7px'>" + temp + "</p></html>");
		        if (selected) {
		            renderer.setBackground(backgroundSelectionColor);
		          } else {
		            renderer.setBackground(backgroundNonSelectionColor);
		          }
		        renderer.setEnabled(tree.isEnabled());
		        returnValue = renderer;
		      }
		    }
		    if (returnValue == null) {
		      returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded,
		          leaf, row, hasFocus);
		    }
		    return returnValue;
	}

}
