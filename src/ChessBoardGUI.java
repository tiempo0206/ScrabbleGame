import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The type Chess board gui.
 */
public class ChessBoardGUI extends JFrame {
    /**
     * The constant boardSize.
     */
    public static final int boardSize = 15;
    /**
     * The constant seconds.
     */
    public static int seconds = 60;
    /**
     * The constant isPaused.
     */
    public static boolean isPaused = false;
    /**
     * The Grids.
     */
    public static JButton[][] grids;
    /**
     * The Tiles.
     */
    public static JButton[] tiles;
    private JButton[] btns;
    private String letterToMove;
    private JButton tileToMove;
    /**
     * The constant timerLabel.
     */
    public static JLabel timerLabel;
    /**
     * The Rule label.
     */
    public JLabel ruleLabel;
    private JButton endButton;
    private static Timer timer;

    /**
     * The constant players.
     */
    public static Player[] players = new Player[SetUp.playerCount];
    /**
     * The constant labels.
     */
    public static JLabel[] labels = new JLabel[SetUp.playerCount];

    private Tools tool = new Tools();
    /**
     * The constant round.
     */
    public static int round = 1;
    /**
     * The constant numEnd.
     */
    public static int numEnd = 0;
    /**
     * The Letters.
     */
    static ArrayList<String> letters = new ArrayList<>();


    /**
     * Create player.
     */
//初始化界面
    public static void createPlayer() {
        for (int i = 0; i < SetUp.playerCount; i++) {
            players[i] = new Player();
        }
    }

    /**
     * Instantiates a new Chess board gui.
     */
    public ChessBoardGUI() {

        setTitle("Chess Board");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);


        createPlayer();
        // 创建一个面板，用于放置棋盘和按钮
        JPanel mainPanel = new JPanel(null);

        // 创建棋盘面板
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        boardPanel.setBounds(250, 10, 500, 500);
        grids = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grids[i][j] = new JButton();
                boardPanel.add(grids[i][j]);
                grids[i][j].setBackground(Color.WHITE);
                grids[i][j].addActionListener(new BasicClickListener());
                grids[i][j].setMargin(new Insets(0, 0, 0, 0));
                grids[i][j].setOpaque(true);
            }
        }

        //创建时钟面板
        JPanel timerPanel = new JPanel(new BorderLayout());
        timerPanel.setBounds(50, 50, 120, 50);
        timerLabel = new JLabel(formatTime(seconds), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Timer", Font.BOLD, 50));
        timerPanel.add(timerLabel, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds--;
                if (seconds >= 0) {
                    timerLabel.setText(formatTime(seconds));
                } else {
                    timer.stop();
                    isPaused = true;
//                    timerLabel.setForeground(Color.RED);
                    tool.skip();
                    PopupMessage.showMessage("Time is up !!!");
                }
            }
        });
        startTimer();

        // 创建按钮面板并添加四个按钮以及文本框
        JPanel playerPanel = new JPanel(new GridLayout(SetUp.playerCount, 1));
        playerPanel.setBounds(20, 120, 210, 150);
        for (int i = 0; i < SetUp.playerCount; i++) {
            labels[i] = new JLabel("player" + (i + 1) + ": " + SetUp.playerNames.get(i));
            labels[i].setFont(new Font("player" + (i + 1) + ": " + SetUp.playerNames.get(i), Font.BOLD, 15));
            playerPanel.add(labels[i]);
        }


        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.setBounds(45, 290, 160, 80);
        btns = new JButton[4];
        for (int i = 0; i < 4; i++) {
            btns[i] = new JButton();
            buttonPanel.add(btns[i]);
            btns[i].setBackground(Color.white);
            btns[i].addActionListener(new BasicClickListener());
        }
        btns[0].setText("skip");
        btns[1].setText("confirm");
        btns[2].setText("cancel");
        btns[3].setText("save");


        JPanel tilePanel = new JPanel(new GridLayout(1, 7));
        tilePanel.setBounds(325, 520, 350, 60);
        tiles = new JButton[7];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new JButton(tool.tileGenerate());
