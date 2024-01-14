package com.lukaseichberg.menubuilder;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MenuBuilder {
	
	public static JMenuBar fromTemplate(String filePath, ActionListener actionListener) throws SAXException, IOException, ParserConfigurationException {
		JMenuBar menuBar = new JMenuBar();
		
		File file = new File(filePath);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		NodeList menus = document.getElementsByTagName("menu");
		
		for (int i = 0; i < menus.getLength(); i++) {
			Node menu = menus.item(i);
			NodeList nodes = menu.getChildNodes();
			
			NamedNodeMap menuAtt = menu.getAttributes();
			Node menuName = menuAtt.getNamedItem("name");
			JMenu jmenu = new JMenu(menuName.getNodeValue());
			
			for (int j = 0; j < nodes.getLength(); j++) {
				Node item = nodes.item(j);
				if (item.getNodeName().equalsIgnoreCase("item")) {
					NamedNodeMap itemAtt = item.getAttributes();
					Node itemName = itemAtt.getNamedItem("name");
					Node itemAction = itemAtt.getNamedItem("action");
					
					JMenuItem jitem = new JMenuItem(itemName.getNodeValue());
					jitem.setActionCommand(itemAction.getNodeValue());
					jitem.addActionListener(actionListener);
					jmenu.add(jitem);
				}
			}
			menuBar.add(jmenu);
		}
		
		
		return menuBar;
	}

}
