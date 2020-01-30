package myRESTwsData;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String username;
	private String password;
	//private List<Integer> listOwnedItems;
		
    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password; 
       // this.listOwnedItems = new ArrayList<Integer> ();
    }
    
    public User() {
    	
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
