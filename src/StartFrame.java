import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The type Start frame.
 */
public class StartFrame extends JFrame {
    /**
     * Instantiates a new Start frame.
     */
    public StartFrame() {
        setTitle("Main Menu");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 创建面板
        JPanel panel = new JPanel();
        // 创建按钮
        JButton playGamesButton = new JButton("Play");
        playGamesButton.setFont(new Font("PLAY",Font.BOLD,20));
        playGamesButton.setBackground(Color.WHITE);
        // 加载背景图片
        ImageIcon backgroundIcon = new ImageIcon("scrabble.png");
        // 创建带有背景图片的标签
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(null);
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBounds(400,350,100,30);
        buttonPanel.setOpaque(false); // 使按钮面板透明，以便显示背景图片
        // 添加按钮到按钮面板
        buttonPanel.add(playGamesButton);
        // 将按钮面板添加到背景标签的南部（即底部）
        backgroundLabel.add(buttonPanel);
        // 添加背景标签到面板
        panel.add(backgroundLabel);
        // 添加面板到窗口
        add(panel);

        // Play Games按钮点击事件
        playGamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前界面

                // 创建新游戏界面
                GameSelectionFrame gameSelectionFrame = new GameSelectionFrame();
                gameSelectionFrame.setVisible(true);
            }
        });


    }
}


