import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
/**
 * The Display class provides methods for creating the graphical user interface
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Display
{
   JFrame window;
   JPanel  imagePanel, buttonPanel, titlePanel, startButtonPanel, mainText, optionPanel, languagePanel, inputPanel;
   JLabel titleLabel;
   JButton quitButton, startButton, option1, option2, language1, language2, language3, confirmInputButton, continueButton;
   JTextArea mainTextArea, titleTextArea;
   JTextField userInput;
   Container con;
   
    URL ge_flag = Game.class.getResource("images/ge_flag.png");
   URL ge_flag_pressed = Game.class.getResource("images/ge_flag_pressed.png");
   URL ge_flag_hover = Game.class.getResource("images/ge_flag_hover.png");
   
   URL en_flag = Game.class.getResource("images/en_flag.png");
   URL en_flag_pressed = Game.class.getResource("images/en_flag_pressed.png");
   URL en_flag_hover = Game.class.getResource("images/en_flag_hover.png");
   
   URL nl_flag = Game.class.getResource("images/nl_flag.png");
   URL nl_flag_pressed = Game.class.getResource("images/nl_flag_pressed.png");
   URL nl_flag_hover = Game.class.getResource("images/nl_flag_hover.png");
   
   Font titleFont = new Font("Times New Roman", Font.PLAIN, 50);
   Font normalFont = new Font(Font.MONOSPACED, Font.PLAIN, 18);
   Font smallFont = new Font(Font.MONOSPACED, Font.PLAIN, 5);
    /**
     * Constructor for objects of class Display
     */
   public Display(int x, int y, String title, Color background, Color foreground){
      this.window = new JFrame();
      this.window.setSize(x, y);
      this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //important to close the window properly
      this.window.getContentPane().setBackground(Color.black);
      this.window.setResizable(false);
      this.window.setLayout(null); 
       
       
      this.titlePanel = new JPanel();
      this.titleLabel = new JLabel();
      this.imagePanel = new JPanel();
      this.buttonPanel = new JPanel();       
      this.mainText = new JPanel(); 
      this.mainTextArea = new JTextArea(); 
      this.titleTextArea = new JTextArea();
      this.inputPanel = new JPanel();       
      this.userInput = new JTextField("");       
      this.languagePanel = new JPanel();
      this.con = window.getContentPane();

      con.add(mainText);
      
      con.add(titlePanel);
      con.add(imagePanel);
      con.add(buttonPanel);
      con.add(languagePanel);
      con.add(buttonPanel);
      con.add(inputPanel);
      
   }
   
   /**
   * Create and add the buttons to the display
   */
   public void addButtons(Game.TitleScreenHandler tsHandler, String start, String quit, String back, String confirmInput, Color background, Color foreground){
       this.startButton = new JButton(start);
       this.startButton.setBackground(background);
       this.startButton.setForeground(foreground);
       this.startButton.setFont(normalFont);
       this.startButton.addActionListener(tsHandler);
       
       this.quitButton = new JButton(quit);
       this.quitButton.setBackground(background);
       this.quitButton.setForeground(foreground);
       this.quitButton.setFont(normalFont);
       this.quitButton.addActionListener(tsHandler);
       
       this.continueButton = new JButton(back);
       this.continueButton.setBackground(background);
       this.continueButton.setForeground(foreground);
       this.continueButton.setFont(normalFont);
       this.continueButton.addActionListener(tsHandler);
       
       this.confirmInputButton = new JButton(confirmInput);
       this.confirmInputButton.setBackground(background);
       this.confirmInputButton.setForeground(foreground);
       this.confirmInputButton.setFont(normalFont);
       this.confirmInputButton.setPreferredSize(new Dimension(60, 50));
       this.confirmInputButton.addActionListener(tsHandler);
       this.window.getRootPane().setDefaultButton(confirmInputButton);
       
        this.language1 = new JButton();

       if (en_flag != null && en_flag_pressed != null && en_flag_hover != null) {
           ImageIcon icon_en = new ImageIcon(en_flag);
           ImageIcon icon_pressed_en = new ImageIcon(en_flag_pressed);
           ImageIcon icon_hover_en = new ImageIcon(en_flag_hover);
           language1.setIcon(icon_en);
           language1.setRolloverIcon(icon_hover_en);
           language1.setPressedIcon(icon_pressed_en);
        }
        else{System.out.println("image not found");}
       this.language1.setBorder(BorderFactory.createEmptyBorder());
       this.language1.setContentAreaFilled(false);
       
        
       this.language1.setBackground(background);
       this.language1.setForeground(foreground);
       this.language1.setFont(normalFont);
       this.language1.addActionListener(tsHandler);
       
       this.language2 = new JButton();
       if (ge_flag != null && ge_flag_pressed != null && ge_flag_hover != null) {
           ImageIcon icon_ge = new ImageIcon(ge_flag);
           ImageIcon icon_pressed_ge = new ImageIcon(ge_flag_pressed);
           ImageIcon icon_hover_ge = new ImageIcon(ge_flag_hover);
           language2.setIcon(icon_ge);
           language2.setRolloverIcon(icon_hover_ge);
           language2.setPressedIcon(icon_pressed_ge);
        }
        else{System.out.println("image not found");}
        this.language2.setBorder(BorderFactory.createEmptyBorder());
       this.language2.setContentAreaFilled(false);
       this.language2.setBackground(background);
       this.language2.setForeground(foreground);
       this.language2.setFont(normalFont);
       this.language2.addActionListener(tsHandler);
       
       
       
       this.language3 = new JButton();
       if (ge_flag != null && ge_flag_pressed != null && ge_flag_hover != null) {
           ImageIcon icon_nl = new ImageIcon(nl_flag);
           ImageIcon icon_pressed_nl = new ImageIcon(nl_flag_pressed);
           ImageIcon icon_hover_nl = new ImageIcon(nl_flag_hover);
           language3.setIcon(icon_nl);
           language3.setRolloverIcon(icon_hover_nl);
           language3.setPressedIcon(icon_pressed_nl);
        }
        else{System.out.println("image not found");}
        this.language3.setBorder(BorderFactory.createEmptyBorder());
       this.language3.setContentAreaFilled(false);
       this.language3.setBackground(background);
       this.language3.setForeground(foreground);
       this.language3.setFont(normalFont);
       this.language3.addActionListener(tsHandler);
       
     
       
       
       
       languagePanel.add(language1);
       languagePanel.add(language2);
       languagePanel.add(language3);
       //inputPanel.add(backButton);
       
       inputPanel.add(confirmInputButton,"East");
       buttonPanel.add(startButton);
       buttonPanel.add(quitButton);
       buttonPanel.add(continueButton);
   }
   
   
    
    public void setMainTextArea(int x, int y, int width, int height, String text, Color background, Color foreground){
        this.mainTextArea = new JTextArea(text);
        this.mainTextArea.setBounds(x, y, width, height);
        this.mainTextArea.setBackground(background);
        this.mainTextArea.setForeground(foreground);
        this.mainTextArea.setEditable(false);
        this.mainTextArea.setFont(normalFont);
        this.mainTextArea.setLineWrap(true);
        this.mainTextArea.setWrapStyleWord(true);
        
        this.mainText.add(mainTextArea);
    }
    
    public void setTitleTextArea(int x, int y, int width, int height, String text, Color background, Color foreground){
        this.titleTextArea = new JTextArea(text);
        this.titleTextArea.setBounds(x, y, width, height);
        this.titleTextArea.setBackground(background);
        this.titleTextArea.setForeground(foreground);
        this.titleTextArea.setEditable(false);
        this.titleTextArea.setFont(smallFont);
        this.titleTextArea.setLineWrap(true);
        this.titleTextArea.setWrapStyleWord(true);
        
        this.imagePanel.add(titleTextArea);
    }
    
    public void setMainTextPanel(int x, int y, int width, int height, Color background){
        this.mainText.setBounds(x, y, width, height);
        this.mainText.setBackground(background);
    }
    
    public void setTitleLabel(String title){
        this.titleLabel.setText(title);
        this.titleLabel.setForeground(Color.white);
        this.titleLabel.setFont(titleFont);
        this.titlePanel.add(titleLabel);
    }
    
    public void setTitlePanel(int x, int y, int width, int height, Color background){
        this.titlePanel.setBounds(x, y, width, height);
        //this.titlePanel.setLayout(new GridLayout(2,0));
        this.titlePanel.setBackground(background);
    }
    
    public void setImagePanel(int x, int y, int width, int height, Color background){
        this.imagePanel.setBounds(x, y, width, height);
        //this.titlePanel.setLayout(new GridLayout(2,0));
        this.imagePanel.setBackground(background);
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
       this.inputPanel.setLayout( new BorderLayout() );
       this.userInput.setSize(80, 20);
       this.inputPanel.add(userInput, "Center");
    }

    
    
}
