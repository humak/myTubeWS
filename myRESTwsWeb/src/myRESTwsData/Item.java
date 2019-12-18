package myRESTwsData;

import java.io.Serializable;

import common.TubeInterface;

public class Item implements Serializable {

    private Integer key;
    private String name;
    private String description;
    private Integer owner;
    private String path;
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