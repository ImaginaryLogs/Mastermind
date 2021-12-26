import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class MainBoard {
    // Colors for quick comparison


    // Visualization
    private static final ArrayList<ArrayList<JButton>> boardButtons = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> boardGuesses = new ArrayList<>();
    private static final ArrayList<JButton> selectorButtons = new ArrayList<>();
    private static final String[] iconPaths = {"assetPNG/redball.png", "assetPNG/greenball.png", "assetPNG/blueball.png", "assetPNG/yellowball.png",
            "assetPNG/brownball.png", "assetPNG/orangeball.png", "assetPNG/grayball.png", "assetPNG/whiteball.png"};
    private static final ArrayList<JButton> launchButtons = new ArrayList<>();
    private static final ArrayList<JPanel> pinPanel_Group = new ArrayList<>();
    private static final ArrayList<ArrayList> pins = new ArrayList<>();
    private static final ArrayList<JLabel> targets = new ArrayList<>();
    private static final int appSize = 650;
    private static final int b_ROW = 10;
    private static final int b_COLUMN = 4;
    private static int previousDisabledButton;
    private static final ImageIcon greyPNG = new ImageIcon("assetPNG/blackball.png");

    // Game
    private static boolean isAllUnique = false;
    private static final ArrayList<Integer> pass = new ArrayList<>();
    private static int chosenBall = 9;

    private enum buttonType{
        BOARD_BUTTONS,
        SELECTOR_BUTTONS,
        LAUNCHER_BUTTONS
    }
    private static final JLabel emptySign = new JLabel();


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException  {
        new MainBoard();
    }

    MainBoard() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        initButtons();
        initJFrame();
        initGame();
    }

    private static void initButtons(){
        buttonHandler handler = new buttonHandler();

        // All board buttons initialized
        for (int i=0; i<b_ROW; i++) {
            ArrayList<JButton> subList_JButton = new ArrayList<>();
            ArrayList<Integer> sublist_Integer = new ArrayList<>();
            for (int j=0; j<b_COLUMN; j++) {
                JButton button = new JButtonConstructor(3, 30, "");
                button.setBackground(new Color(0x91A47E));
                button.setBorder(BorderFactory.createLineBorder(new Color(0x91A47E)));
                button.addActionListener(handler);
                subList_JButton.add(button);
                sublist_Integer.add(9);
            }
            boardGuesses.add(sublist_Integer);
            boardButtons.add(subList_JButton);
        }
        for (int i=0; i<b_ROW - 1; i++){
            for (int j=0; j<b_COLUMN; j++){
                boardButtons.get(i).get(j).setEnabled(false);
                boardButtons.get(i).get(j).setBorder(BorderFactory.createLineBorder(new Color(0x566845)));
                boardButtons.get(i).get(j).setBackground(new Color(0x566845));


            }
        }

        // Selector buttons initialized
        for (String i : iconPaths){
            JButton button = new JButtonConstructor(1, 37,"");
            try{
                ImageIcon image = new ImageIcon(i);
                button.setIcon(image);
            }
            catch(Exception e){
                System.out.println("Error: " + e + " at: " + i);
            }
            selectorButtons.add(button);
            button.addActionListener(handler);
        }

        // Launch buttons initialized
        for (int i = 0; i<b_ROW; i++){
            JButton button = new JButtonConstructor(1, 0, "Submit");
            button.setBackground(new Color(0x566845));
            button.setBorder(BorderFactory.createLineBorder(new Color(0x566845)));


            button.setEnabled(false);
            button.setMaximumSize(new Dimension(74, 26));
            launchButtons.add(button);
            button.addActionListener(handler);
        }
        launchButtons.get(b_ROW - 1).setEnabled(true);
        launchButtons.get(b_ROW - 1).setBackground(new Color(0x91A47E));
        launchButtons.get(b_ROW - 1).setBorder(BorderFactory.createLineBorder(new Color(0x91A47E)));
    }

    public static void initJFrame() {
        JFrame MainFrame = new FrameSettings(appSize + 16);
        JPanel boardPanel = new JPanel();
        JPanel selectorPanel = new JPanel();
        JPanel launchPanel = new JPanel();
        JPanel revealPanel = new JPanel();

        // Interrupts
        {
            emptySign.setMaximumSize(new Dimension(120, 50));
            emptySign.setText("You missed a ball!");
            emptySign.setBackground(new Color(0xeee1e1));
            emptySign.setBorder(BorderFactory.createLineBorder(new Color(0x552200)));
            emptySign.setOpaque(true);
            emptySign.setForeground(new Color(0xaa2200));
            emptySign.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        }

        // pinPanel
        {
            for (int i=0; i<b_ROW; i++){
                JPanel panel = new JPanel();
                panel.setSize(50,50);
                panel.setBackground(new Color(0x97A23C));
                panel.setVisible(true);

                ArrayList<JLabel> tempLabels = new ArrayList<>();
                FlowLayout pinLayout = new FlowLayout(FlowLayout.LEADING, 18,7);

                for (int j=0; j<b_COLUMN; j++){
                    JLabel tempLabel = new JLabel();
                    tempLabel.setVisible(true);
                    try {
                        ImageIcon image = new ImageIcon("assetPNG/pin_black.png");
                        tempLabel.setIcon(image);
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    panel.setLayout(pinLayout);
                    tempLabels.add(tempLabel);
                    panel.add(tempLabel);
                }

                pins.add(tempLabels);
                pinPanel_Group.add(panel);
            }
        }

        // selectorPanel
        {
            selectorPanel.setSize(appSize, appSize / 6);
            selectorPanel.setBackground(new Color(0x224011));

            for (JButton i : selectorButtons) {
                selectorPanel.add(i);
                i.setBackground(new Color(0x566845));
            }
            FlowLayout selector_Layout = new FlowLayout(FlowLayout.CENTER, 5, 5);
            selector_Layout.setVgap(16);
            selectorPanel.setLayout(selector_Layout);
        }

        // boardPanel
        {
            boardPanel.setSize(appSize - appSize/6, (appSize + appSize/2) - appSize/6);
            boardPanel.setBackground(new Color(0x7B6843));

            // Group Layout
            GroupLayout board_GroupLayout = new GroupLayout (boardPanel);
            boardPanel.setLayout(board_GroupLayout);

            board_GroupLayout.setAutoCreateGaps(true);
            board_GroupLayout.setAutoCreateContainerGaps(true);

            // Vertical: 10 row x 5 column
            GroupLayout.SequentialGroup b_vSequentialGroup = board_GroupLayout.createSequentialGroup();
            b_vSequentialGroup.addGap(15); //from revealPanel
            for (int i = 0; i <boardButtons.size(); i++) {
                GroupLayout.ParallelGroup v_pGroup = board_GroupLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING
                );
                for (int j = 0; j < boardButtons.get(i).size(); j++){
                    v_pGroup.addComponent(boardButtons.get(i).get(j), 55, 55,55);
                    v_pGroup.addGap(75);
                }
                // pins
                v_pGroup.addComponent(pinPanel_Group.get(i), 55, 55,55);
                b_vSequentialGroup.addGroup(v_pGroup);

            }
            board_GroupLayout.setVerticalGroup(b_vSequentialGroup);

            // Horizontal: 5 * 10
            GroupLayout.SequentialGroup b_hSequentialGroup = board_GroupLayout.createSequentialGroup();

            for (int i = 0; i < boardButtons.get(0).size(); i++){
                GroupLayout.ParallelGroup h_pGroup = board_GroupLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING
                );
                for (ArrayList<JButton> boardButton : boardButtons) {
                    h_pGroup.addComponent(boardButton.get(i), 100, 100, 100);
                    h_pGroup.addGap(50);
                }
                b_hSequentialGroup.addGroup(h_pGroup);
            }

            b_hSequentialGroup.addGap(12);
            GroupLayout.ParallelGroup h_pGroup = board_GroupLayout.createParallelGroup(
                    GroupLayout.Alignment.LEADING
            );

            for (JPanel panel : pinPanel_Group) {
                h_pGroup.addComponent(panel, 88, 88, 88);
                h_pGroup.addGap(50);
            }

            b_hSequentialGroup.addGroup(h_pGroup);

            board_GroupLayout.setHorizontalGroup(b_hSequentialGroup);
        }

        // launchPanel
        {
            launchPanel.setSize(appSize - appSize * (5 / 6), (appSize + appSize / 2) - appSize / 6);
            launchPanel.setMinimumSize(new Dimension(appSize - appSize * (5 / 6), (appSize + appSize / 2) - appSize / 6));
            launchPanel.setMaximumSize(new Dimension(appSize - appSize * (5 / 6), (appSize + appSize / 2) - appSize / 6));
            launchPanel.setBackground(new Color(0x5B892E));

            launchPanel.setLayout(new BoxLayout(launchPanel, BoxLayout.PAGE_AXIS));
            launchPanel.add(Box.createRigidArea(new Dimension(4,27)));
            for (int i = 0; i < launchButtons.size();i++){
                launchPanel.add(launchButtons.get(i));
                launchPanel.add(Box.createRigidArea(new Dimension(0,49)));
            }
        }

        // revealPanel
        {
            revealPanel.setSize(appSize, appSize/6);
            revealPanel.setBackground(new Color(0x566845));

            // GridBagLayout and adjusting the empty balls warning sign

            GridBagLayout revealGBL = new GridBagLayout();
            revealPanel.setLayout(revealGBL);
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 0.1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0,10, 0, 10);

            for (int i=0; i < b_COLUMN; i++) {
                JLabel targetLabel = new JLabel();
                targetLabel.setVisible(true);
                try {
                    ImageIcon image = new ImageIcon("assetPNG/blackball.png");
                    targetLabel.setIcon(image);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                targets.add(targetLabel);
                revealPanel.add(targetLabel, gbc);
                gbc.gridx++;
                gbc.insets = new Insets(0,10, 0, 10);

            }

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weighty = 0.25;
            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.anchor = GridBagConstraints.PAGE_END;
            gbc.gridwidth = 4;
            revealPanel.add(emptySign, gbc);
        }

        // final stuff
        {
            selectorPanel.setLocation(0, appSize + (appSize / 2) - appSize / 6);
            boardPanel.setLocation(0, appSize/6);
            launchPanel.setLocation(appSize - appSize / 6, appSize/6);
            revealPanel.setLocation(0,0);


            selectorPanel.setVisible(true);
            boardPanel.setVisible(true);
            launchPanel.setVisible(true);
            revealPanel.setVisible(true);
            emptySign.setVisible(false);

            MainFrame.add(selectorPanel);
            MainFrame.add(boardPanel);
            MainFrame.add(launchPanel);
            MainFrame.add(revealPanel);
            MainFrame.setTitle("Mastermind v2");

            MainFrame.pack();
        }
    }

    private static void initGame() {
        Random rand = new Random();

        // find a unique set of balls
        while (!isAllUnique){
            boolean hasFound = false;
            int someRandom = rand.nextInt(8);
            if (!hasFound) {
                try {
                    for (int i = 0; i < 4; i++) {
                        if (pass.get(i) == someRandom) {
                            break;
                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    hasFound = true;
                }
            } else {
                hasFound = true;
            }

            if (hasFound) {
                pass.add(someRandom);
            }

            if (pass.size()==4){
                isAllUnique = true;
            }
        }
        // System.out.println(pass);



    }

    private static void buttonRefactor(int row, int number, buttonType type){
        switch (type) {
            case BOARD_BUTTONS:
                try {
                    boardButtons.get(row).get(number).setIcon(new ImageIcon(iconPaths[chosenBall]));
                } catch (Exception e) {
                    boardButtons.get(row).get(number).setIcon(null);
                    System.out.println(e);
                }

                break;

            case SELECTOR_BUTTONS:
                if (selectorButtons.get(number).getIcon() == greyPNG) {
                    selectorButtons.get(number).setIcon(new ImageIcon(iconPaths[number]));
                    chosenBall = 9;
                } else {
                    // for setting previous Disabled button
                    try {
                        selectorButtons.get(previousDisabledButton).setIcon(new ImageIcon(iconPaths[previousDisabledButton]));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    // set new current Disabled Button
                    previousDisabledButton = number;
                    selectorButtons.get(number).setIcon(greyPNG);
                    chosenBall = number;
                }
                break;
            case LAUNCHER_BUTTONS:
                // add checker function
                for (int i = 0; i < boardButtons.get(row).size(); i++) {
                    JButton tempCurrentButton = boardButtons.get(row).get(i);
                    tempCurrentButton.setDisabledIcon(boardButtons.get(row).get(i).getIcon());
                    tempCurrentButton.setBorder(BorderFactory.createLineBorder(new Color(0x566845)));
                    tempCurrentButton.setBackground(new Color(0x566845));
                    tempCurrentButton.setEnabled(false);
                }
                {
                    JButton tempCurrentButton = launchButtons.get(row);
                    tempCurrentButton.setBorder(BorderFactory.createLineBorder(new Color(0x566845)));
                    tempCurrentButton.setBackground(new Color(0x566845));
                    tempCurrentButton.setEnabled(false);
                }
                try {
                    for (int i = 0; i < boardButtons.get(row - 1).size(); i++) {

                        JButton tempCurrentButton = boardButtons.get(row - 1).get(i);
                        tempCurrentButton.setBorder(BorderFactory.createLineBorder(new Color(0x91A47E)));
                        tempCurrentButton.setBackground(new Color(0x91A47E));
                        tempCurrentButton.setEnabled(true);
                    }
                    {
                        JButton tempCurrentButton = launchButtons.get(row - 1);
                        tempCurrentButton.setBorder(BorderFactory.createLineBorder(new Color(0x91A47E)));
                        tempCurrentButton.setBackground(new Color(0x91A47E));
                        tempCurrentButton.setEnabled(true);
                    }
                }
                catch(IndexOutOfBoundsException e) {}
                break;
        }
    }

    private static void updatePins(int correctAll, int correctBalls, int row){
        int redPins = correctAll;
        int whitePins = correctBalls - correctAll;
        ImageIcon redImage = new ImageIcon("assetPNG/pin_red.png");
        ImageIcon whiteImage = new ImageIcon("assetPNG/pin_white.png");
        for (int i = 0; i < b_COLUMN; i++) {
            // System.out.println(redPins + " " + whitePins);
            JLabel pin = (JLabel) pinPanel_Group.get(row).getComponent(i);
            if (redPins > 0) {
                pin.setIcon(redImage);
                redPins--;
            } else if(whitePins > 0 & redPins <= 0){
                pin.setIcon(whiteImage);
                whitePins--;
            }

        }
    }

    private static void updateFinalPins() {
        for (int i = 0; i < b_COLUMN; i++){
            JLabel pin = targets.get(i);
            ImageIcon realImage = new ImageIcon(iconPaths[pass.get(i)]);
            pin.setIcon(realImage);
            pin.setVisible(true);
        }
    }

    private static class buttonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < selectorButtons.size(); i++) {
                if (e.getSource()== selectorButtons.get(i)){
                    buttonRefactor(0, i, buttonType.SELECTOR_BUTTONS);
                }
            }

            for (int i = 0; i < boardButtons.size(); i++) {
                ArrayList currentGuessRow;
                currentGuessRow = boardGuesses.get(i);
                for (int j = 0; j < boardButtons.get(i).size(); j++) {
                    if (e.getSource() == boardButtons.get(i).get(j)){
                        currentGuessRow.set(j, chosenBall);
                        buttonRefactor(i, j, buttonType.BOARD_BUTTONS);
                        System.out.println(currentGuessRow + " at row: " + String.valueOf(i));
                    }
                }
            }

            for (int i = 0; i < launchButtons.size(); i++){
                if (e.getSource()==launchButtons.get(i)){
                    GameGuess pad = new GameGuess(pass,boardGuesses.get(i));
                    if (pad.getStatus()[0]) {
                        System.out.println("Interrupt");
                        emptySign.setVisible(true);
                        try {
                            new PlaySounds(2);
                        } catch (InterruptedException | IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        emptySign.setVisible(false);
                        updatePins(pad.getNumericalData()[1],pad.getNumericalData()[0], i);
                        buttonRefactor(i, 0, buttonType.LAUNCHER_BUTTONS);
                        if (i < launchButtons.size() + 1 & !pad.getStatus()[3]) {
                            try {
                                new PlaySounds(4);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (pad.getStatus()[3]){
                            try {
                                new WinPanel(appSize*3);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            updateFinalPins();
                        }

                        if (i == 0 & !pad.getStatus()[3]){
                            try {
                                new FailPanel(appSize*3);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            updateFinalPins();
                        }
                    }

                }
            }
        }
    }
}
