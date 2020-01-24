    import java.util.Set;
    import java.util.HashMap;
    import java.util.Iterator;
    import java.util.ArrayList;
    
    /**
     * Class Room - a room in an adventure game.
     *
     * This class is part of the "World of Zuul" application. 
     * "World of Zuul" is a very simple, text based adventure game.  
     *
     * A "Room" represents one location in the scenery of the game.  It is 
     * connected to other rooms via exits.  For each existing exit, the room 
     * stores a reference to the neighboring room.
     * 
     * @author  Michael Kölling and David J. Barnes
     * @version 2016.02.29
     */
    
    public class Room 
    {
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> roomInventory;      // stores the items that are located in this room.
    private boolean locked, amnesia, trapped;                      // stores whether the room is locked or not at this stage
    private String colour;                       // stores which color key is needed.
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        roomInventory = new ArrayList<>();
        locked = false;                         //Every room starts unlocked. If you want one locked, use setLock.
        trapped = false;
        amnesia = false;
        colour = "";                            //Just like the lock, every room has no colour for the lock.
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return Game.r.getString("youAre") + " " + description + ".\n" + getExitString();
    }
    
    /**
     * resets the description with a new string
     * useful when the prefered language has changed
     */
    public void resetDescription(String newDescription){
        description = newDescription;
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
    {
        String returnString = Game.r.getString("exits");
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    public void addItem(Item item){
        roomInventory.add(item);
    }
    
    public void removeItem(Item item){
        roomInventory.remove(item);
    }
    
    public boolean inventoryContains(Item itemToSearch){
        if(roomInventory.contains(itemToSearch)){
            return true;
        } else {
            return false;
        }
    }
    
    public void setLocked(){
        locked = true;
    }
    
    public void setUnlocked(){
        locked = false;
    }
    
    public boolean getLocked(){
        return locked;
    }
    
    public void setAmnesia(){
        amnesia = true;
    }
    
    public void setUnAmnesia(){
        amnesia = false;
    }
    
    public boolean getAmnesia(){
        return amnesia;
    }
    
    public void setTrapped(){
        trapped = true;
    }
    
    public void setUnTrapped(){
        trapped = false;
    }
    
    public boolean getTrapped(){
        return trapped;
    }
    
    public String getColour(){
        return colour;
    }
    
    public void setColour(String newColour){
        colour = newColour;
    }
}

