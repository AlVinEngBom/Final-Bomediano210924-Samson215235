import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PuzzleCanvas extends JComponent{
    private int width, height, playerCollision;
    
    public PuzzleCanvas(int w, int h){
        //sets the values to draw the rectangles and starts the timer
        animationTimer.start();
        width = w;
        height = h;
        playerCollision = 0;
        
   }
   //draws the rectangles
    protected void paintComponent(Graphics g){

   }

   
    Timer animationTimer = new Timer(20, new ActionListener(){
    public void actionPerformed(ActionEvent ae){
        repaint();
    }
        
   });
    public static void main (String[] args){
        int w = 512;
        int h = 768;
        // new JFrame
        JFrame f = new JFrame();
        PuzzleCanvas bc = new PuzzleCanvas(w,h);
        bc.setPreferredSize(new Dimension(512,768));
        f.add(bc);
        f.setTitle("Battle!!");
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }   
}
