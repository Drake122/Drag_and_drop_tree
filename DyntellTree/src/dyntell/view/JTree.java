package dyntell.view;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;


import dyntell.model.tree.DNDTreeHandler;

public class JTree extends JScrollPane{
	
	 static DefaultMutableTreeNode root1 = DNDTreeHandler.createTree();
     static DNDTreeHandler tree1 = new DNDTreeHandler(root1);
      static JScrollPane onLoaded;
     static String lastPathComponent ="";
     static String lastPathComponentParent = "";
     static NewJPanelTáblázat table;

	public static  JScrollPane onLoad(NewJPanelTáblázat table){
		tableDraw();// tábla hozzáadása
		tree1.setFocusable(true); 
		tree1.setBackground(Color.white);
		tree1.addMouseListener(new MouseAdapter(){//egéresemény hozzáadása
   		 public void mouseClicked(MouseEvent me) {
             doMouseClicked(me);
           }

					private void doMouseClicked(MouseEvent me) {
						
						 TreePath tp = tree1.getPathForLocation(me.getX(), me.getY());
						    if (tp != null){
						    	selectionBackground.darker();
						    	tree1.setSelectionPath(tp);
						    	lastPathComponent = tp.getLastPathComponent().toString();
						    	if(lastPathComponent.equals("")){
						    		lastPathComponent ="root";
						    		lastPathComponentParent = "root";
						    	}else{
						    		lastPathComponentParent = tp.getParentPath().getLastPathComponent().toString();
						    		if(lastPathComponentParent.equals("")){
						    			lastPathComponentParent="root";
						    		}
						    	}
						    	
						    	table.getjTable1().setModel(tableModelLoad());
						    	table.getjTable1().repaint();				                   
						    }					
					}
		});	
          
		 onLoaded = new JScrollPane(tree1);
		return onLoaded;
		
	}
	static DefaultTableModel tableModelLoad() {
		//System.out.println("tree1 lastpath: "+lastPathComponent +" parent: " + lastPathComponentParent );
		DefaultTableModel dm = new DefaultTableModel();
		dm = DNDTreeHandler.loadTableModel(lastPathComponent,lastPathComponentParent);	
		//System.out.println("dm: "+dm.getRowCount());
		return dm;
	}

	static void tableDraw() {
		tree1.setCellRenderer(new MyCellRenderer());		
	}

	public static Color selectionBackground = Color.lightGray;
     
	static class MyCellRenderer implements TreeCellRenderer {		
		@Override
	    public Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean selected, boolean expanded,
	            boolean leaf, int row, boolean hasFocus)  {
			 String nameNodeParent="";
	        String nameNode = (String) ((DefaultMutableTreeNode) value).getUserObject();
	       Object[]szülõ = ((DefaultMutableTreeNode) value).getUserObjectPath();
	       ArrayList<Object> parentPath = new ArrayList<>(Arrays.asList(szülõ));
	    
	    	if(parentPath.size()==1){
	    		  nameNodeParent="root"; 
	    		  nameNode="root";
	    	}else{
	    		
	    	   if(parentPath.size()==2){
	    		  nameNodeParent="root";  
	    	  }else{
	    		  nameNodeParent = (String) parentPath.get(parentPath.size()-2);
	    	  }
	    	}	       
			String countPoint = tree1.countPoint(nameNode,nameNodeParent);
	        final String[] params = {nameNode,countPoint};
	        JTable table = new JTable();
	       // table.setCellSelectionEnabled(selected);
	        table.setSelectionBackground(selectionBackground);
	        table.setAutoCreateRowSorter(true);
	        table.setModel(new DefaultTableModel() {
	            private static final long serialVersionUID = 1L;

	            @Override
	            public int getRowCount() {
	                return 1;
	                }

	            @Override
	            public int getColumnCount() {
	                return 2;
	            }

	            @Override
	            public Object getValueAt(int row, int column) {
	            	 return params[column];
	            }
	        });
	        return table;
	    }		
	} 
}
