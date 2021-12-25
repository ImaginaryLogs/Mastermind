import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {
    private static final int widthSize = 600;
    private static final int buttonSize = 10;




    public static void main(String[] args) {
        Main game = new Main();
        game.setTitle("Mastermind");
    }

    Main() {
        initButton();
    }

    void initButton() {
        final JButton buttonRed = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonBlue = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonGreen = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonYellow = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonBlack = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonWhite = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonBrown = new JButtonConstructor(1, buttonSize, "");
        final JButton buttonOrange = new JButtonConstructor(1, buttonSize, "");
        try {
            ImageIcon redBallIcon = new ImageIcon("redball.png");
            ImageIcon blueBallIcon = new ImageIcon("blueball.png");
            ImageIcon brownBallIcon = new ImageIcon("brownball.png");
            ImageIcon greenBallIcon = new ImageIcon("greenball.png");
            ImageIcon blackBallIcon = new ImageIcon("blackball.png");
            ImageIcon whiteBallIcon = new ImageIcon("whiteball.png");
            ImageIcon yellowBallIcon = new ImageIcon("yellowball.png");
            ImageIcon orangeBallIcon = new ImageIcon("orangeball.png");

            buttonRed.setIcon(redBallIcon);
            buttonBlue.setIcon(blueBallIcon);
            buttonBlack.setIcon(blackBallIcon);
            buttonBrown.setIcon(brownBallIcon);
            buttonGreen.setIcon(greenBallIcon);
            buttonYellow.setIcon(yellowBallIcon);
            buttonWhite.setIcon(whiteBallIcon);
            buttonOrange.setIcon(orangeBallIcon);
        } catch (Exception e) {
            System.out.println(e);
        }
        // Main Four Panels
        JPanel pinPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel boardPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        pinPanel.setPreferredSize(new Dimension(widthSize, widthSize / 25 + 25));
        pinPanel.setBackground(new Color(0xB07C5B));

        infoPanel.setPreferredSize(new Dimension(widthSize - (widthSize / 6) * 5, widthSize / 500));
        infoPanel.setBackground(new Color(0xFFF3A254));

        boardPanel.setMinimumSize(new Dimension(widthSize - widthSize / 6, widthSize / 500));
        boardPanel.setBackground(new Color(0xDA7C7C));

        controlPanel.setPreferredSize(new Dimension(widthSize, widthSize / 5));
        controlPanel.setBackground(new Color(0xB07C5B));

        // Sub Panels
        JPanel selectBallButtonPanel = new JPanel();
        JPanel boardButtonPanel = new JPanel();

        selectBallButtonPanel.setPreferredSize(new Dimension(widthSize, widthSize / 25));

        boardButtonPanel.setMinimumSize(new Dimension(widthSize, widthSize / 25));
        boardButtonPanel.setBackground(new Color(0x33300));

        // mainFrame creation
        FrameSettings mainFrame = new FrameSettings(widthSize);
        mainFrame.setLayout(new BorderLayout(5, 5));
        mainFrame.add(infoPanel, BorderLayout.EAST);
        mainFrame.add(boardPanel, BorderLayout.CENTER);
        mainFrame.add(controlPanel, BorderLayout.SOUTH);

        // add sub panels
        //boardPanel.add(pinPanel, BorderLayout.NORTH);
        boardPanel.add(boardButtonPanel, BorderLayout.SOUTH);

        // misc
        {
            //pinPanel.setVisible(true);
            //pinPanel.setOpaque(true);
            infoPanel.setVisible(true);
            infoPanel.setOpaque(true);
            boardPanel.setVisible(true);
            boardPanel.setOpaque(true);
            controlPanel.setVisible(true);
            controlPanel.setOpaque(true);
        }
        {
            selectBallButtonPanel.setVisible(true);
            selectBallButtonPanel.setOpaque(true);
            boardButtonPanel.setVisible(true);
            boardButtonPanel.setOpaque(true);
        }

        //Add buttons to the pin panel
        controlPanel.add(buttonRed);
        controlPanel.add(buttonBlack);
        controlPanel.add(buttonBrown);
        controlPanel.add(buttonBlue);

        controlPanel.add(buttonGreen);
        controlPanel.add(buttonYellow);
        controlPanel.add(buttonWhite);
        controlPanel.add(buttonOrange);

        controlPanel.setLayout(new GridLayout(2, 4));
        // TODO: create radio buttons for the balls per row on the main plate and create a list that holds the values of
        //  each row's content that are list of each balls inside.

        ArrayList<JButton> boardButtonsList = new ArrayList<JButton>();

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 4; j++) {
                JButton btn = new JButtonConstructor(1, buttonSize, "");
                boardButtonsList.add(btn);
                boardButtonPanel.add(btn);
            }
            System.out.println(boardButtonsList);
        }
        boardButtonPanel.setLayout(new GridLayout(7, 5));

    }



}