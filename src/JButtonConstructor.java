import javax.swing.*;
import java.awt.*;

public class JButtonConstructor extends JButton {
    public JButtonConstructor(int buttonCase, int buttonSize, String frontLabel) {
        switch (buttonCase) {
            case 1:
                this.setPreferredSize(new Dimension(2 * buttonSize, buttonSize));
                this.setText(frontLabel);
                this.setFocusable(false);
                this.setVisible(true);
            case 2:
                this.setPreferredSize(new Dimension(2 * buttonSize, buttonSize));
                this.setFocusable(false);
                this.setVisible(true);
            case 3:
                this.setPreferredSize(new Dimension(4/3 * buttonSize, buttonSize));
                this.setFocusable(false);
                this.setVisible(true);
        }
    }
}