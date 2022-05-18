import java.awt.*;

public class Enemy{
    private int x,y,width,height;
    private double xspeed,yspeed;
   
    //a constructor that accepts arguments for the x and y positions, width, height, 
    //and speed of the rectangle
    public Enemy(int x, int y, int width, int height, double xspeed, double yspeed){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
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
    public static Object add(Enemy currentEnemy) {
        return currentEnemy;
    }
}
