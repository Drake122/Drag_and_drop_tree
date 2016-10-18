package dyntell.model.tree;


	import java.util.*;
	import java.util.List;
	import java.awt.*;
	import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

import dyntell.database.Database;
import dyntell.model.element.Element;
import dyntell.model.person.Person;
import dyntell.view.NewJPanelTáblázat;

import java.awt.dnd.*;
	import java.sql.*;


	public class DNDTreeHandler extends JTree{

	     /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Insets autoscrollInsets = new Insets(20, 20, 20, 20); // insets
	      static Connection con = null;
	      static Statement stm = null;
	      static private ArrayList<Person> personListFromDatabase = new ArrayList<Person>();
	     // NewJPanelMidle panelKozep ;
	      NewJPanelTáblázat table;
	     
	      
	     public DNDTreeHandler(DefaultMutableTreeNode root) {
	          setAutoscrolls(true);
	          
	       
	                  
	          DefaultTreeModel treemodel = new  DefaultTreeModel(root);
	          
	        // treemodel.addTreeModelListener(treeModelListener);//child hozzáadása után 
	          
	          setModel(treemodel);
	        
			setIgnoreRepaint(false);
	          setRootVisible(true); 
	          setShowsRootHandles(false);//to show the root icon
	          getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //set single selection for the Tree
	          setEditable(false);
	          new DefaultTreeTransferHandler(this, DnDConstants.ACTION_COPY_OR_MOVE);
	     }
	     
	    

	     public static ArrayList<Person> getPersonListFromDatabase() {
			return personListFromDatabase;
		}



		public static void setPersonListFromDatabase(ArrayList<Person> personListFromDatabase) {
			DNDTreeHandler.personListFromDatabase = personListFromDatabase;
		}



		public void autoscroll(Point cursorLocation)  {
	    
	          Insets insets = getAutoscrollInsets();
	          Rectangle outer = getVisibleRect();
	          Rectangle inner = new Rectangle(outer.x+insets.left, outer.y+insets.top, outer.width-(insets.left+insets.right), outer.height-(insets.top+insets.bottom));
	          if (!inner.contains(cursorLocation))  {
	               Rectangle scrollRect = new Rectangle(cursorLocation.x-insets.left, cursorLocation.y-insets.top,     insets.left+insets.right, insets.top+insets.bottom);
	               scrollRectToVisible(scrollRect);
	          }
	     }

	     public Insets getAutoscrollInsets()  {
	          return (autoscrollInsets);
	     }

	     public DefaultMutableTreeNode makeDeepCopy(DefaultMutableTreeNode node) {
	          DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
	          for (Enumeration e = node.children(); e.hasMoreElements();) {     
	               copy.add(makeDeepCopy((DefaultMutableTreeNode)e.nextElement()));
	          }
	          return(copy);
	     }
	     
	    
	     
	     private static  List<String[]> data = new ArrayList<String[]>();	 
	
	     
	     static Collection<Element> getElementTreeFromPlainList() { 	
	    	
	    	dataFromDatabase(personListFromDatabase);	  //adat betöltése az adatbázisból Listába      	
	         
	         dataUploadToTreeData(personListFromDatabase);	 //adatok betöltése a fához a Listából   	 
	    	 
	         // builds a map of elements object returned from store
	         Map<String, Element> values = new HashMap<String, Element>();
	         for (String[] s : data) {
	             values.put(s[0], new Element(s[2], s[1]));
	         }

	         // creates a result list
	         Collection<Element> result = new ArrayList<Element>();

	         // for each element in the result list that has a parent, put it into it
	         // otherwise it is added to the result list
	         for (Element e : values.values()) {
	             if (e.getParent() != null) {
	                 values.get(e.getParent()).getChildren().add(e);
	             } else {
	                 result.add(e);
	             }
	         }

	         return result;
	     }

		public static void dataUploadToTreeData(ArrayList<Person> personListFromDatabase) {
			try {
	        	 String id="";
	        	 String name ="";
	        	 String id_parent;
	        	 System.out.println("dataUploadToTreeData: "+ personListFromDatabase.toString());
				for(Person a: personListFromDatabase){
					id = String.valueOf(a.getId());
					name = a.getName();
					id_parent = (a.getId_Parent()==0 ) ? null:String.valueOf(a.getId_Parent());
										
						data.add(new String[] { id, name, id_parent });					
					 
				 }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public static void dataFromDatabase(ArrayList<Person> personListFromDatabase) {
			try{
	    	 try {
	             con = Database.getConnection();
	            
	             stm = con.createStatement();
	         } catch (Exception ex) {
	             ex.printStackTrace();
	         }
	    	 String sql = "SELECT * from points";
	    	 ResultSet rs = stm.executeQuery(sql);
	         while (rs.next()) {
	        	 int id = rs.getInt(1);        	
	        	 int id_Parent = rs.getInt(2);
	        	 String name = rs.getString(3);
	        	 Double point = rs.getDouble(4);        	         	 
	        	Person person = new Person(id, id_Parent, name, point);
	        	personListFromDatabase.add(person) ;        	      	 
	         }         
	    	} catch (Exception ex) {
	            ex.printStackTrace();
	        }
		}
	 
	     static void createTreeNodesForElement(final DefaultMutableTreeNode dmtn, final Collection<Element> elements) {
	         // for each element a tree node is created
	         for (Element child : elements) {
	             DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
	             dmtn.add(created);
	             createTreeNodesForElement(created, child.getChildren());
	         }
	     }

	     public static DefaultMutableTreeNode createTree() {
	    	  DefaultMutableTreeNode root = new DefaultMutableTreeNode();	    	  
	    	  createTreeNodesForElement(root, getElementTreeFromPlainList());	    	          
			return(root);
	     }
	

		public String countPoint(String nameNode, String nameNodeParent) {//pont számolás
			String countPoint="";
			int searchID = -1;
			//int searchIdParent = -1;
			for(Person person : personListFromDatabase){
				if(person.getName().equals(nameNode) ){
					if(nameNodeParent.equals("root")){
						searchID = person.getId();
						countPoint = "pont: " +count(searchID);
					}else{
						countPoint = counPointIfParentNotRoot(nameNodeParent, countPoint, person);						
					}
				}
			}
			return countPoint;
		}



		private String counPointIfParentNotRoot(String nameNodeParent, String countPoint, Person person) {
			int searchID;
			for(Person person2 : personListFromDatabase){											
				if(person2.getName().equals(nameNodeParent)&& person2.getId() == person.getId_Parent()){
					searchID = person.getId();
					countPoint = "pont: " +count(searchID);
				}						
			}
			return countPoint;
		}



		Double NodeAvarage=0.0;
		private Double count(int id) {
			
			for(Person person : personListFromDatabase){
				if(person.getId()==id){
					if(person.getPoint()==0){
						szamol(person);						
					}else{						
						NodeAvarage= person.getPoint();
					}
				}
			}			
			return NodeAvarage;		
		}



		private void szamol(Person person) {
			Double összeg=0.0;
			int darabSzam=0;
			int childs = 0;
			for(Person person2 : personListFromDatabase){
				if(person2.getId_Parent()==person.getId()){
					childs++;
					darabSzam++;
					összeg=összeg+count(person2.getId());					
				}			
           }  
			if(childs==0){
				NodeAvarage=0.0;
			}else{
				NodeAvarage= összeg/darabSzam;					
			}
			
		}	 
		
		public static DefaultTableModel loadTableModel(String nameNode, String nameNodeParent){
			 String[] header = {"Name","Point"};			
			 DefaultTableModel model = new DefaultTableModel(header,0);
			 Object[] obj = new Object[2];
			 int searchID=0;
			// int szamol =0;
			 for(Person person : personListFromDatabase){
					if(person.getName().equals(nameNode) ){
						if(nameNodeParent.equals("root")){
							searchID = person.getId();
							//szamol++;
						}else{
							for(Person person2 : personListFromDatabase){											
								if(person2.getName().equals(nameNodeParent)&& person2.getId() == person.getId_Parent()){
									searchID = person.getId();	
								//	szamol++;
								}						
							}												
						}
					}
				}
			
			// System.out.println("idSearch: " +searchID);
			 for(Person person : personListFromDatabase){
				 if(person.getId_Parent()==searchID){					 
					 obj[0]= person.getName();
					 obj[1]= person.getPoint();
					 model.addRow(obj);
				 }
			 }			  
			return model;			
		}		
		
	}


