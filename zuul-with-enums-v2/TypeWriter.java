import java.util.Timer; 
import java.util.TimerTask; 
/**
 * Write a description of class TypeWriter here.
 *
 * @author Pjotr F.W. Kooijmans 
 * @version 2020.01.15
 */
public class TypeWriter
{
    // instance variables - replace the example below with your own
    private String string, string2;
    int i, waitTime, j;
    Timer myTimer;
    String str;
    String[] glitch = {"", ""};
    int duration;
    
    TimerTask task = new TimerTask(){
        public void run() {
            if(i < string.length()){
                //System.out.println(string.charAt(i));
                str += String.valueOf(string.charAt(i));  
                Game.display.mainTextArea.setText(str);
            }
            i++;
        }
    };
    

    public void start(){
        myTimer.schedule(task, 30, 30); 
    }
    
    /**
     * Constructor for objects of class TypeWriter
     */
    public TypeWriter(int i, int waitTime){
         this.myTimer = new Timer();
         this.string = "";
         this.i = i;
         this.waitTime = waitTime;
         
    }
    
    public void type(String string){
        this.i = 0;
        this.string = string;
        str = "";
    }
    
   

    
}

