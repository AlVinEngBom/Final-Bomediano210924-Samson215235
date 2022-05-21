import java.awt.*;

public class Player {
    private int x,y,width,height,speed;
    private boolean up,down,left,right;
    
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
    //acccessor methods to return all instance fields
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
    
    //booleans for collision
    public boolean enemyIsColliding(Enemy other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }

    public boolean wallIsColliding(Wall other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
