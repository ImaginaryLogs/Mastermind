import javax.swing.*;
import java.awt.*;

public class JFrameConstructor extends JFrame{
    public JFrameConstructor(int widthSize){
        this.setSize(widthSize, widthSize+(widthSize/2));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(true);
        this.setTitle("Mastermind v. 1");
        this.getContentPane().setBackground(new Color(0x333333));
        this.setMinimumSize(new Dimension(widthSize,widthSize +(widthSize/2)));
    }
}
