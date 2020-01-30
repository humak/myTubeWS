package myRESTwsDatabase;


import myRESTwsData.Item;
import myRESTwsData.Server;
import myRESTwsData.User;

import java.rmi.Remote;
import java.sql.*;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBase implements Remote {
    
    public static int add(String name, String description, Integer owner, Integer server, String path) throws SQLException, ClassNotFoundException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();

        String sql;
        Statement stmt = null;

        c.setAutoCommit(false);
        stmt = c.createStatement();
        sql = "INSERT INTO public.items (name, description, owner, server, path) \n VALUES (\'";
        sql = sql + name +"\',\'" + description +"\',"+ owner + ","+ server + ",\'"+ path +"\') RETURNING key;";

        ResultSet rs = stmt.executeQuery( sql );
        rs.next();
        int i = (rs.getInt("key"));
        stmt.close();
        c.commit();
        c.close();
        return (i);
    }

    public static void update_path(int key, String path) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();

        String sql;
        Statement stmt = null;

        c.setAutoCommit(false);

        stmt = c.createStatement();

        sql = "UPDATE public.items\n" +
                "\tSET path=\'"+path +"\'"+"\n" +
                "\tWHERE key =" + key + ";";

        stmt.executeUpdate(sql);
        c.commit();
        stmt.close();
        c.close();
    }

    public static ArrayList<Item> getAll( ) throws ClassNotFoundException, SQLException, NamingException {
    	
        
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();

        Statement stmt = null;

        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS;" );
        ArrayList<Item> result = new ArrayList<>();
        Integer i =0;
        while ( rs.next() ) {	           
            result.add(new Item(i, rs.getString("name"), rs.getString("description"),  (Integer)rs.getInt("owner"),  rs.getString("path"), (Integer)rs.getInt("server")));
            i++;
        }
        rs.close();
        stmt.close();
        c.close();
        return result;

    }

    public static ArrayList<Item> search(String search_key) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        Statement stmt = null;
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE name ilike \'%"+search_key+"%\' or description ilike \'%"+search_key+"%\' ;" );
        ArrayList<Item> result = new ArrayList<>();
        
        while ( rs.next() ) {
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"), rs.getInt("server")));            
        }
        rs.close();
        stmt.close();
        c.close();

        return result;
    }
    
    public static ArrayList<Item> searchByOwner(String search_key, String user_id) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE ( name ilike \'%"+search_key+"%\' OR description ilike \'%"+search_key+"%\' ) AND owner = "+user_id+" ;" );
        ArrayList<Item> result = new ArrayList<>();
        
        while ( rs.next() ) {
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"), rs.getInt("server")));
        }
        rs.close();
        stmt.close();
        c.close();
        
        return result;
    }
    
    public static ArrayList<Item> searchByServer(String search_key, Integer server_id) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
               
        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE (name ilike \'%"+search_key+"%\' or description ilike \'%"+search_key+"%\' ) AND server = " + server_id.toString() +" ;");
        ArrayList<Item> result = new ArrayList<>();

        while ( rs.next() ) {
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"), rs.getInt("server")));
        }
        rs.close();
        stmt.close();
        c.close();
        return result;
    }

    public static ArrayList<Item> searchAllByOwner(int owner) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:f/PostgresXADS");
        Connection c = data.getConnection();
                
        Statement stmt = null;
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE owner ="+owner + ";" );
        ArrayList<Item> result = new ArrayList<>();
        
        while ( rs.next() ) {
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"), rs.getInt("server")));
        }
        rs.close();
        stmt.close();
        c.close();
        return result;
    }

    public static void delete(int key) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        String sql;
        Statement stmt = null;

        c.setAutoCommit(false);

        stmt = c.createStatement();

        sql = "DELETE FROM public.items WHERE key =" +key  +";";

        stmt.executeUpdate(sql);
        c.commit();
        stmt.close();
        c.close();
    }

    public static void updateItemName(Integer key, String new_name) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();

        String sql;
        Statement stmt = null;
        c.setAutoCommit(false);

        stmt = c.createStatement();

        sql = "UPDATE public.items\n" +
                "\tSET name=\'"+new_name +"\' "+" WHERE key =" + key + ";";

        stmt.executeUpdate(sql);
        c.commit();
        stmt.close();       
        c.close();
    }
    
    public static void updateItemDescr(Integer key, String new_descr) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();

        String sql;
        Statement stmt = null;
        c.setAutoCommit(false);

        stmt = c.createStatement();

        sql = "UPDATE public.items\n" +
                "SET description=\'"+new_descr +"\' "+
                "WHERE key =" + key + ";";

        stmt.executeUpdate(sql);
        c.commit();
        stmt.close();
        c.close();
    }

    public static int newUser(String name, String pass) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();        

        String sql;
        Statement stmt = null;
        c.setAutoCommit(false);
        stmt = c.createStatement();
        sql = "INSERT INTO users (name,password) VALUES ('"+name+ "', '"+pass+"') RETURNING id;";

        ResultSet rs = stmt.executeQuery( sql );
        rs.next();
        int i = (rs.getInt("id"));
        stmt.close();
        c.commit();
        c.close();
        return (i);

    }

    public static ArrayList<String> GetNames() throws ClassNotFoundException, SQLException, NamingException {
        ArrayList<String> names = new ArrayList<>();
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();       

        String sql;
        Statement stmt = null;
        c.setAutoCommit(false);
        stmt = c.createStatement();
        sql = "SELECT * FROM USERS ";
        ResultSet rs = stmt.executeQuery( sql );
        while (rs.next()){
            String temp = rs.getString("name");
            names.add(temp);
        }
        stmt.close();
        c.commit();
        c.close();
        return names;
    }

    public static Integer getUser(String username, String passw) throws NamingException {
    	
    	Integer user_id = -1;
    	
    	
		try {
			InitialContext cxt = new InitialContext();
	        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
	        Connection c = data.getConnection();
	        c.setAutoCommit(false);
	        
	        Statement stmt = c.createStatement();
	        String sql = "SELECT id FROM users WHERE  name = '"+username+"' and password = '"+passw+"' ;";
	        ResultSet rs;
			rs = stmt.executeQuery( sql );
			rs.next();
	        user_id = (rs.getInt("id"));
	        stmt.close();
	        c.commit();
	        c.close();
		} catch (SQLException e) {
			// If it does not find a user_id with this (username, password)
			e.printStackTrace();
		}
        
        return user_id;
    }
    
    /*
    public static String GetPass(String name) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        //Connection c = null;

        String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);
        stmt = c.createStatement();
        sql = "SELECT password FROM public.users WHERE name= '"+name+"'" ;
        ResultSet rs = stmt.executeQuery( sql );
        rs.next();
        String pass = rs.getString("password");
        return pass;
    }

    public static int Getid(String name) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        //Connection c = null;

        String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);
        stmt = c.createStatement();
        sql = "SELECT id FROM public.users WHERE name= '"+name+"'" ;
        ResultSet rs = stmt.executeQuery( sql );
        rs.next();
        int id = rs.getInt("id");
        return id;
    }
    */
    
    // SERVERS
        
    public static int newServer(String ip, Integer port) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
                 
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();
        String sql = "INSERT INTO servers (ip, port) VALUES ('"+ip+"', "+port+") RETURNING id;";

        ResultSet rs = stmt.executeQuery( sql );
        rs.next();
        int i = (rs.getInt("id"));
        stmt.close();
        c.commit();
        c.close();
        return (i);
    }
    
    
    public static Item getItemByKey(Integer key) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE key = "+key+" ;");        
        
        rs.next();
        Item result = new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"), rs.getInt("server"));
            
        rs.close();
        stmt.close();
        c.close();
        return result;
    }
    
    public static Server getServerById(Integer server_id) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM SERVERS WHERE ID = "+server_id+" ;");        
        
        rs.next();
        Server result = new Server(rs.getInt("id"), rs.getString("ip"), rs.getInt("port"));         
            
        rs.close();
        stmt.close();
        c.close();
        return result;
    }
    
 public static User getUserById(Integer user_id) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE ID = "+user_id+" ;");        
        
        rs.next();
        User result = new User(rs.getInt("id"), rs.getString("name"), rs.getString("password"));         
            
        rs.close();
        stmt.close();
        c.close();
        return result;
    }
    
    public static void deleteServer(Integer server_id) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();               

        Statement stmt = c.createStatement();

        String sql = "DELETE FROM SERVERS WHERE ID = "+server_id+" ;";

        stmt.executeUpdate(sql);
        c.commit();
        stmt.close();
        c.close();
    }  
    
    public static void deleteUser(Integer user_id) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();               

        Statement stmt = c.createStatement();

        String sql = "DELETE FROM users WHERE ID = "+user_id+" ;";

        stmt.executeUpdate(sql);
        //c.commit();
        stmt.close();
        c.close();
    }

}

