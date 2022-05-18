//This class extendsJComponent and overrides thepaintComponent method in
//order to create the custom drawing.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//testing commit
public class GameCanvas extends JComponent{

    public GameCanvas() {
        System.out.println("Hello there!");
        setPreferredSize( new Dimension(1024, 768) );
    }

  
}