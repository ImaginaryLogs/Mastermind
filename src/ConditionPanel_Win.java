import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ConditionPanel_Win {
    ConditionPanel_Win(int Appsize) throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        JLabel winText = new JLabel();
        JPanel winPanel = new JPanel();
        JFrame winFrame = new JFrameConstructor((Appsize + 16)/4);

        winFrame.setLayout(new BorderLayout(10, 10));

        winFrame.setVisible(true);
        winFrame.getContentPane().setBackground(Color.WHITE);

        winPanel.setBackground(Color.WHITE);
        winPanel.setVisible(true);

        winText.setText("YOU WON :0");
        winText.setVerticalAlignment(JLabel.CENTER);
        winText.setHorizontalAlignment(JLabel.CENTER);
        winText.setMaximumSize(new Dimension(400,400));
        winText.setBackground(new Color(0xcc0000));
        winText.setBorder(BorderFactory.createLineBorder(new Color(0xddaa00)));
        winText.setForeground(new Color(0xddaa00));
        winText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        winText.setVisible(true);
        winText.setOpaque(true);

        winFrame.pack();
        new PlaySounds(1);

        Thread.sleep(2000);
        winFrame.add(winText);
    }
}
