/**
 *  This class is the main class of the "Cthulhu Quest" application. 
 *  "Cthulhu Quest" is a very simple, text based adventure game.
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser, the display, the tyepwriter and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Sjoerd Jr Helmhout and Pjotr F.W. Kooijmans 
 * @version 2020.01.24
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean parse = false;
    
    public static Display display;
    public static TypeWriter typeWriter;

    
    private ArrayList<Item> playerInventory;      //. Items are added in the take item command located below
    private Room foyer, hallway, reading_room, woods, congierge_office, engine_room, long_hall, long_hall_north, front_yard, dungeon, fainting_room, tabagie, frigidarium, cry_room, servant_hall, psychomantium, aerary;       //. As stated below at createRooms, the rooms were moved for easier access to them.
    Item branch, manual, steroids, purpleKey, redKey, blueKey, potion;                                    //. Items are treated the same way as rooms.
    private int weightCapacity;
    
    JFrame window;
    Container con;
    //JPanel titlePanel, startButtonPanel, mainText, optionPanel, languagePanel, inputPanel;
    //JLabel titleLabel;

    JButton startButton, option1, option2, language1, language2, confirmInputButton;
    JTextArea mainTextArea;
    JTextField userInput;
    public static Locale deutsch = new Locale("ge", "GE");
    public static Locale nederlands = new Locale("nl", "NL");
    public static Locale english = new Locale("en", "EN");
    public static ResourceBundle r = ResourceBundle.getBundle("Bundle", english);
    static String getValue;
    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ArrayList<Room> roomHistory = new ArrayList<Room>();
    Random rand = new Random();
    
    
    
    public static void main(String[] args){
        Game game = new Game();
    }
    
    /**
     * Constructor for objects of class Game.
     * Create the game and initialise its internal map.
     */
    public Game() 
    {        
        createRooms();
        parser = new Parser();
        createDisplay();
        
        createInventory();      //.
        
        weightCapacity = 1;    //. This is how much a player can carry in total.
        
        typeWriter = new TypeWriter(0, 1);
        typeWriter.start();        
        createItems();  
    }
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {              
        // create the rooms
        foyer = new Room(r.getString("foyer"));
        hallway = new Room(r.getString("hallway"));
        reading_room = new Room(r.getString("reading_room"));
        congierge_office = new Room(r.getString("congierge_office"));
        engine_room = new Room(r.getString("engine_room"));
        long_hall = new Room(r.getString("long_hall"));
        long_hall_north = new Room(r.getString("long_hall_north"));
        front_yard = new Room(r.getString("front_yard"));
        dungeon = new Room(r.getString("dungeon"));
        fainting_room = new Room(r.getString("fainting_room"));
        tabagie = new Room(r.getString("tabagie"));
        frigidarium = new Room(r.getString("frigidarium"));
        cry_room = new Room(r.getString("cry_room"));
        servant_hall = new Room(r.getString("servant_hall"));
        psychomantium = new Room(r.getString("psychomantium"));
        aerary= new Room(r.getString("aerary"));
        woods= new Room(r.getString("woods"));
        
        // initialise room exits
        foyer.setExit(r.getString("north"), hallway);
        foyer.setExit(r.getString("south"), front_yard);
        
        front_yard.setExit(r.getString("north"), foyer);
        
        hallway.setExit(r.getString("south"), foyer);
        hallway.setExit(r.getString("west"), congierge_office);
        hallway.setExit(r.getString("east"), reading_room);
        hallway.setExit(r.getString("north"), long_hall);
        
        reading_room.setExit(r.getString("west"), hallway);
        
        congierge_office.setExit(r.getString("east"), hallway);
        congierge_office.setExit(r.getString("west"), engine_room);
        
        engine_room.setExit(r.getString("east"), congierge_office);
        
        long_hall.setExit(r.getString("south"), hallway);
        long_hall.setExit(r.getString("north"), long_hall_north);
        
        long_hall_north.setExit(r.getString("south"), long_hall);
        long_hall_north.setExit(r.getString("north"), dungeon);
        long_hall_north.setExit(r.getString("east"), fainting_room);
        
        dungeon.setExit(r.getString("south"), long_hall_north);
        
        tabagie.setExit(r.getString("east"), long_hall_north);
        
        fainting_room.setExit(r.getString("south"), frigidarium);
        fainting_room.setExit(r.getString("west"), long_hall_north);
        
        frigidarium.setExit(r.getString("south"), woods);
        frigidarium.setExit(r.getString("north"), fainting_room);
        frigidarium.setExit(r.getString("east"), servant_hall);
        frigidarium.setExit(r.getString("west"), cry_room);
        
        cry_room.setExit(r.getString("south"), reading_room);
        cry_room.setExit(r.getString("east"), frigidarium);

        servant_hall.setExit(r.getString("west"), frigidarium);
        servant_hall.setExit(r.getString("east"), psychomantium);
        servant_hall.setExit(r.getString("north"), aerary);
        
        psychomantium.setExit(r.getString("west"), servant_hall);
        
        aerary.setExit(r.getString("south"), servant_hall);
        
        woods.setExit(r.getString("west"), front_yard);
        woods.setExit(r.getString("north"), frigidarium);

        
        //lock the appropriate rooms at the start of the game.
        front_yard.setLocked();
        front_yard.setColour("purple");
        
        engine_room.setLocked();
        engine_room.setColour("red");
        
        dungeon.setLocked();
        dungeon.setColour("blue");
        
        //create amnesia in the appropriate rooms at the start of the game.
        cry_room.setAmnesia();
        woods.setAmnesia();
        
        //trap the appropriate rooms at the start of the game.
        long_hall_north.setTrapped();
        
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
        branch = new Item(r.getString("branch"), r.getString("item_branch_description"), 3);
        foyer.addItem(branch);
        
        manual = new Item(r.getString("manual"), r.getString("item_manual_description"), 1);
        congierge_office.addItem(manual);
        
        potion = new Item(r.getString("potion"), r.getString("item_potion_description"), 1);
        fainting_room.addItem(potion);
        
        steroids = new Item(r.getString("steroids"), r.getString("item_steroids_description"), 1);
        foyer.addItem(steroids);
        fainting_room.addItem(steroids);
        aerary.addItem(steroids);
        
        purpleKey = new Item(r.getString("item_purpleKey_name"), r.getString("item_purpleKey_description"), 2);
        reading_room.addItem(purpleKey);
        
        redKey = new Item(r.getString("item_redKey_name"), r.getString("item_redKey_description"), 2);
        front_yard.addItem(redKey);
        
        blueKey = new Item(r.getString("item_blueKey_name"), r.getString("item_blueKey_description"), 2);
        tabagie.addItem(blueKey);
    }
    
    /**
     * Create all the elements of the display
     */
    private void createDisplay(){
        display = new Display(800, 600, "button", Color.white, Color.black);
        display.setButtonPanel(300, 400, 200, 100, Color.black);
        display.setInputPanel(250,500,300,50,Color.blue);
        display.setLanguagePanel(10, 10, 50, 100, Color.black);
        display.setMainTextPanel(100,100,600,300,Color.black);
        display.setMainTextArea(100,100,600,300,"test",Color.black,Color.white);
        display.setTitlePanel(100,0,600,100,Color.black);
        display.setTitleLabel("C̶͉͉͆̈ẗ̶͓̤̓̐̚͝ẖ̴̀͝u̴͌̆̄ͅl̵̢̡̒h̷̢̛̛͔̀̈́͠ǘ̷̢̨͚͍́̈͊̆ ̷̬̓͗́̇Q̴̗̰̖͈̟͊͝û̷͔̝̗̫͗͝͝è̵̺s̶̰͒̀̔ṭ̴̡̞̝͊");
        display.setImagePanel(200,100,400,250,Color.black);
        display.setTitleTextArea(100,100,350,300,r.getString("cthulhu"),Color.black,Color.white);
        
        display.addButtons(tsHandler, r.getString("start"), r.getString("quit"), r.getString("continue"), "↵", Color.black, Color.white);
        
        showScreen(1);
        display.window.setVisible(true);
        
    }
    
    /**
     * Switch between different screens
     * start screen: 1
     * game screen: 2
     * option screen: 3
     */
    private void showScreen(int i){
        switch(i){
            case 1:
                //show start
                display.inputPanel.setVisible(false);
                display.mainText.setVisible(false);
                display.buttonPanel.setVisible(true);
                display.continueButton.setVisible(false);
                break;
            case 2:
                //display.imagePanel.setVisible(false);
                display.titlePanel.setVisible(false);
                display.languagePanel.setVisible(false);
                display.buttonPanel.setVisible(false);
                display.inputPanel.setVisible(true);
                display.mainText.setVisible(true);            
                break;
            case 3:
                display.inputPanel.setVisible(false);
                display.buttonPanel.setVisible(true);
                display.startButton.setVisible(false);
                display.continueButton.setVisible(false);
                break;
            case 4:
                display.mainText.setVisible(false);
                display.inputPanel.setVisible(false);
                display.imagePanel.setVisible(true);
                display.buttonPanel.setVisible(true);
                display.startButton.setVisible(false);
                display.continueButton.setVisible(true);
                break;
        }
    }
    
    /**
     *  Start play routine.
     *  Instead of a loop event triggers are used.
     */
    public void play() 
    {                     
        currentRoom = foyer;  // start game in foyer.
        roomHistory.add(currentRoom);
        printWelcome();
    }
    
    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {   
        typeWriter.type(r.getString("welcome") + "\n\n" + currentRoom.getShortDescription() + "\n\n" + currentRoom.getExitString());
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
                typeWriter.type(r.getString("unknown"));
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
            
            case BACK:                
                goBack();
                break;
            
            case LOOK:
                look();
                break;
                
            case TAKE: 
                takeItem(command);
                break;
                
            case INVENTORY:             //.
                printInventory();
                break;
                    
            case DROP:
                dropItem(command);
                break;
                
            case USE:                   //..
                useItem(command);
                break;
        }
        return wantToQuit;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        typeWriter.type(r.getString("helpText") + r.getString("commandWords"));
    }

    /**
     * Outputs what is currently in the player's inventory.
     */
    private void printInventory(){      //..
        String output = "";
        if(playerInventory.isEmpty()){
            typeWriter.type(r.getString("inventory_empty"));
        }  else {
            for(Item item : playerInventory){
                //System.out.println(item.getName() + ":  " + item.getDescription());
                output += item.getName() + ": " + item.getDescription() + "\n";
            }
            typeWriter.type(output);
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
    private void takeItem(Command command){       //..
        if(!command.hasSecondWord()){
            typeWriter.type(r.getString("take_what"));
            return;
        }

        CommandWord itemToTake = command.getSecondWord();       
        int totalWeight = calculateWeight();
        
        switch(itemToTake)
        {
            case BRANCH:
            // TODO: switchen op constants that are set in the resource bundles.
                if(totalWeight + branch.getWeight() <= weightCapacity){
                    if(currentRoom.inventoryContains(branch)){
                        playerInventory.add(branch);
                        currentRoom.removeItem(branch);
                        typeWriter.type(r.getString("item_branch_description"));
                    } else {
                        typeWriter.type(r.getString("item_branch_null"));
                    }
                } else {
                    typeWriter.type(r.getString("item_too_heavy"));
                    typeWriter.type(r.getString("remove_x_weight")  + branch.getWeight());
                }
                break;
                
            case STEROIDS:
                if(totalWeight + steroids.getWeight() <= weightCapacity){
                    if(currentRoom.inventoryContains(steroids)){
                        playerInventory.add(steroids);
                        currentRoom.removeItem(steroids);
                        typeWriter.type(r.getString("taken_item_steroids"));
                    } else {
                        typeWriter.type(r.getString("item_steroids_null"));
                    }
                } else {
                    typeWriter.type(r.getString("item_too_heavy"));
                    typeWriter.type(r.getString("remove_x_weight")  + steroids.getWeight());
                }
                break;
                
            case MANUAL:
                if(totalWeight + manual.getWeight() <= weightCapacity){
                    if(currentRoom.inventoryContains(manual)){
                        playerInventory.add(manual);
                        currentRoom.removeItem(manual);
                        typeWriter.type(r.getString("taken_item_manual"));
                    } else {
                        typeWriter.type(r.getString("item_manual_null"));
                    }
                } else {
                    typeWriter.type(r.getString("item_too_heavy"));
                    typeWriter.type(r.getString("remove_x_weight")  + manual.getWeight());
                }
                break;
                
            case POTION:
                if(totalWeight + potion.getWeight() <= weightCapacity){
                    if(currentRoom.inventoryContains(potion)){
                        playerInventory.add(potion);
                        currentRoom.removeItem(potion);
                        typeWriter.type(r.getString("taken_item_potion"));
                    } else {
                        typeWriter.type(r.getString("item_potion_null"));
                    }
                } else {
                    typeWriter.type(r.getString("item_too_heavy"));
                    typeWriter.type(r.getString("remove_x_weight")  + manual.getWeight());
                }
                break;
                
            case KEY:
                if(totalWeight + purpleKey.getWeight() <= weightCapacity){
                    if(currentRoom.inventoryContains(purpleKey)){
                        playerInventory.add(purpleKey);
                        currentRoom.removeItem(purpleKey);
                        typeWriter.type(r.getString("taken_item_key"));
                    } else if(currentRoom.inventoryContains(redKey)) {
                        playerInventory.add(redKey);
                        currentRoom.removeItem(redKey);
                        typeWriter.type(r.getString("taken_item_redKey"));
                    } else if(currentRoom.inventoryContains(blueKey)) {
                        playerInventory.add(blueKey);
                        currentRoom.removeItem(blueKey);
                        typeWriter.type(r.getString("taken_item_key"));
                    } else {
                        typeWriter.type(r.getString("item_key_null"));
                    }
                } else {
                    typeWriter.type(r.getString("item_too_heavy"));
                    typeWriter.type(r.getString("remove_x_weight")  + purpleKey.getWeight());
                }
                break;
                
            default:
                typeWriter.type(r.getString("item_no_take"));
                break;
        }
    }
    
    /**
     * Drops the stated item on the floor
     */
    private void dropItem(Command command){
        CommandWord itemToDrop = command.getSecondWord();
        
        switch(itemToDrop)
        {
            case BRANCH:           
                if(playerInventory.contains(branch)){
                    currentRoom.addItem(branch);
                    playerInventory.remove(branch);
                    typeWriter.type(r.getString("item_branch_drop"));
                } else {
                    typeWriter.type(r.getString("drop_null_item"));
                }
                break;
                
            case STEROIDS:           
                if(playerInventory.contains(steroids)){
                    currentRoom.addItem(steroids);
                    playerInventory.remove(steroids);
                    typeWriter.type(r.getString("item_drop") + r.getString("steroids"));
                } else {
                    typeWriter.type(r.getString("drop_null_item"));
                }
                break;
                
            case MANUAL:           
                if(playerInventory.contains(manual)){
                    currentRoom.addItem(manual);
                    playerInventory.remove(manual);
                    typeWriter.type(r.getString("item_drop") + r.getString("manual"));
                } else {
                    typeWriter.type(r.getString("drop_null_item"));
                }
                break;
                
            case POTION:           
                if(playerInventory.contains(potion)){
                    currentRoom.addItem(potion);
                    playerInventory.remove(potion);
                    typeWriter.type(r.getString("item_drop") + r.getString("potion"));
                } else {
                    typeWriter.type(r.getString("drop_null_item"));
                }
                break;
            //TODO cases toevoegen voor elk item
            default:
                typeWriter.type(r.getString("drop_what")); 
                break;
        }
    }
    
    /**
     * Uses an item and destroys it in the process.
     */
    private void useItem(Command command){  //..
        if(!command.hasSecondWord()){
            typeWriter.type(r.getString("use_what?"));
            return;
        }
        
        CommandWord itemToUse = command.getSecondWord();
        
        switch(itemToUse)
        {
            case STEROIDS:
                if(playerInventory.contains(steroids)){
                    weightCapacity = 30;
                    playerInventory.remove(steroids);
                    typeWriter.type(r.getString("used_item_steroids"));
                }
                break;
            case MANUAL:
                if(playerInventory.contains(manual) && currentRoom == engine_room){
                    weightCapacity = 30;
                    playerInventory.remove(manual);
                    long_hall_north.setUnTrapped();
                    typeWriter.type(r.getString("used_item_manual"));
                }else{
                    typeWriter.type(r.getString("use_manual_later"));
                }
                break;
                
            case POTION:
                if(playerInventory.contains(potion)){
                    weightCapacity = 1;
                    playerInventory.remove(potion);
                    long_hall_north.setExit(r.getString("west"), tabagie);
                    typeWriter.type(r.getString("used_item_potion"));
                }
                break;
            
            default:
                typeWriter.type(r.getString("unknown"));
        }
    }
    
    /** 
     * Try to go in one direction, if there is an exit, check for lock and key. Then enter the new
     * room. Also checks if you are in the final room.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            typeWriter.type(r.getString("goWhere"));
            return;
        }

        String direction = command.getSecondString();
        
         // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        //..
        if (nextRoom == null) {
            display.mainTextArea.append("\n" + r.getString("noDoor"));
           
        } else {
            if(nextRoom.getLocked()){
                String colour = nextRoom.getColour();
                if(colour == "purple" && playerInventory.contains(purpleKey)){
                    nextRoom.setUnlocked();
                    playerInventory.remove(purpleKey);
                    typeWriter.type(r.getString("door_unlocked"));
                } else if(colour == "red" && playerInventory.contains(redKey)){
                    nextRoom.setUnlocked();
                    playerInventory.remove(redKey);
                    typeWriter.type(r.getString("door_unlocked"));
                } else if(colour == "blue" && playerInventory.contains(blueKey)){
                    nextRoom.setUnlocked();
                    playerInventory.remove(blueKey);
                    typeWriter.type(r.getString("door_unlocked"));
                } else {
                    typeWriter.type(r.getString("no_key"));
                }
            } else if(nextRoom.getTrapped()){
               trapTeleport();
            } 
            else {
                if(currentRoom.getAmnesia()){
                    roomHistory.clear();
                }
                currentRoom = nextRoom;
                
                    roomHistory.add(currentRoom);
                    typeWriter.type(currentRoom.getShortDescription() + "\n\n" + currentRoom.getExitString());
                if(currentRoom == dungeon){
                    showScreen(3);
                    typeWriter.type(r.getString("end"));
                }
                         
            }   
        }
    }
    
    /**
     * Change the current room to the previous room.
     */
    private void goBack(){
        if(roomHistory.size() >= 2){
            currentRoom = roomHistory.get(roomHistory.size() - 2);
            typeWriter.type(currentRoom.getShortDescription() + "\n\n" + currentRoom.getExitString());   
            roomHistory.remove(roomHistory.size() - 1);
        }
        else {
            typeWriter.type(r.getString("cannotBack"));
        }
    }
    
    private void trapTeleport(){
        System.out.println("it's a trap!");
        int n = rand.nextInt(roomHistory.size());
        //display.mainTextArea.setText(r.getString("cthulhu"));        
        display.mainTextArea.setText(r.getString("cthulhu"));
        currentRoom = roomHistory.get(n);
        typeWriter.type(r.getString("trapped")); 
    }
    
    
    /**
     * Sets the textArea to a description of the current room.
     */
    private void look(){
        typeWriter.type(currentRoom.getShortDescription() + "\n\n" + currentRoom.getExitString());   
    }
    
    /** 
     * "Quit" was entered. Show a screen with the quit and continue button.
     * If continue is pressed you return to the game
     * If quit is pressed the program is ended.
     */
    private boolean quit(Command command) 
    {
            typeWriter.type(r.getString("quit"));
            showScreen(4);
            return true;  // signal that we want to quit
    }
         
    /**
     * 
     */
    public class TitleScreenHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(display.startButton.equals(event.getSource())){
              showScreen(2);
              play();
            }
            else if (display.language1.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", english);
                createRooms();
                createItems();
                CommandWords.resetEnum(english);
            }
            else if (display.language2.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", deutsch);
                createRooms();
                createItems();
                CommandWords.resetEnum(deutsch);
            }
            else if (display.language3.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", nederlands);
                createRooms();
                createItems();
                CommandWords.resetEnum(nederlands);
            }
            else if (display.confirmInputButton.equals(event.getSource())){
                getValue = display.userInput.getText();
                display.userInput.setText("");
                
                boolean finished = false;                 
                Command command = parser.getCommand(getValue);
                finished = processCommand(command);                
            }
            else if (display.continueButton.equals(event.getSource())){
                showScreen(2);
                look();
            }
            else if (display.quitButton.equals(event.getSource())){
                System.exit(0);
            }                       
        }
    }
        
      
}


    
