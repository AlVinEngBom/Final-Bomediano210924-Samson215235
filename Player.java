import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Player {
    private int x,y,width,height,speed, boundWidth, boundHeight;
    private boolean up,down,left,right;
    
    public Player(int x, int y, int width, int height, int boundWidth, int boundHeight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        up = down = left = right = false;
        speed = 5;
        this.boundWidth = boundWidth;
        this.boundHeight = boundHeight;

        

    }
    //draws the rectangle
    public void draw(Graphics g, Image ii, int x, int y ){
        g.drawImage(ii, x, y, null);
    }
    //acccessor methods to return all instance fields
    public void stop() {
        up = down = left = right = false;
    }
    public void boundLeft() {
        x = boundWidth - width;
    }
    public void boundRight() {
        x = 0;
    } 
    public void boundTop() {
        // y = 0;
        y = boundHeight - height;
    } 
    public void boundBottom() {
        y = 0;
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
    
    //boolean for collision
    public boolean isColliding(Enemy other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
