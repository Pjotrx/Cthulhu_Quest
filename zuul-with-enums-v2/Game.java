import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Item> playerInventory;      //. Items are added in the take item command located below
    private Room outside, theater, pub, lab, office;        //. As stated below at createRooms, the rooms were moved for easier access to them.
    private Item branch;                                    //. Items are treated the same way as rooms.
    private int weightCapacity;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();     
        createInventory();      //.
        createItems();          //.
        weightCapacity = 20;    //. This is how much a player can carry in total.
    }
    
    public static void main(String[] args){
        Game game = new Game();
        game.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms(){         //. Rooms were moved to the general scope for easier access to them while making items
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }
   
    /**
     *  Creates a fresh inventory for the player.
     */
    private void createInventory(){         //.
        playerInventory = new ArrayList<>();
    }
    
    /**
     * Creates all items and puts them in the appropriate rooms.
     */
    private void createItems(){             //.
        branch = new Item("Branch", "An ordinary branch. Why did you pick this up again?", 3);
        outside.addItem(branch);
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case TAKE:                  //.
                takeItem(command);
                break;
                
            case INVENTORY:             //.
                printInventory();
                break;
                    
            case DROP:                  //.
                dropItem(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Outputs what is currently in the player's inventory.
     */
    private void printInventory(){
        for(Item item : playerInventory){
            System.out.println(item.getName() + ":  " + item.getDescription());
        }
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Calculates the total weight of the items the player is carrying and returns this.
     */
    private int calculateWeight(){
        int totalWeight = 0;
        
        for(Item item: playerInventory){
            totalWeight = totalWeight + item.getWeight();
        }
        
        return totalWeight;
    }
    
    /**
     *  First off checks if a second word is entered. After that it calculates the total weight the player is already carrying.
     *  Then it switches between all the items that can be picked up, if the item cannot be picked up, it displays a message via the default.
     */
    private void takeItem(Command command){       //.
        if(!command.hasSecondWord()){
            System.out.println("Take what, u doofus?");
            return;
        }
        
        String itemToTake = command.getSecondWord();
        int totalWeight = calculateWeight();
        
        switch(itemToTake)
        {
            case "branch":
            // TODO: switchen op ints, and thus make a converter that converts the second word to an integer?
                if(totalWeight + branch.getWeight() < weightCapacity){
                    if(currentRoom.inventoryContains(branch)){
                        playerInventory.add(branch);
                        currentRoom.removeItem(branch);
                        System.out.println("You picked up a branch, I guess. What are you gonna do with it, play fetch with yourself? Haha");
                    } else {
                        System.out.println("I don't see any branches in here, do you dumbass?");
                    }
                } else {
                    System.out.println("It appears to me that you are unable to carry this item. Next time before you wander off, please take a gym class or two, will ya?");
                    System.out.println("You need to free up at least: " + branch.getWeight());
                }
                break;
                
            default:
                System.out.println("This item cannot be picked up and carried around. Better luck next time, eh?");
                break;
        }
    }
    
    /**
     * Drops the stated item on the floor
     */
    private void dropItem(Command command){
        if(!command.hasSecondWord()){
            System.out.println("I am starting to believe your mother dropped you as a child. But, that aside, what do you need me to drop?");
            return;
        }
        
        String itemToDrop = command.getSecondWord();
        
        switch(itemToDrop)
        {
            case "branch":
            //TODO: ook dit kan via ints net als bij take.
                if(playerInventory.contains(branch)){
                    currentRoom.addItem(branch);
                    playerInventory.remove(branch);
                    System.out.println("Thank god you finally stopped carrying that dumb branch around. What did you expect to use that thing for anyway?");
                } else {
                    System.out.println("You, my good Sir, are a prime example of mental incompetence. How can you drop something you don't possess?");
                }
                break;
                
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
