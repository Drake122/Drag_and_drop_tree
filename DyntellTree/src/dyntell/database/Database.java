package dyntell.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.PreparedStatement;

import dyntell.model.person.Person;


public class Database {
	 private static java.sql.PreparedStatement ps;
	 private ResultSet rs;
	
	public static Connection getConnection() {
        try  {
      	 
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/points",
                    "root","");
            System.out.println("Worked");
            return con;
        }
        catch(Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }

     public static void close(Connection con) {
        try  {
            System.out.println("Closed");
            con.close();
            
        }
        catch(Exception ex) {
        }
    }
     
    public static void updateToDatabase(int idAkt, int idParent) {
    	
    	 try {             
             ps=getConnection().prepareStatement("UPDATE points SET id_parent = ? WHERE id=?");
             ps.setInt(1, idParent);
             ps.setInt(2, idAkt);
             ps.executeUpdate();
             ps.close();
             
              
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
 		
    	 close(getConnection());
 	}

	public static void delete(int id) {
		 try {             
             ps=getConnection().prepareStatement("DELETE FROM points WHERE id=?");
             ps.setInt(1, id);             
             ps.executeUpdate();
             ps.close();            
              
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
		 close(getConnection());	
		
	}

	public static void updateNamePointToDatabase(int id, String nameTo, Double point2) {
		 try {             
             ps=getConnection().prepareStatement("UPDATE points SET name = ?, point = ? WHERE id=?");
             ps.setString(1, nameTo);
             ps.setDouble(2, point2);
             ps.setInt(3, id);
             ps.executeUpdate();
             ps.close();
             
              
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
 		
		close(getConnection());
	}

	public static void addElem(int idWhere, String name3, Double pointd3) {
		 try {             
             ps=getConnection().prepareStatement("INSERT INTO `points`(`id_parent`, `name`, `point`) VALUES (?,?,?)");
             ps.setInt(1, idWhere);
             ps.setString(2, name3);
             ps.setDouble(3, pointd3);
             ps.executeUpdate();
             ps.close();
             
              
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
 		
		close(getConnection());		
	}

	public static Integer searchId(int id_parent, String name3) {
		 int id=0;
		try{
	    	 Statement stm = null;
			try {
	             Connection con = getConnection();
	            
	             stm = con.createStatement();
	         } catch (Exception ex) {
	             ex.printStackTrace();
	         }
	    	 String sql = "SELECT `id` FROM `points` WHERE (`name`='"+name3+"' AND `id_parent` ="+id_parent+")";
	    	 ResultSet rs = stm.executeQuery(sql);
	         while (rs.next()) {
	        	id = rs.getInt(1);  
	        	System.out.println("id: " +id);
	        	    	         	 
	        	 	      	 
	         }         
	    	} catch (Exception ex) {
	            ex.printStackTrace();
	        }
		
	
		close(getConnection());
		return id;
		
		 }
}