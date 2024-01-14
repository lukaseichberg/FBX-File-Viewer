package com.lukaseichberg.fbxviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;

import com.lukaseichberg.fbxloader.FBXFile;
import com.lukaseichberg.fbxloader.FBXLoader;

public class ActionHandler implements ActionListener {
	
	private File prevDir;

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "FILE_OPEN":
			JFileChooser chooser = new JFileChooser();
			if (prevDir != null) {
				chooser.setCurrentDirectory(prevDir);
			}
			FileNameExtensionFilter filter = new FileNameExtensionFilter("FBX File", "fbx");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(FBXFileViewer.tree);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filePath = chooser.getSelectedFile().getAbsolutePath();

				try {
					FBXFile file = FBXLoader.loadFBXFile(filePath);
					DefaultTreeModel model = new DefaultTreeModel(FBXTree.generate(file));
					FBXFileViewer.tree.setModel(model);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			prevDir = chooser.getCurrentDirectory();
			break;
		
//		case "EXPORT_OBJ":
//			exportOBJ();
//			break;
		
		case "EXIT":
			System.exit(0);
			break;
			
		case "INFO":
			File aboutFile = new File("res/about.txt");
			String text = null;
			try {
				text = Files.readString(aboutFile.toPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, text, "About", JOptionPane.PLAIN_MESSAGE);
			break;
		}
	}

}
