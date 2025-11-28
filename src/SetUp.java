import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The type Set up.
 */
public class SetUp extends JFrame {
    /**
     * The Player names.
     */
    static ArrayList<String> playerNames = new ArrayList<>();
    private JLabel titleLabel;

    private JRadioButton twoPlayersButton;
    private JRadioButton threePlayersButton;
    private JRadioButton fourPlayersButton;
    /**
     * The constant playerCount.
     */
//    private JButton startButton;
    public static int playerCount;
//    public static String[] playerNames;

    /**
     * Instantiates a new Set up.
     */
    public SetUp() {
        super("Player Setup");


        titleLabel = new JLabel("Enter Player Information");
        titleLabel.setFont(new Font("Name", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);


        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // 创建单选按钮
        twoPlayersButton = new JRadioButton("2 Players");
        threePlayersButton = new JRadioButton("3 Players");
        fourPlayersButton = new JRadioButton("4 Players");

        // 创建按钮组
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(twoPlayersButton);
        buttonGroup.add(threePlayersButton);
        buttonGroup.add(fourPlayersButton);

        //按钮创建监听器
        twoPlayersButton.addActionListener(new ButtonListener());
        threePlayersButton.addActionListener(new ButtonListener());
        fourPlayersButton.addActionListener(new ButtonListener());

        // 添加单选按钮到面板
        panel.add(new JLabel("Number of Players:"));
        panel.add(twoPlayersButton);
        panel.add(threePlayersButton);
        panel.add(fourPlayersButton);

        // 添加面板到窗口
        add(panel, BorderLayout.CENTER);


//        startButton = new JButton("Start");
//        startButton.addActionListener(this);
//        JPanel startButtonPanel = new JPanel(new FlowLayout());
//        startButtonPanel.add(startButton);
//        add(startButtonPanel, BorderLayout.SOUTH);


        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * The type Button listener.
     */
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == twoPlayersButton) {
                playerCount = 2;
            } else if (e.getSource() == threePlayersButton) {
                playerCount = 3;
            } else if (e.getSource() == fourPlayersButton) {
                playerCount = 4;
            }

//            playerNames = new String[playerCount];
            for (int i = 0; i < playerCount; i++) {
                String name = JOptionPane.showInputDialog(null, "Enter name for player " + (i + 1) + ":");
                playerNames.add(name);
            }


            String message = "The following players have been set up:\n";
            for (int i = 0; i < playerCount; i++) {
                message += (i + 1) + ". " + playerNames.get(i) + "\n";
            }
            JOptionPane.showMessageDialog(null, message);
            startGame();

        }
    }

    private void startGame() {
        dispose();
        SwingUtilities.invokeLater(ChessBoardGUI::new);
    }

}

