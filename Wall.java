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
    //boolean for collision
    public boolean isColliding(Enemy other){
        return !(   this.x + this.width <= other.getX() ||
                    this.x >= other.getX() + other.getWidth() ||
                    this.y + this.height <= other.getY() ||
                    this.y >= other.getY() + other.getHeight() );
    }
}
