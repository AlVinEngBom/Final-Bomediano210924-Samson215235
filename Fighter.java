import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Fighter {
    private int x,y,width,height;

    public Fighter(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    public boolean isColliding(Fighter other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
