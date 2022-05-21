/**
    @authors Al Vincent E. Bomediano (210924), Jerril Nheo A. Samson (215235)
    @version May 21, 2022
**/
/*
    We have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.
    We have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.
    If any Java language code or documentation used in our program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of our program.
*/

//This class contains the main method that will start the game from the client's side. 
//Sets the window size, connection to the server, gui and keybindings.

public class GameStarter {

    public static void main (String[] args){
        GameFrame gf = new GameFrame(1024, 576);
        gf.connectToServer();
        gf.setUpGUI();
        gf.addKeyBindings();
    }
}
