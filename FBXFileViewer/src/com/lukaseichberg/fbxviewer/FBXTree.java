package com.lukaseichberg.fbxviewer;

import javax.swing.tree.DefaultMutableTreeNode;

import com.lukaseichberg.fbxloader.FBXDataType;
import com.lukaseichberg.fbxloader.FBXFile;
import com.lukaseichberg.fbxloader.FBXNode;
import com.lukaseichberg.fbxloader.FBXProperty;

public class FBXTree {

	public static DefaultMutableTreeNode generate(FBXFile file) {
		FBXNode rootNode = file.getRootNode();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("[FBXFile] "+rootNode.getName());
		root.add(new DefaultMutableTreeNode("[FBXFileHeader] Kaydara FBX Binary  [0x1A, 0x00] "+file.getVersion()));
		
		int count = rootNode.getNumChildren();
		for (int i = 0; i < count; i++) {
			root.add(genTreeNode(rootNode.getChild(i), i));
		}
		return root;
	}
	
	private static DefaultMutableTreeNode genTreeNode(FBXNode node, int index) {
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("[FBXNode "+index+"] "+node.getName());
		
		int propertyCount = node.getNumProperties();
		for (int i = 0; i < propertyCount; i++) {
			treeNode.add(genTreeProperty(node.getProperty(i), i));
		}
		int nodeCount = node.getNumChildren();
		for (int i = 0; i < nodeCount; i++) {
			treeNode.add(genTreeNode(node.getChild(i), i));
		}
		return treeNode;
	}
	
	private static DefaultMutableTreeNode genTreeProperty(FBXProperty property, int index) {
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("[FBXProperty "+index+"] "+getDataString(property));
		if (property.getDataType().isArray() || property.getDataType() == FBXDataType.RAW) {
			String[] data = getDataStringArray(property);
			for (String d:data) {
				DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(d);
				treeNode.add(dataNode);
			}
		}
		return treeNode;
	}

	public static String getDataString(FBXProperty property) {
		FBXDataType dataType = property.getDataType();
		Object data = property.getData();
		
		switch (dataType) {
		case SHORT:
			return "short: "+(short)data;
		case BOOLEAN:
			return "boolean: "+(boolean)data;
		case INT:
			return "int: "+(int)data;
		case FLOAT:
			return "float: "+(float)data;
		case DOUBLE:
			return "double: "+(double)data;
		case LONG:
			return "long: "+(long)data;
		case FLOAT_ARRAY:
			return "float["+((float[])data).length+"]";
		case DOUBLE_ARRAY:
			return "double["+((double[])data).length+"]";
		case LONG_ARRAY:
			return "long["+((long[])data).length+"]";
		case INT_ARRAY:
			return "int["+((int[])data).length+"]";
		case BOOLEAN_ARRAY:
			return "boolean["+((boolean[])data).length+"]";
		case STRING:
			return "String("+((String)data).length()+"): \""+(String)data+"\"";
		case RAW:
			return "RAW("+((byte[])data).length+")";
		default:
			return null;
		}
	}
	
	public static String[] getDataStringArray(FBXProperty property) {
		FBXDataType dataType = property.getDataType();
		Object data = property.getData();
		
		if (!dataType.isArray() && dataType != FBXDataType.RAW) {
			return null;
		}
		
		String[] stringArray;
		
		switch (dataType) {
		case FLOAT_ARRAY:
			float[] floats = (float[]) data;
			stringArray = new String[floats.length];
			for (int i = 0; i < floats.length; i++) {
				stringArray[i] = "["+i+"]: "+Float.toString(floats[i]);
			}
			return stringArray;
			
		case DOUBLE_ARRAY:
			double[] doubles = (double[]) data;
			stringArray = new String[doubles.length];
			for (int i = 0; i < doubles.length; i++) {
				stringArray[i] = "["+i+"]: "+Double.toString(doubles[i]);
			}
			return stringArray;
			
		case LONG_ARRAY:
			long[] longs = (long[]) data;
			stringArray = new String[longs.length];
			for (int i = 0; i < longs.length; i++) {
				stringArray[i] = "["+i+"]: "+Long.toString(longs[i]);
			}
			return stringArray;
			
		case INT_ARRAY:
			int[] ints = (int[]) data;
			stringArray = new String[ints.length];
			for (int i = 0; i < ints.length; i++) {
				stringArray[i] = "["+i+"]: "+Integer.toString(ints[i]);
			}
			return stringArray;
			
		case BOOLEAN_ARRAY:
			boolean[] bools = (boolean[]) data;
			stringArray = new String[bools.length];
			for (int i = 0; i < bools.length; i++) {
				stringArray[i] = "["+i+"]: "+Boolean.toString(bools[i]);
			}
			return stringArray;
			
		case RAW:
			byte[] bytes = (byte[]) data;
			stringArray = new String[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				stringArray[i] = String.format("%1$08X",i)+": 0x"+Integer.toString(bytes[i] & 0xFF, 16).toUpperCase();
			}
			return stringArray;
			
		default:
			return null;
			
		}
	}
}
