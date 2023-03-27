/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */



/**
 * Don Santiago || G00301287 || Don.Santiago@live.com || Ghengis_Don
 * this class is of the command words that are reserved for usage that exists in Game
 */


public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"), SEARCH("search"), ACTION("interact"), UNKNOWN("?"), BACK("back");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
