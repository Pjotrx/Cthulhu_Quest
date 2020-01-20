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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private boolean parse = false;
    
    public static Display display;
    public static TypeWriter typeWriter;
    
    JFrame window;
    Container con;
    JPanel titlePanel, startButtonPanel, mainText, optionPanel, languagePanel, inputPanel;
    JLabel titleLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 50);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font smallFont = new Font("Times New Roman", Font.PLAIN, 12);
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
    
    
    
    public static void main(String[] args){
        Game game = new Game();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        
        createRooms();
        parser = new Parser();
        createDisplay();
        typeWriter = new TypeWriter(0, 1);
        typeWriter.start();
    }
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
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
     * Create all the elements of the display
     */
    private void createDisplay(){
        display = new Display(800, 600, "button", Color.white, Color.black);
        display.setButtonPanel(300, 400, 200, 100, Color.black);
        display.setInputPanel(250,500,300,50,Color.blue);
        display.setLanguagePanel(10, 10, 50, 100, Color.black);
        display.setMainTextPanel(100,100,600,250,Color.black);
        display.setMainTextArea(100,100,600,250,"test",Color.black,Color.white);
        display.addButtons(tsHandler, "start", "↵", "En", "De", "back", Color.black, Color.white);
        
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
                break;
            case 2:
                
                display.buttonPanel.setVisible(false);
                display.inputPanel.setVisible(true);
                display.mainText.setVisible(true);
                printWelcome();
                break;
            case 3:
                //show options
                break;
        }
    }
    
    /**
     *  Start play routine.
     *  Instead of a loop event triggers are used.
     */
    public void play() 
    {           
        
        printWelcome();
    }
    
    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {   
        //display.mainTextArea.setText(r.getString("welcome") + "\n" + currentRoom.getLongDescription());
        typeWriter.type(r.getString("welcome") + "\n" + currentRoom.getLongDescription());
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
        typeWriter.type(r.getString("helpText") + parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            typeWriter.type(r.getString("goWhere"));
            return;
        }

        String direction = command.getSecondWord();
         // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            display.mainTextArea.append("\n" + r.getString("noDoor"));
        }
        else {
            currentRoom = nextRoom;
            roomHistory.add(currentRoom);
            typeWriter.type(currentRoom.getLongDescription());         
        }
    }
    
    /**
     * Change the current room to the previous room.
     */
    private void goBack(){
        if(roomHistory.size() >= 2){
            currentRoom = roomHistory.get(roomHistory.size() - 2);
            typeWriter.type(currentRoom.getLongDescription());
            roomHistory.remove(roomHistory.size() - 1);
        }
        else {
            typeWriter.type(r.getString("cannotBack"));
        }
    }
    
    
    /**
     * Sets the textArea to a description of the current room.
     */
    private void look(){
        typeWriter.type(currentRoom.getLongDescription());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            typeWriter.type(r.getString("quitWhat"));
            return false;
        }
        else {
            typeWriter.type("Just press the X");
            return true;  // signal that we want to quit
        }
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
            }
            else if (display.language1.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", english);
                createRooms();
                CommandWords.resetEnum(english);
            }
            else if (display.language2.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", deutsch);
                createRooms();
                CommandWords.resetEnum(deutsch);
            }
            else if (display.confirmInputButton.equals(event.getSource())){
                getValue = display.userInput.getText();
                //System.out.println(getValue);   //just for debugging
                display.userInput.setText("");
                
                boolean finished = false;                 
                Command command = parser.getCommand(getValue);
                finished = processCommand(command);                
            }
            else if (display.backButton.equals(event.getSource())){
                showScreen(1);
            }
            
            
        }
    }
        
      
}


    
