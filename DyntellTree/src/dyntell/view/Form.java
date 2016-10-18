package dyntell.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import dyntell.model.table.TableHandler;
import dyntell.model.tree.DNDTreeHandler;

public class Form extends javax.swing.JFrame{
	DNDTreeHandler dndtree;
	TableHandler tableHandler;
	//  JScrollPane onLoad;
	public Form() {
	        initComponents();
	    }
	 
	 private void initComponents() {
		 try {
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
             JFrame frame = new JFrame();
             Container contentPane  = frame.getContentPane();
             contentPane.setLayout(new GridLayout(1,2));           
             NewJPanelTáblázat tablazat = new NewJPanelTáblázat(dndtree);            
			contentPane.add(JTree.onLoad(tablazat));           
             contentPane.add(tablazat);           
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setSize(700,400);
             String title = "Dyntel_task";
             frame.setTitle(title );
             frame.setLocation(400, 100);
             frame.setVisible(true);
             
		 }
		 catch(Exception e){
			 System.out.println(e);
		 }
	 }
}
       
             
          /*   tablazat.getjTable1().repaint();*/
             
        
       
        
	 
   
	 