//            tiles[i].setFont(new Font(tool.tileGenerate(),Font.BOLD,15));
            players[0].ownedTiles[i] = tiles[i].getText();
            tilePanel.add(tiles[i]);
            tiles[i].setBackground(new Color(255, 205, 98));
            tiles[i].addActionListener(new BasicClickListener());
        }

        JPanel endPanel = new JPanel(new BorderLayout());
        endPanel.setBounds(85, 370, 80, 40);
        endButton = new JButton("end");
        endButton.setBackground(Color.WHITE);
        endButton.addActionListener(new BasicClickListener());
        endPanel.add(endButton, BorderLayout.CENTER);

        JPanel rulePanel = new JPanel();
        rulePanel.setBounds(20, 420, 210, 130);
        ruleLabel = new JLabel("<html>1:   A, E, I, O, U, L, N, R, S, T<br>" +
                "2:   D, G<br>3:   B, C, M, P<br>4:   F, H, V, W, Y<br>5:   K<br>8:   J, X<br>10:   Q, Z");
        rulePanel.add(ruleLabel);


        // 设置颜色
        // 设置特殊点的颜色
        //triple word
        for (int a = 0; a < 15; a = a + 7) {
            for (int b = 0; b < 15; b = b + 7) {
                grids[a][b].setBackground(Color.decode("#F09B59"));
            }
        }

        //double word
        for (int a = 1; a < 5; a++) {
            grids[a][a].setBackground(Color.decode("#F09B59"));
        }
        for (int a = 10; a < 14; a++) {
            grids[a][a].setBackground(Color.decode("#F09B59"));
        }
        for (int a = 10; a < 14; a++) {
            int b = 14 - a;
            grids[a][b].setBackground(Color.decode("#F09B59"));

        }
        for (int b = 10; b < 14; b++) {
            int a = 14 - b;
            grids[a][b].setBackground(Color.decode("#F09B59"));

        }
        grids[7][7].setBackground(Color.decode("#F09B59"));
        //triple letter
        for (int a = 1; a < 14; a = a + 4) {
            for (int b = 5; b < 10; b = b + 4) {
                grids[a][b].setBackground(Color.decode("#1E86B9"));
            }
        }
        for (int a = 5; a < 10; a = a + 4) {
            for (int b = 1; b < 14; b = b + 12) {
                grids[a][b].setBackground(Color.decode("#1E86B9"));
            }
        }
        Color color = Color.decode("#7AB131");
        //double letter
        grids[0][3].setBackground(color);
        grids[0][11].setBackground(color);
        grids[2][6].setBackground(color);
        grids[2][8].setBackground(color);
        grids[3][0].setBackground(color);
        grids[3][7].setBackground(color);
        grids[3][14].setBackground(color);
        grids[6][2].setBackground(color);
        grids[6][6].setBackground(color);
        grids[6][8].setBackground(color);
        grids[6][12].setBackground(color);
        grids[7][3].setBackground(color);
        grids[7][11].setBackground(color);
        grids[8][2].setBackground(color);
        grids[8][6].setBackground(color);
        grids[8][8].setBackground(color);
        grids[8][12].setBackground(color);
        grids[11][0].setBackground(color);
        grids[11][7].setBackground(color);
        grids[11][14].setBackground(color);
        grids[12][6].setBackground(color);
        grids[12][8].setBackground(color);
        grids[14][3].setBackground(color);
        grids[14][11].setBackground(color);


        // 将棋盘面板和按钮面板添加到主面板中
        mainPanel.add(boardPanel);
        mainPanel.add(timerPanel);
        mainPanel.add(endPanel);
        mainPanel.add(playerPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(rulePanel);
        mainPanel.add(tilePanel);
        boardPanel.setBackground(Color.decode("#F5EBD7"));
        timerPanel.setBackground(Color.decode("#F5EBD7"));
        rulePanel.setBackground(Color.decode("#F5EBD7"));
        playerPanel.setBackground(Color.decode("#F5EBD7"));
        tilePanel.setBackground(Color.decode("#F5EBD7"));

        // 将主面板添加到窗口中
        add(mainPanel);
        mainPanel.setBackground(Color.decode("#F5EBD7"));
    }

    //监听器(流程)
    private class BasicClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (isTilesButton(clickedButton)) {
                letterToMove = clickedButton.getText();
                tileToMove = clickedButton;
            } else if (isGridsButton(clickedButton) && letterToMove != null) {
                if (clickedButton.getText() == "") {
                    clickedButton.setText(letterToMove);
                    letters.add(letterToMove);
                    letterToMove = null;
                    tileToMove.setText(null);
                } else {
                    tool.cancel();
                    PopupMessage.showMessage("Please put the tile to blank board");
                }
            } else if (isConfirmButton(clickedButton)) {
                if (new Check().isLegal()) {
                    //回合加一
                    //先固定上一名玩家选择并存入刚创建的链表(在Check里)
                    //再根据round 替换player
                    //再将上一名玩家分数统计好
                    //再将下一名玩家手牌刷新并显示出来
                    round++;
                    for (int i = 0; i < boardSize; i++) {
                        for (int j = 0; j < boardSize; j++) {
                            if (grids[i][j].getText() != "") {
                                grids[i][j].setEnabled(false);
                            }
                        }
                    }
                    tool.roundRefresh(round);
                    letters.clear();
                }
            } else if (isSkipButton(clickedButton)) {
                tool.skip();
            } else if (isCancelButton(clickedButton)) {
                tool.cancel();
            } else if (isSaveButton(clickedButton)) {
                tool.save();
            } else if (isEndButton(clickedButton)) {
                tool.end();
            }
            for (int i = 0; i < SetUp.playerCount; i++) {
                if (players[i].getScore() == 60) {
                    PopupMessage.showMessage("player" + (i + 1) + " wins!!!");
                    System.exit(0);
                }
            }
        }
    }

    private boolean isTilesButton(JButton clickedButton) {
        for (int i = 0; i < tiles.length; i++) {
            if (clickedButton == tiles[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean isGridsButton(JButton clickedButton) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (clickedButton == grids[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSkipButton(JButton clickedButton) {
        if (clickedButton == btns[0]) {
            return true;
        }
        return false;
    }

    private boolean isConfirmButton(JButton clickedButton) {
        if (clickedButton == btns[1]) {
            return true;
        }
        return false;
    }

    private boolean isCancelButton(JButton clickedButton) {
        if (clickedButton == btns[2]) {
            return true;
        }
        return false;
    }

    private boolean isSaveButton(JButton clickedButton) {
        if (clickedButton == btns[3]) {
            return true;
        }
        return false;
    }

    private boolean isEndButton(JButton clickedButton) {
        if (clickedButton == endButton) {
            return true;
        }
        return false;
    }

    /**
     * Start timer.
     */
    public static void startTimer() {
        isPaused = false;
        seconds = 60;
        timerLabel.setText(formatTime(seconds));
        timer.start();
        timerLabel.setForeground(Color.BLACK);
    }

    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }

}
