package dyntell.model.tree;
import java.awt.*;
import javax.swing.tree.*;

import dyntell.controller.tree.AbstractTreeTransferHandler;
import dyntell.database.Database;
import dyntell.model.person.Person;

import java.awt.dnd.*;
import java.util.ArrayList;

public class DefaultTreeTransferHandler extends AbstractTreeTransferHandler {
	
	 
     public DefaultTreeTransferHandler(DNDTreeHandler tree, int action) {
          super(tree, action, true);
     }

     //végrehajtható e amûvelet
     public boolean canPerformAction(DNDTreeHandler target, DefaultMutableTreeNode draggedNode, int action, Point location) {
          TreePath pathTarget = target.getPathForLocation(location.x, location.y);
          
          if (pathTarget == null) {
               target.setSelectionPath(null);
          	 
               return(false);
          }
          target.setSelectionPath(pathTarget);
          if(action == DnDConstants.ACTION_COPY) {
               return(true);
          }
          else
          if(action == DnDConstants.ACTION_MOVE) {     
               DefaultMutableTreeNode parentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();                    
               if (draggedNode.isRoot() || parentNode == draggedNode.getParent() || draggedNode.isNodeDescendant(parentNode)) {                         
                    return(false);     
               }
               else {
                    return(true);
               }                     
          }
          else {          
               return(false);     
          }
     }

     public boolean executeDrop(DNDTreeHandler target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 
          if (action == DnDConstants.ACTION_COPY) {
               DefaultMutableTreeNode newNode = target.makeDeepCopy(draggedNode);
               ((DefaultTreeModel)target.getModel()).insertNodeInto(newNode,newParentNode,newParentNode.getChildCount());
               TreePath treePath = new TreePath(newNode.getPath());
               target.scrollPathToVisible(treePath);
               target.setSelectionPath(treePath);   
               
               return(true);
          }
          if (action == DnDConstants.ACTION_MOVE) {
               draggedNode.removeFromParent();
               ((DefaultTreeModel)target.getModel()).insertNodeInto(draggedNode,newParentNode,newParentNode.getChildCount());
               TreePath treePath = new TreePath(draggedNode.getPath());
               target.scrollPathToVisible(treePath);
               target.setSelectionPath(treePath);
             
            //  dndtree.personListFromDatabase;
               String parentNode = treePath.getPathComponent(treePath.getPath().length-2).toString();
			System.out.println("parent: "+ parentNode.toString());
			   String aktNodeName = treePath.getPathComponent(treePath.getPath().length-1).toString();
			System.out.println("akt node name: "+aktNodeName.toString());
              saveToDatabase(parentNode,aktNodeName); //drag and dropnál update meghívása a database-be
               return(true);
               
          }
          return(false);
     }

	private void saveToDatabase(String parentNode, String aktNodeName) {
		//DNDTree dndtree = null;
		ArrayList<Person>personList = new ArrayList<>(DNDTreeHandler.getPersonListFromDatabase());
		System.out.println("personList: " + personList);
		for(Person personakt: personList){//akt person
			if(personakt.getName().equals(aktNodeName)){//parent person
				int idAkt = personakt.getId();
				for(Person personParent: personList){
					if(personParent.getName().equals(parentNode)){
						int idParent = personParent.getId();
						personakt.setId_Parent(idParent);
						Database.updateToDatabase(idAkt, idParent); //update
					}
				}
			}
		}
		System.out.println("personList: " + personList);		
	}

	
}