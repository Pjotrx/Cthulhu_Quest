import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Color;


/**
 * Write a description of class Display here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Display
{
   JFrame window;
   JPanel  buttonPanel, titlePanel, startButtonPanel, mainText, optionPanel, languagePanel, inputPanel;
   JLabel titleLabel;
   JButton button, startButton, option1, option2, language1, language2, confirmInputButton;
   JTextArea mainTextArea;
   JTextField userInput;
   Container con;
   
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 50);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font smallFont = new Font("Times New Roman", Font.PLAIN, 12);
    
    
    
   
   public Display(int x, int y){
       this.window = new JFrame();
       this.window.setSize(x, y);
       this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //important to close the window properly
       this.window.getContentPane().setBackground(Color.black);
       this.window.setLayout(null); //set default layout to null
       
       this.titlePanel = new JPanel();
       
       this.buttonPanel = new JPanel();
       
       this.languagePanel = new JPanel();
       
       this.con = window.getContentPane();
       
       con.add(titlePanel);
       con.add(buttonPanel);
       con.add(languagePanel);
    }
   /**
     * Create a window with the start button etc
     */
   
   public void addButton(String title, Color background, Color foreground){
        this.button = new JButton(title);
        this.button.setBackground(background);
        this.button.setForeground(foreground);
        this.button.setFont(normalFont);
        this.button.addActionListener(Game.tsHandler);
        buttonPanel.add(button);
   }
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
        startButton.addActionListener(Game.tsHandler);
        
        
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
        language1.addActionListener(Game.tsHandler);
        languagePanel.add(language1);
        
        language2 = new JButton("De");
        language2.setBackground(Color.black);
        language2.setForeground(Color.white);
        language2.setFont(normalFont);
        language2.addActionListener(Game.tsHandler);
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
        confirmInputButton.addActionListener(Game.tsHandler);
        inputPanel.add(userInput);
        con.add(confirmInputButton); 
        
        window.getRootPane().setDefaultButton(confirmInputButton);
    }
    
   
    
}
