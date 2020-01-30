package myRESTwsData;

import java.io.Serializable;

public class Item implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer key;
    private String name;
    private String description;
    private Integer owner; // user_id
    private String path;
    // We store the port at the server instance, and with the port the RMIclient can get the TubeInterface of the targetServer
    //private TubeInterface targetServerRef;     
    private Integer server; //server_id
    
    //public Item(Integer key, String name, String description, Integer owner, String path,  Integer server_id) {
    public Item(Integer key, String name, String description, Integer owner, String path, Integer server) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.path = path;
        //this.targetServerRef = targetServerRef;
        this.server = server;
    }   

    public Item() { // Seems necessary
    	
    }
    
    // And some getters

	public long getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOwner() {
        return owner;
    }

    public String getPath() {
        return path;
    }

    /*
    public TubeInterface getTargetServerRef() {
        return targetServerRef;
    }
	*/

    public Integer getServer() {
        return server;
    }
    
    /*
    public void setTargetServerRef(TubeInterface targetServerRef) {
        this.targetServerRef = targetServerRef;
    }
    */
    
    




}