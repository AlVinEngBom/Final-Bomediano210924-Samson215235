import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.text.DecimalFormat;

public class GameCanvas extends JComponent{

    private int width, height, gameTime, playerID, fighterHealth;
    private Player fighter, solver;
    private ArrayList<Enemy> Enemies;
    private boolean win, lose;
    private String ddSecond,ddMinute, timerText;
    private int second, minute;
    private int delaySecond;

    
    public GameCanvas(int w, int h, int pID){
        width = w;
        height = h;
        playerID = pID;
        setPreferredSize( new Dimension(width, height) );

        gameTime = 0;
        second = 0;
        minute = 0;

        animationTimer.start();
        fighter = new Player(100, 100, 25, 25, width, height);
        solver = new Player(200, 200, 25, 25, width, height);

        Enemies = new ArrayList<Enemy>();
        Enemies.add(new Enemy(65,305,50,50,1,3));
        Enemies.add(new Enemy(100,400,50,50,1,2));

        fighterHealth = 3;
        win = lose = false;
        

   }
   
    protected void paintComponent(Graphics g){
        
        Image img = new ImageIcon("Sprites/GameBackground.png").getImage();
        g.drawImage(img, 0, 0, null);

        g.setColor(Color.RED);
        for(int i = 0; i < Enemies.size(); i+=1){
            Enemies.get(i).draw(g);
        }

        g.setColor(Color.GREEN);
        fighter.draw(g);

        g.setColor(Color.BLUE);
        solver.draw(g);

        g.setColor(Color.WHITE);
        g.drawString(timerText,497,20);

        g.setColor(Color.WHITE);
        g.drawString("Lives: " +fighterHealth,490,30);
   }

    public Player getPlayer(){
        if (playerID == 1) {
            return fighter;
        } else {
            return solver;
        }
    }
    Timer animationTimer = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            
            delaySecond++;
            second = delaySecond/100;
            // minute = delayMinute/100;
            DecimalFormat dFormat = new DecimalFormat("00");
            ddSecond = dFormat.format(second);
            ddMinute = dFormat.format(minute);
            timerText = ddMinute + ":" + ddSecond;

            if(second == 60){
                delaySecond = 0;
                second = 0;
                minute++;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                timerText = ddMinute + ":" + ddSecond;
            } 

            gameTime++;
            // System.out.println(gameTime/100);
            if(gameTime == 1000){
                Enemies.add(new Enemy(5,5,50,50,3,1));

            }
            else if(gameTime == 2000){
                Enemies.add(new Enemy(457,5,50,50,1,4));
            }
            else if(gameTime == 3000){
                Enemies.add(new Enemy(5,413,50,50,1,1));
            }
            else if(gameTime == 4000){
                Enemies.add(new Enemy(457,413,50,50,3,2));
            }
            else if(gameTime == 5000){
                Enemies.add(new Enemy(5,5,50,50,2,3));
            }
            else if(gameTime == 6000){
                Enemies.add(new Enemy(457,50,50,50,1,2));
            }
            else if(gameTime == 7000){
                Enemies.add(new Enemy(50,413,50,50,3,1));
            }
            else if(gameTime == 8000){
                Enemies.add(new Enemy(457,413,50,50,1,2));
            }
            

            for(int i = 0; i < Enemies.size(); i+=1){
                if(Enemies.get(i).getX() <= 0){ 
                    Enemies.get(i).reverseX();
                }
                else if(Enemies.get(i).getX() + Enemies.get(i).getWidth() >= width){ 
                    Enemies.get(i).reverseX();
                }
                if(Enemies.get(i).getY() <= 0){ 
                    Enemies.get(i).reverseY();
                }
                else if(Enemies.get(i).getY() + Enemies.get(i).getHeight() >= height){ 
                    Enemies.get(i).reverseY();
                }

                Enemies.get(i).move();
            }

            // PlayerFighter to Wall Collissions
            if(fighter.getX() <= 0){ 
                fighter.boundRight();
            }
            else if(fighter.getX() + fighter.getWidth() >= width){
                fighter.boundLeft();
            }
            if(fighter.getY() <= 0){
                fighter.boundBottom();
            }
            else if(fighter.getY() + fighter.getHeight() >= height){
                fighter.boundTop();
            }

            // Enemy to Enemy Collissions
            for(int i = 0; i < Enemies.size(); i++){
                for(int k = i; k < Enemies.size(); k++){
                    if(Enemies.get(i).isColliding(Enemies.get(k)) && (i != k)){
                        Enemies.get(i).reverseSpeed();
                        Enemies.get(k).reverseSpeed();
                    }
                }
            }

            // Enemy to PlayerFighter Collissions
            for(int i = 0; i < Enemies.size(); i++){
                if(fighter.isColliding(Enemies.get(i))){
                    fighterHealth -= 1;
                    // Enemies.get(i).reverseSpeed();
                    Enemies.remove(i);     
                    
                    if(fighterHealth == 0){
                        lose = true;
                        fighter.stop();
                        animationTimer.stop();
                        ImageIcon loseIcon = new ImageIcon("Sprites/LoseEmoji.png");
                        Image loseImage = loseIcon.getImage();
                        Image modifiedLoseImage = loseImage.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
                        loseIcon = new ImageIcon(modifiedLoseImage);
                        JOptionPane.showMessageDialog(null, "Fighter ran out of lives!!","YOU LOST!", JOptionPane.INFORMATION_MESSAGE,loseIcon);
                    }
                }
                
            }

            fighter.move();
            solver.move();
            repaint();

        }
   });
}
