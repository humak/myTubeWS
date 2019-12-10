package myRESTwsBean;

public class Item {

    private Integer key;
    private String name;
    private String description;
    private Integer owner;
    private String path;
    //private TubeInterface targetServerRef;

    public Item(Integer key, String name, String description, Integer owner, String path) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.path = path;
       // this.targetServerRef = targetServerRef;
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


}
