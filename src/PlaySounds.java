import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySounds implements Runnable{
    private static final String[] soundEffectPaths = {"../sounds/bye-have-a-great-time.wav",
            "../sounds/kids-cheering-sound-effect.wav", "../sounds/movie_1.wav",
            "../sounds/oh-no-no-no-no-laugh.wav", "../sounds/vine-boom1.wav"};
    private static Clip clip;
    private static int effect;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public PlaySounds(int effect) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        this.effect = effect;
        tracker(effect);
    }

    public synchronized void tracker(int effect){

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                File file = new File(soundEffectPaths[effect]); //
                AudioInputStream file2 = null;
                try {
                    file2 = AudioSystem.getAudioInputStream(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                double seconds = ((file2.getFrameLength() + 0.00) / file2.getFormat().getFrameRate());
                long millis = (long) (seconds * 1000);
                try {
                    clip.open(file2);
                    clip.start();
                } catch (Exception f) {
                    System.out.println("Cannot find " + f);
                }
                System.out.println(millis);
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clip.stop();
                clip.close();
                System.out.println("It is Done");

            }
        };

        Thread t1 = new Thread(r1);

        t1.start();


    }
    public void run(){
        tracker(effect);
    }
}
