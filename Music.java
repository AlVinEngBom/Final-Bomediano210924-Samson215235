//Music is responsible for getting the file name of the music file
//The music file should be a .wav to work
//source: https://www.youtube.com/watch?v=TNNcAGl_7WA 
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Music {
    Clip clip;
    //gets the music file and open/plays it
    public void setURL(URL soundFileName){
        
        try{
            AudioInputStream music = AudioSystem.getAudioInputStream(soundFileName);
            clip = AudioSystem.getClip();
            clip.open(music);
        }
        catch(Exception e){
            System.out.println("Exception ex in setURL().");
        }
    }
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }
}
