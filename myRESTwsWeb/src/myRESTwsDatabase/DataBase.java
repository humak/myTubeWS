package myRESTwsDatabase;


import common.TubeInterface;
import myRESTwsData.Item;
import myRESTwsData.Server;

import java.rmi.Remote;
import java.sql.*;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBase implements Remote {
    
    //private static String dbname;
	
    private static int local_id = 0;            

    public static void start() throws SQLException, ClassNotFoundException, NamingException {
        //database start
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        //Connection c = null;
        String sql;
        //create(dbname);
        //System.out.println("check pgAdmin4 for the database "+dbname +local_id + "press enter when ready");
        //Scanner myObj = new Scanner(System.in);
        //String check = myObj.nextLine();
        try {
            Statement stmt = null;
            //try to connect to database
            //???creating database???
            //Class.forName("org.postgresql.Driver");
            //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname.toLowerCase() +local_id, "postgres", "4558");
            //stmt = null;
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS USERS(" +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "PASSWORD VARCHAR NOT NULL," +
                    "ID SERIAL PRIMARY KEY" +
                    ");\n";
            sql =sql+ "CREATE TABLE IF NOT EXISTS ITEMS(" +
                    " NAME VARCHAR NOT NULL," +
                    " DESCRIPTION VARCHAR NOT NULL," +
                    " OWNER INTEGER NOT NULL," +
                    " KEY SERIAL NOT NULL," +
                    " SERVER INTEGER NOT NULL,"+
                    " PATH VARCHAR NOT NULL," +
                    " FOREIGN KEY (OWNER) REFERENCES USERS(ID) ON DELETE CASCADE );";
            
            sql =sql+ "CREATE TABLE IF NOT EXISTS SERVERS(" +
                    " ID SERIAL PRIMARY KEY," +
                    " IP VARCHAR NOT NULL," +
                    " PORT INTEGER NOT NULL );";
            //System.out.println(sql);

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("Tables in place");

        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
        }
    }
	
    /*
    private static void create(String dbname) throws SQLException, ClassNotFoundException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        //Connection c = null;

        String sql;
        try{
            //Class.forName("org.postgresql.Driver");
            //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "4558");
            System.out.println("Opened database successfully");
            Statement stmt = null;
            stmt = c.createStatement();


            sql = "CREATE DATABASE " +dbname+local_id;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }catch (Exception e) {
            //e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //System.exit(0);
            ////Class.forName("org.postgresql.Driver");
            ////c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LOCAL"+local_id, "postgres", "4558");
        }
    }
    */

    public static int add(String name, String description, Integer owner,Integer server_id, String path) throws SQLException, ClassNotFoundException, NamingException {
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
        sql = "INSERT INTO public.items (name, description, owner,server, path) \n VALUES (\'";
        sql = sql + name +"\',\'" + description +"\',"+ owner + ","+ server_id + ",\'"+ path +"\') RETURNING key;";
        //stmt.executeUpdate(sql);

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
        //Connection c = null;

        String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
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
        //Connection c = null;

        
        //String sql;
        Statement stmt = null;
        ////Class.forName("org.postgresql.Driver");
        ////c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS;" );
        ArrayList<Item> result = new ArrayList<>();
        Integer i =0;
        while ( rs.next() ) {
            //String  name = rs.getString("name");
            //String desc = rs.getString("description");
            //xSystem.out.println(name);	           
            result.add(new Item(i, rs.getString("name"), rs.getString("description"),  (Integer)rs.getInt("owner"),  rs.getString("path"), (TubeInterface)null, (Integer)rs.getInt("server")));
            i++;
        }
        rs.close();
        stmt.close();
        c.close();
        return result;

    }

    public static ArrayList<Item> search(String search_key) throws ClassNotFoundException, SQLException, NamingException {
        //System.out.println("here8");
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        //Connection c = null;
        //String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE name ilike \'%"+search_key+"%\' or description ilike \'%"+search_key+"%\' ;" );
        ArrayList<Item> result = new ArrayList<>();
        int i =0;
        while ( rs.next() ) {
            //String  name = rs.getString("name");
            //String desc = rs.getString("description");
            //int key =rs.getInt("key");
            //System.out.println(name);
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"),null,rs.getInt("server")));
            i++;
        }
        rs.close();
        stmt.close();
        c.close();
        //System.out.println("here9");
        return result;
    }
    
    public static ArrayList<Item> searchByServer(String search_key, Integer server_id) throws ClassNotFoundException, SQLException, NamingException {
        //System.out.println("here8");
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        //Connection c = null;
        //String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE name ilike \'%"+search_key+"%\' or description ilike \'%"+search_key+"%\' AND server_id = " + server_id.toString() +" ;");
        ArrayList<Item> result = new ArrayList<>();
        int i =0;
        while ( rs.next() ) {
            //String  name = rs.getString("name");
            //String desc = rs.getString("description");
            //int key =rs.getInt("key");
            //System.out.println(name);
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"),null,rs.getInt("server")));
            i++;
        }
        rs.close();
        stmt.close();
        c.close();
        //System.out.println("here9");
        return result;
    }

    public static ArrayList<Item> searchByOwner(int owner) throws ClassNotFoundException, SQLException, NamingException {
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:f/PostgresXADS");
        Connection c = data.getConnection();
        
        //Connection c = null;
        //String sql;
        Statement stmt = null;
        ////Class.forName("org.postgresql.Driver");
        ////c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE owner ="+owner + ";" );
        ArrayList<Item> result = new ArrayList<>();
        int i =0;
        while ( rs.next() ) {
            //String  name = rs.getString("name");
            //String desc = rs.getString("description");
            //int key =rs.getInt("key");
            //System.out.println(name);
            result.add(new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"),null,rs.getInt("server")));
            i++;
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
        
        //Connection c = null;
        String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
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

    public static int newUser(String name,String pass) throws ClassNotFoundException, SQLException, NamingException {
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
        sql = "INSERT INTO users (name,password) VALUES ('"+name+ "', '"+pass+"') RETURNING id;";

        //stmt.executeUpdate(sql);
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
        //Connection c = null;

        String sql;
        Statement stmt = null;
        //Class.forName("org.postgresql.Driver");
        //c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/local"+local_id, "postgres", "4558");
        c.setAutoCommit(false);
        stmt = c.createStatement();
        sql = "SELECT * FROM USERS ";
        ResultSet rs = stmt.executeQuery( sql );
        while (rs.next()){
            String temp = rs.getString("name");
            names.add(temp);
            //System.out.println(temp);
        }
        stmt.close();
        c.commit();
        c.close();
        return names;
    }

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
    
    // SERVERS
    
    public static int newServer(Integer id, String ip, Integer port) throws ClassNotFoundException, SQLException, NamingException {
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
                 
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();
        String sql = "INSERT INTO users (id, ip, port) VALUES ("+id+", '"+ip+"', "+port+") RETURNING id;";

        ResultSet rs = stmt.executeQuery( sql );
        rs.next();
        int i = (rs.getInt("id"));
        stmt.close();
        c.commit();
        c.close();
        return (i);
    }
    
    
    public static Item getItemByKey(Integer key) throws ClassNotFoundException, SQLException, NamingException {
        //System.out.println("here8");
    	
    	InitialContext cxt = new InitialContext();
        DataSource data = (DataSource) cxt.lookup("java:/PostgresXADS");
        Connection c = data.getConnection();
        
        c.setAutoCommit(false);

        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM ITEMS WHERE key = "+key+" ;");        
        
        rs.next();
        Item result = new Item(rs.getInt("key"), rs.getString("name"), rs.getString("description"),  rs.getInt("owner"),  rs.getString("path"),null,rs.getInt("server"));
            
        rs.close();
        stmt.close();
        c.close();
        return result;
    }
    
    public static Server getServerById(Integer server_id) throws ClassNotFoundException, SQLException, NamingException {
        //System.out.println("here8");
    	
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

}

