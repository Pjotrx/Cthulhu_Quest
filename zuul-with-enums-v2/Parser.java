import java.util.Scanner;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two-word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Sjoerd Jr Helmhout and Pjotr F.W. Kooijmans 
 * @version 2020.01.24
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand(String input) 
    {
        String[] inputLine = input.split(" ");  // will hold the full input line
        String word1 = inputLine[0];
        String word2 = null;
        if(inputLine.length >= 2){
            word2 = inputLine[1];
        }        
        return new Command(commands.getCommandWord(word1), commands.getCommandWord(word2), word2);
    } 

    /**
     * Print out a list of valid command words.
     */
   /* public String showCommands()
    {
        commands.showAll();        
        return commands.printAll();
    }*/
}
