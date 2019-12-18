package myRESTwsWeb;

import myRESTwsData.Item;
import myRESTwsData.Server;
import myRESTwsDatabase.DataBase;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import common.TubeInterface;



@RequestScoped
@Path("")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class MyResource {

	
	// GET Test
	@GET
	@Path("/test/{name}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getDummyItem(@PathParam("name") String name) {
        
    	List<Item> results = new ArrayList<>();            	
    	results.add(new Item(1, name, "Hello descr", -1, "/somewhere/folder", -1));
    	results.add(new Item(2, "Great Item", "Hello descr two", -1, "/somewhere/fold/data.txt", -2));
    	
    	try {
			DataBase.start();
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			return Response.status(500).entity("Error in DB: " + e.toString()).build();
		}
        return Response.status(200).entity(results).build();        
    }
	
    // POST Item
    @POST
    @Path("/item")  
    @Produces(MediaType.APPLICATION_JSON)
    public Response postItem(Item item) throws SQLException {        
        
        Integer key = -1;
		try {
			key = DataBase.add(item.getName(), item.getDescription(), item.getOwner(), item.getServer(), item.getPath());
		} catch (ClassNotFoundException | NamingException e) {						
			return Response.status(500).entity("Error in DB: " + e.toString()).build();
		}
        
        return Response.status(201).entity(key).build(); //Resource created
    }

    // Global search by keyword
    @GET
    @Path("/global-item/{search_key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocalItem(@PathParam("search_key") String search_key) {
        try {
        	List<Item> results = new ArrayList<>();            
			results = DataBase.search(search_key);                        
            return Response.status(200).entity(results).build();

        } catch (SQLException | ClassNotFoundException | NamingException e) {
        	return Response.status(500).entity("Error in DB: " + e.toString()).build();
        }
    }

    // Local search by keyword
    @GET
    @Path("/local-item/{search_key}/server/{server_id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGlobalItem(@PathParam("search_key") String search_key, @PathParam("server_id") String server_id) {
    	try {
        	List<Item> results = new ArrayList<>();            
			results = DataBase.searchByServer(search_key, Integer.parseInt(server_id)) ;                      
            return Response.status(200).entity(results).build();

        } catch (SQLException | ClassNotFoundException | NamingException e) {
        	return Response.status(500).entity("Error in DB: " + e.toString()).build();
        }
    }         
    
    // UPDATE item name
    @POST
    @Path("/item/{key}/name/{new_name}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response updateItemName(@PathParam("key") String key, @PathParam("new_name") String new_name) {
        try {
            DataBase.updateItemName(Integer.parseInt(key), new_name);
            return Response.status(204).build();

        } catch (SQLException | NumberFormatException | ClassNotFoundException | NamingException ex) {
            return Response.status(500).entity("Database ERROR").build();
        }
    }
    
    // UPDATE item description
    @POST
    @Path("/item/{key}/description/{new_descr}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response updateItemDescription(@PathParam("key") String key, @PathParam("new_descr") String new_descr) {
        try {
            DataBase.updateItemDescr(Integer.parseInt(key), new_descr);
            return Response.status(204).build();

        } catch (SQLException | NumberFormatException | ClassNotFoundException | NamingException ex) {
            return Response.status(500).entity("Database ERROR").build();
        }
    }
    
    // GET item
    @GET
    @Path("/item/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("key") String key) {
        try {
        	Item item;
            item = DataBase.getItemByKey(Integer.parseInt(key));
            return Response.status(200).entity(item).build();

        } catch (SQLException | NumberFormatException | ClassNotFoundException | NamingException ex) {
            return Response.status(500).entity("Database ERROR").build();
        }
    }
    
    // DELETE item
    @DELETE
    @Path("/item/{key}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("key") String key) {
        try {
            DataBase.delete(Integer.parseInt(key));
            return Response.status(204).build();

        } catch (SQLException | NumberFormatException | ClassNotFoundException | NamingException ex) {
            return Response.status(500).entity("Database ERROR").build();
        }
    }
    
    
    // POST a Server
    @POST
    @Path("/server")
    public Response postServer(Server s) throws SQLException {        
        Integer key = -1;
		try {
			key = DataBase.newServer(s.getId(), s.getIp(), s.getPort());
		} catch (ClassNotFoundException | NamingException e) {						
			return Response.status(500).entity("Error in DB: " + e.toString()).build();
		}
        
        return Response.status(201).entity(key).build(); //Resource created
    }

    
    // GET a Server by Id
    @GET
    @Path("/server/{server_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerById(@PathParam("server_id") String server_id) {    	
    	try {
    		Server server;
            server = DataBase.getServerById(Integer.parseInt(server_id));
            return Response.status(200).entity(server).build();

        } catch (SQLException | NumberFormatException | ClassNotFoundException | NamingException ex) {
            return Response.status(500).entity("Database ERROR").build();
        }
    }

    // DELETE a server by server_id
    @DELETE
    @Path("/server/{server_id}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response deleteServer(@PathParam("server_id") String server_id) {
    	try {
            DataBase.deleteServer(Integer.parseInt(server_id));
            return Response.status(204).build();

        } catch (SQLException | NumberFormatException | ClassNotFoundException | NamingException ex) {
            return Response.status(500).entity("Database ERROR").build();
        }
    }        
    
}