package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import parser.Article;
import system.WallStreetSystem;

public class SearchGUI extends JFrame implements TreeSelectionListener {

	private JRadioButton bodyBox;
	private JTextPane docPane;
	private JLabel docTitle;
	private JTree docTree;
	private JRadioButton headLineBox;
	private JRadioButton leadParBox;
	private JLabel resultsLabel;
	private JButton searchButton;
	private JTextField searchField;
	private JPanel searchPanel;
	private JButton searchViewButton;
	private JLabel suggestLabel;
	private JRadioButton tagsBox;
	private JRadioButton allBox;
	private JScrollPane textScroll;
	private JLabel titleLabel;
	private JScrollPane treeScroll;
	private JPanel viewPanel;
	private JPanel searchHolder;
	private JPanel fieldsHolder;
	private JSplitPane splitPane;
	private JPanel titlePanel;
	private ButtonGroup group;
	private JPanel suggestPanel;

	private List<Article> articles = null;
	private WallStreetSystem system = null;
	private String action = "contents";

	public SearchGUI() {
		initComponents();
		system = new WallStreetSystem();
	}

	public void initComponents() {

		searchPanel = new JPanel();
		searchHolder = new JPanel();
		fieldsHolder = new JPanel();
		searchField = new JTextField(32);
		searchButton = new JButton("Search");
		titleLabel = new JLabel("Wall Street Journal System");
		suggestLabel = new JLabel();
		headLineBox = new JRadioButton("Headline");
		tagsBox = new JRadioButton("Tags");
		leadParBox = new JRadioButton("Leading Paragraph");
		bodyBox = new JRadioButton("Text Body");
		allBox = new JRadioButton("Full Text");
		viewPanel = new JPanel();
		docPane = new JTextPane();
		textScroll = new JScrollPane(docPane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		docTitle = new JLabel("Document Viewer");
		resultsLabel = new JLabel("Results");
		docTree = new JTree(new DefaultMutableTreeNode(null));
		treeScroll = new JScrollPane(docTree,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		searchViewButton = new JButton("Search View");
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll,
				textScroll);
		titlePanel = new JPanel();
		group = new ButtonGroup();
		suggestPanel = new JPanel();

		searchButton.setName("searchButton");
		searchViewButton.setName("searchView");
		searchField.getDocument().addDocumentListener(new MyDocumentListener());

		// JFrame
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Wall Street Journal Viewer");
		setMinimumSize(new Dimension(480, 360));
		setName("mainFrame"); // NOI18N
		setResizable(true);
		setPreferredSize(new Dimension(720, 480));

		headLineBox.addActionListener(new radioListener());
		leadParBox.addActionListener(new radioListener());
		bodyBox.addActionListener(new radioListener());
		tagsBox.addActionListener(new radioListener());
		allBox.addActionListener(new radioListener());
		headLineBox.setActionCommand("headline");
		leadParBox.setActionCommand("leadPar");
		bodyBox.setActionCommand("body");
		tagsBox.setActionCommand("in");
		allBox.setActionCommand("contents");
		suggestLabel.setPreferredSize(new Dimension(250, 100));
		docPane.setContentType("text/html");

		suggestPanel.setPreferredSize(new Dimension(500, 200));
		suggestPanel.add(suggestLabel);
		searchHolder.add(searchField, BorderLayout.PAGE_END);
		searchHolder.add(searchButton, BorderLayout.PAGE_END);
		// fieldsHolder.setLayout(new FlowLayout());
		fieldsHolder.add(headLineBox, BorderLayout.NORTH);
		fieldsHolder.add(leadParBox, BorderLayout.NORTH);
		fieldsHolder.add(bodyBox, BorderLayout.NORTH);
		fieldsHolder.add(tagsBox, BorderLayout.NORTH);
		fieldsHolder.add(allBox, BorderLayout.NORTH);
		// fieldsHolder.add(suggestLabel, BorderLayout.SOUTH);
		group.add(headLineBox);
		group.add(leadParBox);
		group.add(tagsBox);
		group.add(allBox);
		group.add(bodyBox);

		titleLabel.setPreferredSize(new Dimension(300, 150));
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);

		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
		searchPanel.add(titleLabel);
		searchPanel.add(searchHolder);
		searchPanel.add(fieldsHolder);
		searchPanel.add(suggestPanel);
		searchPanel.setPreferredSize(this.getPreferredSize());

		resultsLabel.setPreferredSize(new Dimension(200, 50));
		docTitle.setPreferredSize(new Dimension(200, 50));
		titlePanel.setPreferredSize(new Dimension(500, 50));
		titlePanel.add(resultsLabel);
		titlePanel.add(docTitle);

		viewPanel.setPreferredSize(new Dimension(720, 480));

		docTree.setEditable(false);
		docTree.setName("docTree");
		docTree.setRowHeight(0);
		docTree.setCellRenderer(new ResultsCellRenderer());
		docTree.addTreeSelectionListener(this);

		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);
		splitPane.setPreferredSize(new Dimension(620, 330));
		Dimension minimumSize = new Dimension(100, 100);
		treeScroll.setMinimumSize(minimumSize);
		treeScroll.setViewportView(docTree);
		textScroll.setMinimumSize(minimumSize);
		textScroll.setViewportView(docPane);

