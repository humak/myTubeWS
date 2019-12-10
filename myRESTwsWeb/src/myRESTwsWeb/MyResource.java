package myRESTwsWeb;



import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.mail.imap.protocol.Item;


@Path("")

public class MyResource {
	
	@Path("/text")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello()
    {
          return "Hello everybody!";
    }
	
	
	@Path("/user/{userName: [a-zA-Z][a-zA-Z_0-9] }/{passWord}")
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@PathParam("userName")String userName, @PathParam("passWord") String passWord) {
		
		
		return  "Hello User!";
	}
	
	@Path("/user/{userName: [a-zA-Z][a-zA-Z_0-9] }/{passWord}")
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public void signIn(@PathParam("userName")String userName, @PathParam("passWord") String passWord) {
		
		
		
	}
	
	@Path("/item")
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public void upload(Item item) {
		
		
		
	}
	
	
	/*
	@Path("/json/{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BeanJSon[] helloJSon(@PathParam("username") String userName)
	{
		
			BeanJSon bean[] = new BeanJSon[2];
			  bean[0] = new BeanJSon();
			  bean[0].setCodi(1);
			  bean[0].setNom(userName);
			  bean[1] = new BeanJSon();
			  bean[1].setCodi(2);
			  bean[1].setNom("default");
			  return bean;
	}
	
	@POST
	@Path("/post")
	public Response crearJSon(BeanJSon bean)
	{
		String resultat = bean.getNom();
		return Response.status(201).entity(resultat).build();
	}
	*/
	
	
	
	
	
	
}