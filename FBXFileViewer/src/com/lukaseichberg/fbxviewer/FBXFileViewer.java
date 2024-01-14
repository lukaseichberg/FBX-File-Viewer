package com.lukaseichberg.fbxviewer;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.lukaseichberg.menubuilder.MenuBuilder;

public class FBXFileViewer {
	
	private static JFrame frame;
	static JTree tree;

	public static void start() {
		tree = new JTree(new DefaultTreeModel(null));
		ActionHandler actionHandler = new ActionHandler();

		JMenuBar menuBar = null;
		try {
			menuBar = MenuBuilder.fromTemplate("res/menu.xml", actionHandler);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		JScrollPane pane = new JScrollPane(tree);
		
		frame = new JFrame("FBX File Viewer");
		frame.setLayout(new BorderLayout());
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(menuBar, BorderLayout.NORTH);
		frame.add(pane, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
