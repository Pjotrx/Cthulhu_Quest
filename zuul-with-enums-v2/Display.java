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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Write a description of class Display here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Display
{
    // instance variables - replace the example below with your own
     JFrame window;
   JPanel  buttonPanel, titlePanel, startButtonPanel, mainText, optionPanel, languagePanel, inputPanel;
   JLabel titleLabel;
   JButton button, startButton, option1, option2, language1, language2, confirmInputButton, backButton;
   JTextArea mainTextArea;
   JTextField userInput;
   Container con;
   
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 50);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    Font smallFont = new Font("Times New Roman", Font.PLAIN, 12);
    /**
     * Constructor for objects of class Display
     */
    public Display(int x, int y, String title, Color background, Color foreground){
       this.window = new JFrame();
       this.window.setSize(x, y);
       this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //important to close the window properly
       this.window.getContentPane().setBackground(Color.black);
       this.window.setLayout(null); //set default layout to null
       
       
       this.titlePanel = new JPanel();
       
       this.buttonPanel = new JPanel();
       
       this.mainText = new JPanel();
       
       this.mainTextArea = new JTextArea();
       
       this.inputPanel = new JPanel();
       
       this.userInput = new JTextField("");
       
       this.languagePanel = new JPanel();
       
       this.con = window.getContentPane();
       
      
      
      
      con.add(mainText);
      
      con.add(titlePanel);
      con.add(buttonPanel);
      con.add(languagePanel);
      con.add(buttonPanel);
      con.add(inputPanel);
      window.setVisible(true);
    }
    
    public void addButton(String title, Color background, Color foreground){
        this.button = new JButton(title);
        this.button.setBackground(background);
        this.button.setForeground(foreground);
        this.button.setFont(normalFont);
        //this.button.addActionListener(Game.tsHandler);
        buttonPanel.add(button);
   }
   
   public void addButtons(Game.TitleScreenHandler tsHandler, String start, String confirmInput, String languageString1, String languageString2, String back, Color background, Color foreground){
       this.startButton = new JButton(start);
       this.startButton.setBackground(background);
       this.startButton.setForeground(foreground);
       this.startButton.setFont(normalFont);
       this.startButton.addActionListener(tsHandler);
       
       this.confirmInputButton = new JButton(confirmInput);
       this.confirmInputButton.setBackground(background);
       this.confirmInputButton.setForeground(foreground);
       this.confirmInputButton.setFont(normalFont);
       this.confirmInputButton.addActionListener(tsHandler);
       this.window.getRootPane().setDefaultButton(confirmInputButton);
       
       this.language1 = new JButton(languageString1);
       this.language1.setBackground(background);
       this.language1.setForeground(foreground);
       this.language1.setFont(normalFont);
       this.language1.addActionListener(tsHandler);
       
       this.language2 = new JButton(languageString2);
       this.language2.setBackground(background);
       this.language2.setForeground(foreground);
       this.language2.setFont(normalFont);
       this.language2.addActionListener(tsHandler);
       
       this.backButton = new JButton(back);
       this.backButton.setBackground(background);
       this.backButton.setForeground(foreground);
       this.backButton.setFont(normalFont);
       this.backButton.addActionListener(tsHandler);
       
       
       
       languagePanel.add(language1);
       languagePanel.add(language2);
       inputPanel.add(backButton);
       
       inputPanel.add(confirmInputButton);
       buttonPanel.add(startButton);
   }
   
   
    
    public void setMainTextArea(int x, int y, int width, int height, String text, Color background, Color foreground){
        this.mainTextArea = new JTextArea(text);
        this.mainTextArea.setBounds(x, y, width, height);
        this.mainTextArea.setBackground(background);
        this.mainTextArea.setForeground(foreground);
        this.mainTextArea.setEditable(false);
        this.mainTextArea.setFont(normalFont);
        this.mainTextArea.setLineWrap(true);
        
        this.mainText.add(mainTextArea);
    }
    
    public void setMainTextPanel(int x, int y, int width, int height, Color background){
        this.mainText.setBounds(x, y, width, height);
        this.mainText.setBackground(background);
    }
    
    public void setLanguagePanel(int x, int y, int width, int height, Color background){
       this.languagePanel.setBounds(x, y, width, height);
       this.languagePanel.setBackground(background);
    }
    
    public void setButtonPanel(int x, int y, int width, int height, Color background){
       this.buttonPanel.setBounds(x, y, width, height);
       this.buttonPanel.setBackground(background);
    }
    
    public void setInputPanel(int x, int y, int width, int height, Color background){
       this.inputPanel.setBounds(x, y, width, height);
       this.inputPanel.setBackground(background);
       this.inputPanel.setLayout(new GridLayout(1, 1));
       this.inputPanel.add(userInput);
    }

    
    
}
