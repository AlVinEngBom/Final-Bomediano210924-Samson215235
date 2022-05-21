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

//This class contains the code that manages the player's appearance, process and logic.
//The methods in this class will be used in game canvas to make the game work properly.

import java.awt.*;

public class Player {
    private int x,y,width,height,speed;
    private boolean up,down,left,right;
    
    //sets the location, size and speed of the player.
    public Player(int x, int y, int width, int height, int speed){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        up = down = left = right = false;
    }
    //draws the rectangle
    public void draw(Graphics g, Image i){
        g.drawImage(i, x, y, null);
    }
    //mutator methods to change certain fields
    public void stop() {
        up = down = left = right = false;
    }
    public void boundRight() {
        this.right = false;
    }
    public void boundLeft() {
        this.left = false;
    } 
    public void boundBottom() {
        this.down = false;
    } 
    public void boundTop() {
        this.up = false;
    } 
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void move(){
        if(up){
           y -= speed; 
        }
        else if(down){
            y += speed; 
        }
        else if(left){
            x -= speed; 
        }
        else if(right){
            x += speed; 
        }
    }
    public void setDirection(String dir){
        if(dir.equals("up")){
            up = true;
            down = left = right = false;
        }
        else if(dir.equals("down")){
            down = true;
            up = left = right = false;
        }
        else if(dir.equals("left")){
            left = true;
            up = down = right = false;
        }
        else if(dir.equals("right")){
            right = true;
            up = left = down = false;
        }
        else{
            up = down = left = right = false;
        }
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
    //booleans for collision
    //for enemy
    public boolean enemyIsColliding(Enemy other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
    //for walls
    public boolean wallIsColliding(Wall other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
