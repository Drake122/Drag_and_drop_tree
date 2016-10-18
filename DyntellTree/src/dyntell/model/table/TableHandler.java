package dyntell.model.table;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import dyntell.model.person.Person;
import dyntell.model.tree.DNDTreeHandler;
import dyntell.view.JTree;
import dyntell.view.NewJPanelTáblázat;

public class TableHandler extends DNDTreeHandler {

	public TableHandler(DefaultMutableTreeNode root) {
		super(root);
		
	}
	 ArrayList<Integer> delList = new ArrayList<>();
	 NewJPanelTáblázat table;
	 
	/*public void deleteClickTable(String name, Double pont) {
		try {
			int searchId = 0;
			System.out.println("belemegy");
			for(Person person: super.getPersonListFromDatabase()){
					if((person.getName().equals(name)) && (person.getPoint()==pont)){
					searchId = person.getId();
				}
					System.out.println(getPersonListFromDatabase().get(searchId).toString());
					//personListFromDatabase.remove(searchId);
					
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}*/
	
	
	
	  public void deleteClickTable(String name2, Double pont2) {
		  System.out.println("tablehand kezd");
	    	delList.clear();
	    	int id = 0;
	    	int index=-1;
	    	int searchIndex=0;
	    	for(Person person: super.getPersonListFromDatabase()){
	    		index++;
				if((person.getName().equals(name2)) && (person.getPoint()==pont2)){
				searchIndex = index;
				id = person.getId();
				//dndtree.getPersonListFromDatabase().get(index);
			}
				//personListFromDatabase.remove(searchId);
		}
	    	
	    	delList.add(id);
	    	searchChilds(delList);
	    	System.out.println("delList: " + delList.toString());
	    	if(delList.size()>1){
	    		 int p= JOptionPane.showConfirmDialog(table.jPanel1, "A mappa nem üres, ha törlöd minden benne lévõ adat elvész!  Így is törölni akarod ?", "Töröl", JOptionPane.YES_NO_OPTION);
	    	       if(p == 0){
	    	          for(int j = 0 ; j < delList.size(); j++){
	    	        	 // Database.delete(delList.get(j)); //törlés adatbázisból
	    	        	  System.out.println("id: "+ delList.get(j));
	    	        	  int indexPerson=-1;
	    	        	  int delIndex = -1;
	    	        	 for(Person person : super.getPersonListFromDatabase()){
	    	        		 indexPerson++;
	    	        		 if(person.getId()== delList.get(j)){
	    	        			delIndex = indexPerson;
	    	        		 }
	    	        	 }
	    	        	 super.getPersonListFromDatabase().remove(delIndex);
	    	          }
	    	           JOptionPane.showMessageDialog(table.jPanel1, "Töröltem");
	    	       }
	    		
	    	}else{
	    		//Database.delete(id);
	    		super.getPersonListFromDatabase().remove(searchIndex) ;
	    		
	    	}    	
	    	
	    	System.out.println(super.getPersonListFromDatabase().toString());
	    	/*setTableModel((JTree.tableModelLoad()));
	    	jTable1.setModel(tableModel);	*/	
	    	//Form.initComponents();    	
	    }

		private void searchChilds(ArrayList<Integer> delList) {
			for(int i= 0; i < delList.size(); i++){
			for(Person person : super.getPersonListFromDatabase()){
				if(person.getId_Parent()==delList.get(i)){
					delList.add(person.getId());				
				}
			}
			}
}
		
}
