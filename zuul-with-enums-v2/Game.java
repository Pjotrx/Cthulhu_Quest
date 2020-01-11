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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;

public class Game 
{
    private static Parser parser = new Parser();
    private static Room currentRoom;
    private boolean parse = false;
    
    
    
    public static Display display = new Display();
   
    
    public static Locale deutsch = new Locale("ge", "GE");
    public static Locale nederlands = new Locale("nl", "NL");
    public static Locale english = new Locale("en", "EN");
    public static ResourceBundle r = ResourceBundle.getBundle("Bundle", english);
    public static String getValue;
    public static TitleScreenHandler tsHandler = new TitleScreenHandler();
    private static ArrayList<Room> roomHistory = new ArrayList<Room>();
    
    /**
     * Create the game and initialise its internal map.
     */
    public static void main(String[] args)
    { 
        display.createStartScreen();
        createRooms();
        //Parser parser = new Parser();
    }
    
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private static void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room(r.getString("outside"));
        theater = new Room(r.getString("theater"));
        pub = new Room(r.getString("pub"));
        lab = new Room(r.getString("lab"));
        office = new Room(r.getString("office"));
        
        // initialise room exits
        outside.setExit(r.getString("east"), theater);
        outside.setExit(r.getString("south"), lab);
        outside.setExit(r.getString("west"), pub);

        theater.setExit(r.getString("west"), outside);

        pub.setExit(r.getString("east"), outside);

        lab.setExit(r.getString("north"), outside);
        lab.setExit(r.getString("east"), office);

        office.setExit(r.getString("west"), lab);

        currentRoom = outside;  // start game outside
        roomHistory.add(currentRoom);
    }

    /**
     *  Start play routine.
     *  Instead of a loop event triggers are used.
     */
    public void play() 
    {           
        display.createGameScreen();
        printWelcome();
    }
    
    /**
     * Print out the opening message for the player.
     */
    public static void printWelcome()
    {   
        Display.mainTextArea.setText(r.getString("welcome") + "\n" + currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public static boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                Display.mainTextArea.setText(r.getString("unknown"));
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
        }
        return wantToQuit;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private static void printHelp() 
    {
        System.out.println("hulp gevraagd");
        display.mainTextArea.setText(r.getString("helpText") + parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private static void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            display.mainTextArea.setText(r.getString("goWhere"));
            return;
        }

        String direction = command.getSecondWord();
         // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            Display.mainTextArea.append("\n" + r.getString("noDoor"));
        }
        else {
            currentRoom = nextRoom;
            roomHistory.add(currentRoom);
            Display.mainTextArea.setText(currentRoom.getLongDescription());         
        }
    }
    
    /**
     * Change the current room to the previous room.
     */
    private static void goBack(){
        if(roomHistory.size() >= 2){
            currentRoom = roomHistory.get(roomHistory.size() - 2);
            Display.mainTextArea.setText(currentRoom.getLongDescription());
            roomHistory.remove(roomHistory.size() - 1);
        }
        else {
            Display.mainTextArea.setText(r.getString("cannotBack"));
        }
    }
    
    
    /**
     * Sets the textArea to a description of the current room.
     */
    private static void look(){
        Display.mainTextArea.setText(currentRoom.getLongDescription());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private static boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            Display.mainTextArea.setText(r.getString("quitWhat"));
            return false;
        }
        else {
            Display.mainTextArea.setText("Just press the X");
            return true;  // signal that we want to quit
        }
    }
    
  
    /**
     * 
     */
    public static class TitleScreenHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(display.startButton.equals(event.getSource())){
              //play();
              
              display.createGameScreen();
              printWelcome(); 
              System.out.println("succes");
            }
            else if (Display.language1.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", english);
                createRooms();
                CommandWords.resetEnum(english);
            }
            else if (Display.language2.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", deutsch);
                createRooms();
                CommandWords.resetEnum(deutsch);
            }
            else if (display.confirmInputButton.equals(event.getSource())){
                getValue = display.userInput.getText();
                System.out.println(getValue);   //just for debugging
                display.userInput.setText("");
                
                boolean finished = false;                 
                Command command = parser.getCommand(getValue);
                finished = processCommand(command);                
            }
            
            
        }
    }
        
      
}


    