		viewPanel.add(titlePanel, BorderLayout.PAGE_START);
		viewPanel.add(splitPane, BorderLayout.CENTER);
		viewPanel.add(searchViewButton, BorderLayout.PAGE_END);
		// setSearchVisible();

		searchButton.addActionListener(new searchButtonListener());
		searchViewButton.addActionListener(new searchViewListener());

		setSearchVisible();

		this.add(searchPanel);
		this.add(viewPanel);

		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}

	public void setSearchVisible() {

		getContentPane().removeAll();
		getContentPane().add(searchPanel);
		docTree.setModel(null);
		docPane.setText("");
		repaint();
		pack();
	}

	public void setViewVisible() {
		getContentPane().removeAll();
		getContentPane().add(viewPanel);
		repaint();
		pack();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SearchGUI();
			}
		});

	}

	@Override
	public void valueChanged(TreeSelectionEvent evt) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) docTree
				.getLastSelectedPathComponent();

		if (node == null)
			return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf() && nodeInfo instanceof Article) {
			Article art = (Article) nodeInfo;
			docPane.setText(art.displayInfo());
		} else if (node == null || node.isRoot()) {
			docPane.setText("No Results");
		}

		docPane.updateUI();
	}

	public void populateTree() {
		DefaultMutableTreeNode node = buildNodeFromString();
		DefaultTreeModel model = new DefaultTreeModel(node);
		docTree.setModel(model);
		docTree.updateUI();
	}

	public DefaultMutableTreeNode buildNodeFromString() {
		DefaultMutableTreeNode node = null, root = null;
		root = new DefaultMutableTreeNode("Query: " + searchField.getText());
		for (Article key : articles) {
			node = new DefaultMutableTreeNode(key);
			root.add(node);
		}
		return root;
	}

	class searchButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (searchField.getText().length() < 2) {
				JOptionPane
						.showConfirmDialog(
								searchPanel,
								"You must enter a valid query to be able to perform a search.",
								"Search", JOptionPane.WARNING_MESSAGE,
								JOptionPane.OK_OPTION);
				searchField.setText("");
			} else {
				String[] fields = { action };
				system.search(searchField.getText(), fields);
				articles = system.getResults();
				populateTree();
				setViewVisible();
			}
		}

	}

	class searchViewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setSearchVisible();

		}

	}

	class radioListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			action = e.getActionCommand();
		}

	}

	class MyDocumentListener implements DocumentListener {
		String newline = "\n";

		public void insertUpdate(DocumentEvent e) {
			updateLog(e);
		}

		public void removeUpdate(DocumentEvent e) {
			updateLog(e);
		}

		public void changedUpdate(DocumentEvent e) {
			// Plain text components do not fire these events
		}

		public void updateLog(DocumentEvent e) {
			Document doc = (Document) e.getDocument();

			try {
				String s = doc.getText(0, doc.getLength());
				if (s.length() > 0) {
					String suggestions[] = system.suggestions(s);
					String temp = "<html>";
					if (suggestions != null && suggestions.length > 0) {
						for (String word : suggestions) {
							temp = temp + "Did you mean: " + word + "<br>";
						}
						temp = temp + "</html>";
						suggestLabel.setText(temp);
						//repaint();
					} else {
						temp = "No suggestions found for word:" + s;
					}
				} else {
					suggestLabel.setText("");
					//repaint();
				}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}
