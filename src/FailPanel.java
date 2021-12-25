import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FailPanel{
    FailPanel(int Appsize) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        JLabel failText = new JLabel();
        JFrame failFrame = new FrameSettings((Appsize + 16)/4);

        failFrame.setLayout(new BorderLayout(10, 10));

        failFrame.setVisible(true);
        failFrame.getContentPane().setBackground(Color.WHITE);

        failText.setText("Game Over");
        failText.setVerticalAlignment(JLabel.CENTER);
        failText.setHorizontalAlignment(JLabel.CENTER);
        failText.setMaximumSize(new Dimension(400,400));
        failText.setBackground(new Color(0x0000cc));
        failText.setBorder(BorderFactory.createLineBorder(new Color(0xddaa00)));
        failText.setForeground(new Color(0xddaa00));
        failText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        failText.setVisible(true);
        failText.setOpaque(true);

        failFrame.pack();

        new PlaySounds(3);
        failFrame.add(failText);
    }
}