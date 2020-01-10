import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.util.Locale;
import java.util.ResourceBundle;
public class CommandWords
{
    // A mapping between a command word and the CommandWord
    // associated with it.
    private static HashMap<String, CommandWord> validCommands;
    String allCommands = "";
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
                System.out.println(command.toString());
            }
        }
    }

    public static void resetEnum(Locale locale){
        Game.r = ResourceBundle.getBundle("Bundle", locale);
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(Game.r.getString(command.toString()), command);
                System.out.println(Game.r.getString(command.toString()));
            }
        }
      
    }
    
    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord The word to look up.
     * @return The CommandWord correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
    
    public String printAll() 
    {
        StringBuffer output = new StringBuffer(100);
        //output.append("test");
        for(String command : validCommands.keySet()) {
            output.append(command + "  ");
        }
        //System.out.println();
        return output.toString();
    }
}
