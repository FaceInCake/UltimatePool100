package misc;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
    public static int maxPoolBall = 4;
    public static Clip[] poolBalls = new Clip [maxPoolBall];
    static{ for(int i=0;i<maxPoolBall;i++) poolBalls[i] = getClip("PoolBall.wav");}
    public static int curPoolBall = 0;
    
    private static Clip getClip (String fileName) {
        String filePath = "assets/audio/"+fileName;
        File f = new File(filePath);
        AudioInputStream ais;
        try { ais = AudioSystem.getAudioInputStream(f);
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace(System.err);
            return null;
        }
        AudioFormat af = ais.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, af);
        Clip aclip;
        try { aclip = (Clip) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            e.printStackTrace(System.err);
            return null;
        }
        try { aclip.open(ais);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
        return aclip;
    }

    public static void playPoolBallColl () {
        Clip pb = poolBalls[curPoolBall];
        pb.stop();
        pb.setMicrosecondPosition(0);
        pb.start();
        curPoolBall = (curPoolBall + 1) % maxPoolBall;
    }

}
