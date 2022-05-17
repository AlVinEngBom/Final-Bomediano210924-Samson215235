import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Puzzle extends JComponent{
    private Enemy e1;
    private Enemy e2;
    private int width, height, playerCollision;
    
    public Puzzle(int w, int h){
        //sets the values to draw the rectangles and starts the timer
        animationTimer.start();
        e1 = new Enemy(10,250,50,50,3,3);
        e2 = new Enemy(100,50,50,50,3,3);
        width = w;
        height = h;
        playerCollision = 0;
        
   }
   //draws the rectangles
    protected void paintComponent(Graphics g){
        g.setColor(Color.RED);
        e1.draw(g);

        g.setColor(Color.RED);
        e2.draw(g);
   }

   
    Timer animationTimer = new Timer(20, new ActionListener(){
    public void actionPerformed(ActionEvent ae){
        //move e1
        if(e1.getX() <= 0){ //left side
            e1.reverseX();
        }
        else if(e1.getX() + e1.getWidth() >= width){ //right side
            e1.reverseX();
        }
        if(e1.getY() <= 0){ // top side
            e1.reverseY();
        }
        else if(e1.getY() + e1.getHeight() >= height){ //bottom side
            e1.reverseY();
        }

        //move e2
        if(e2.getY() <= 0){ //top side
            e2.reverseY();
        }
        else if(e2.getY() + e2.getHeight() >= height){ //bottom side
            e2.reverseY();
        }

        if(e2.getX() <= 0){ //left side
            e2.reverseX();
        }
        else if(e2.getX() + e2.getWidth() >= width){ //right side
            e2.reverseX();
        }

        //reverses the speed if the two rectangles are colliding
        if(e1.isColliding(e2)){
            e1.reverseSpeed();
            e2.reverseSpeed();
        }
        //starts moving the enemies
        e1.move();
        e2.move();
        repaint();
    }
        
   });
    public static void main (String[] args){
        int w = 512;
        int h = 768;
        // new JFrame
        JFrame f = new JFrame();
        BattleCanvas bc = new BattleCanvas(w,h);
        bc.setPreferredSize(new Dimension(512,768));
        f.add(bc);
        f.setTitle("Battle!!");
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }   
}
