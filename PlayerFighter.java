import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerFighter {
    private int x,y,width,height;
    private boolean up,down,left,right;
    
    public PlayerFighter(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        up = down = left = right = false;

    }
    //draws the rectangle
    public void draw(Graphics g){
        g.fillRect(x,y,width,height);
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
    public void move(){
        int speed = 5;
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
    
    
    //reverses the horizontal direction
    // public void reverseX(){
    //     xspeed *= -1;
    // }
    // //reverses the vertical direction
    // public void reverseY(){
    //     yspeed *= -1;
    // }
    // //reverses the direction for both
    // public void reverseSpeed(){
    //     xspeed *= -1;
    //     yspeed *= -1;
    // }
    // //moves the x
    // public void moveX(){
    //     x += xspeed;
    // }
    // //moves the y
    // public void moveY(){
    //     y += yspeed;
    // }
    // //moves x and y
    // public void move(){
    //     x += xspeed;
    //     y += yspeed;
    // }
    //boolean for collision
    public boolean isColliding(PlayerFighter other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
