package myRESTwsBean;

import java.util.ArrayList;
import java.util.List;

public class User {

	private Integer userId;
	private String userName;
	private String passWord;
	//private List<Integer> listOwnedItems;
	
	
    public User(Integer userId, String userName, String passWord) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord; 
       // this.listOwnedItems = new ArrayList<Integer> ();
    }

    public Integer getuserId() {
        return userId;
    }

    public String getuserName() {
        return userName;
    }

    public String getpassWord() {
        return passWord;
    }
    
    
}
