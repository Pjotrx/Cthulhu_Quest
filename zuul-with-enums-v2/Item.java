
/**
 * The item class only contains the description of a given item for now.
 *
 * @author Sjoerd Jr Helmhout
 * @version 12-01-2020
 */
public class Item
{
    private String description;
    private String name;

    /**
     * Constructor for objects of class Item
     */
    public Item(String newName, String newDescription)
    {
        name = newName;
        description = newDescription;
    }

    /**
     * Returns the description of said item
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Returns the name of said item.
     */
    public String getName(){
        return name;
    }
}
