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

// Java class that creates an enemy for the playerFighter to avoid. 
// These also contains methods for movement and collissions to be called in the GameCanvas

import java.awt.*;

public class Enemy{
    private int x,y,width,height;
    private double xspeed,yspeed;
   
    //a constructor that accepts arguments for the x and y positions, width, height, 
    //and speed of the rectangle
    public Enemy(int x, int y, double xspeed, double yspeed){
        this.x = x;
        this.y = y;
        width = 35;
        height = 40;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
    }
    //draws the rectangle
    public void draw(Graphics g, Image i){
        g.drawImage(i, x, y, null);
    }
    //acccessor methods to return all instance fields
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
    public double getXspeed(){
        return xspeed;
    }
    public double getYspeed(){
        return yspeed;
    }
    //reverses the horizontal direction
    public void reverseX(){
        xspeed *= -1;
    }
    //reverses the vertical direction
    public void reverseY(){
        yspeed *= -1;
    }
    //reverses the direction for both
    public void reverseSpeed(){
        xspeed *= -1;
        yspeed *= -1;
    }
    //moves the x
    public void moveX(){
        x += xspeed;
    }
    //moves the y
    public void moveY(){
        y += yspeed;
    }
    //moves x and y
    public void move(){
        x += xspeed;
        y += yspeed;
    }
    //boolean for collision
    public boolean isColliding(Enemy other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
