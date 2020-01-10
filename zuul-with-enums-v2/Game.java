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
    String getValue;
    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ArrayList<Room> roomHistory = new ArrayList<Room>();
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createStartScreen();
        createRooms();
        parser = new Parser();
    }
    
    /**
     * Create a window with the start button etc
     */
    public void createStartScreen(){
        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //important to close the window properly
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null); //set default layout to null
        
        con = window.getContentPane();
        
        titlePanel = new JPanel();
        titlePanel.setBounds(100, 100, 600, 150);       // (xpos, ypos, width, height)
        titlePanel.setBackground(Color.black);
        
        titleLabel = new JLabel("C̶͉͉͆̈ẗ̶͓̤̓̐̚͝ẖ̴̀͝u̴͌̆̄ͅl̵̢̡̒h̷̢̛̛͔̀̈́͠ǘ̷̢̨͚͍́̈͊̆ ̷̬̓͗́̇Q̴̗̰̖͈̟͊͝û̷͔̝̗̫͗͝͝è̵̺s̶̰͒̀̔ṭ̴̡̞̝͊");
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(titleFont);
        
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setBackground(Color.black);
        
        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        
        
        //needs to be added to optionScreen
        //==============================
        languagePanel = new JPanel();
        languagePanel.setBounds(20, 20, 200, 50);
        languagePanel.setBackground(Color.black);
        languagePanel.setLayout(new GridLayout(1, 4));
        con.add(languagePanel);
        
        language1 = new JButton("En");
        language1.setBackground(Color.black);
        language1.setForeground(Color.white);
        language1.setFont(normalFont);
        language1.addActionListener(tsHandler);
        languagePanel.add(language1);
        
        language2 = new JButton("De");
        language2.setBackground(Color.black);
        language2.setForeground(Color.white);
        language2.setFont(normalFont);
        language2.addActionListener(tsHandler);
        languagePanel.add(language2);
        //==============================
        
        titlePanel.add(titleLabel);
        startButtonPanel.add(startButton);
        con.add(titlePanel);
        con.add(startButtonPanel);
        window.setVisible(true);
    }
    /**
     * Creates a screen to display settings.
     * Not finished or even functional
     */
    public void creatOptionScreen(){
        optionPanel = new JPanel();
        optionPanel.setBounds(250, 350, 300, 50);
        optionPanel.setBackground(Color.black);
        optionPanel.setLayout(new GridLayout(4, 1));
        
        option1 = new JButton("Option 1");
        option1.setBackground(Color.black);
        option1.setForeground(Color.white);
        option1.setFont(normalFont);
        optionPanel.add(option1);
        
        option2 = new JButton("Option 2");
        option2.setBackground(Color.black);
        option2.setForeground(Color.white);
        option2.setFont(normalFont);
        optionPanel.add(option2);
        
        con.add(optionPanel);
    }
    
    /**
     * Creates a main JTextArea to display descriptions and instructions.
     * As well as an JTextField and submit button for user input.
     */
    public void createGameScreen() 
    {
        titlePanel.setVisible(false);
        languagePanel.setVisible(false);
        startButtonPanel.setVisible(false);
        
        mainText = new JPanel();
        mainText.setBounds(100, 100, 600, 250);
        mainText.setBackground(Color.black);
        con.add(mainText);
        
        mainTextArea = new JTextArea("this is the main text Area");
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);
        mainTextArea.setEditable(false);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        
        mainText.add(mainTextArea);
        
        inputPanel = new JPanel();
        inputPanel.setBounds(250, 350, 300, 50);
        inputPanel.setBackground(Color.black);
        inputPanel.setLayout(new GridLayout(2, 1));
        con.add(inputPanel);
       
        userInput = new JTextField("");
        confirmInputButton = new JButton("confirm");
        confirmInputButton.setBounds(550, 350, 50, 50);
        confirmInputButton.addActionListener(tsHandler);
        inputPanel.add(userInput);
        con.add(confirmInputButton);
        
        window.getRootPane().setDefaultButton(confirmInputButton);
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
     *  Start play routine.
     *  Instead of a loop event triggers are used.
     */
    public void play() 
    {           
        createGameScreen();
        printWelcome();
    }
    
    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {   
        mainTextArea.setText(r.getString("welcome") + "\n" + currentRoom.getLongDescription());
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
                mainTextArea.setText(r.getString("unknown"));
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
        mainTextArea.setText(r.getString("helpText") + parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            mainTextArea.setText(r.getString("goWhere"));
            return;
        }

        String direction = command.getSecondWord();
         // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            mainTextArea.append("\n" + r.getString("noDoor"));
        }
        else {
            currentRoom = nextRoom;
            roomHistory.add(currentRoom);
            mainTextArea.setText(currentRoom.getLongDescription());         
        }
    }
    
    /**
     * Change the current room to the previous room.
     */
    private void goBack(){
        if(roomHistory.size() >= 2){
            currentRoom = roomHistory.get(roomHistory.size() - 2);
            mainTextArea.setText(currentRoom.getLongDescription());
            roomHistory.remove(roomHistory.size() - 1);
        }
        else {
            mainTextArea.setText(r.getString("cannotBack"));
        }
    }
    
    
    /**
     * Sets the textArea to a description of the current room.
     */
    private void look(){
        mainTextArea.setText(currentRoom.getLongDescription());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            mainTextArea.setText(r.getString("quitWhat"));
            return false;
        }
        else {
            mainTextArea.setText("Just press the X");
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
            if(startButton.equals(event.getSource())){
              play();
            }
            else if (language1.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", english);
                createRooms();
                CommandWords.resetEnum(english);
            }
            else if (language2.equals(event.getSource())){
                r = ResourceBundle.getBundle("Bundle", deutsch);
                createRooms();
                CommandWords.resetEnum(deutsch);
            }
            else if (confirmInputButton.equals(event.getSource())){
                getValue = userInput.getText();
                System.out.println(getValue);   //just for debugging
                userInput.setText("");
                
                boolean finished = false;                 
                Command command = parser.getCommand(getValue);
                finished = processCommand(command);                
            }
            
            
        }
    }
        
      
}


    
