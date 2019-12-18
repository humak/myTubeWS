package myRESTwsData;

public class User {
	
	private Integer userId;
	private String userName;
	private String password;
	//private List<Integer> listOwnedItems;
		
    public User(Integer userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password; 
       // this.listOwnedItems = new ArrayList<Integer> ();
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
