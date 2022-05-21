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

//This class creates walls for the maze the playerSolver.
//The walls simply contains logic to not let th

import java.awt.*;

public class Wall{
    private int x,y,width,height;
    private boolean alreadyCollided;
   
    //a constructor that accepts arguments for the x and y positions, width, height, 
    //and speed of the rectangle
    public Wall(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        alreadyCollided = false;
    }
    //draws the rectangle
    public void draw(Graphics g){
        if(alreadyCollided) {
            g.fillRect(x,y,width,height);
        }
    }
    //acccessor methods to return all instance fields
    public void collided() {
        alreadyCollided = true; 
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
}
