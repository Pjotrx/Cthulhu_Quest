
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
    private int weight;

    /**
     * Constructor for objects of class Item
     * @param 
     */
    public Item(String newName, String newDescription, int newWeight)
    {
        this.name = newName;
        this.description = newDescription;
        this.weight = newWeight;
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
    
    /**
     * Returns the weight of said item.
     */
    public int getWeight(){
        return weight;
    }
    
    
}