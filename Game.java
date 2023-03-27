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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Room nextRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, courtyard, fields, stadium, bathroom1, bathroom2, storageroom1, storageroom2, parkinglot, highway, forest;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        courtyard= new Room("at the courtyard of the University");
        fields= new Room("at the sport practice fields");
        stadium= new Room("at the ultimate place to see sports");
        bathroom1= new Room("smelling both 1 and 2 at the same time...gross");
        bathroom2= new Room("thankful there is toilet paper in this bathroom at least");
        storageroom1= new Room("lost in a room where there just happens to be too much stuff");
        storageroom2= new Room("in the staff storage room");
        parkinglot = new Room("where paradise was at least until they put up this parking lot");
        forest = new Room("staring into the endless void of trees, better not go in");
        highway = new Room("definitely not goin gto walk into all of that traffic. People got places to be");
        
        // initialise room exits in the fashion of locationStart.setExit("direction", locationEnd);
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", courtyard);
        
        courtyard.setExit("north",parkinglot);
        courtyard.setExit("south",outside);    
        courtyard.setExit("east",fields);
        courtyard.setExit("west",stadium);
        
        parkinglot.setExit("west", highway);
        parkinglot.setExit("east", forest);
        parkinglot.setExit("south", outside);
        
        theater.setExit("west", outside);
        theater.setExit("east", storageroom1);
        
        storageroom1.setExit("west",theater);
        storageroom1.setExit("north",bathroom2);
        
        bathroom2.setExit("south",storageroom1);

        pub.setExit("east", outside);
        pub.setExit("south", bathroom1);
        
        bathroom1.setExit("north", pub);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("south",storageroom2);
        
        storageroom2.setExit("north",office);
        
        highway.setExit("east", parkinglot);
        
        forest.setExit("west", parkinglot);
        
        stadium.setExit("east", courtyard);
        
        fields.setExit("west", courtyard);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
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
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case SEARCH:
                searchRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            

        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * This section is to deal with going back and forth between the current node and the last node.
     */
    private void backRoom(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Back to where exactly?");
            return;
        }
        
    }

    
    /**
     * This is the section of the command Search, if we don't have a second word it asks where are we searching
     * otherwise we are going to "search" for object in the room and check if we will "interact" with said object
     * mode code must be placed here before this is considered acceptable
     */
    private void searchRoom(Command command)
    {
        if(!command.hasSecondWord())
        {
            //if the room is not referenced we do not know what to search.
            System.out.println("Search what?");
            return;
        }
        
    }
    
    /**
     * This is the section of the command Interact, if we don't have a second word we do not know what object we are interacting with
     * also more code must be placed here before it is considered acceptable
     */
    private void interactRoom(Command command)
    {
        if(!command.hasSecondWord())
        {
            //if the object is not referenced we do not know what to interact with
            System.out.println("Just what are we interacting with?");
            return;
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
