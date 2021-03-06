package common;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import myRESTwsData.Item;

public interface TubeInterface extends Remote{

    boolean register(ClientInterface new_client) throws RemoteException;
    //ArrayList<Item> searchLocal(String fileName) throws SQLException, ClassNotFoundException, RemoteException;
    void uploadFile(byte[] filebyte, String name, String description, String file_name, int owner) throws IOException, SQLException, ClassNotFoundException;
    List<Item> searchFile(String fileName) throws RemoteException, SQLException, ClassNotFoundException;
    byte[] downloadFile(String fileName, ClientInterface client) throws SQLException, ClassNotFoundException, IOException;
    public Item globalDownloadFileItem(String fileName, ClientInterface client) throws SQLException, ClassNotFoundException, IOException;
    byte[] downloadFileByItem(Item item) throws IOException;
    public Item searchByOwner(int owner, ClientInterface client) throws ClassNotFoundException, SQLException, RemoteException;
    void deletebyItem(Item item) throws SQLException, ClassNotFoundException, RemoteException;
    void updatebyItem(Item item,String newname,String newdesc) throws SQLException, ClassNotFoundException, RemoteException;
    public ArrayList<String> getnames() throws RemoteException, SQLException, ClassNotFoundException;
    public int NewUser(String new_name,String new_pass) throws RemoteException, SQLException, ClassNotFoundException;
    String getPass(String name) throws RemoteException, SQLException, ClassNotFoundException;
    int getId(String name) throws RemoteException, SQLException, ClassNotFoundException;

    String hello() throws RemoteException;
    
}
