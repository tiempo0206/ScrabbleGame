import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Game selection frame.
 */
public class GameSelectionFrame extends JFrame {
    /**
     * Instantiates a new Game selection frame.
     */
    public GameSelectionFrame() {
        setTitle("Game Selection");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        // 创建单选按钮
        JRadioButton newGameRadioButton = new JRadioButton("New a Game");
        JRadioButton loadGameRadioButton = new JRadioButton("Load the Game");

        // 创建按钮组
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(newGameRadioButton);
        buttonGroup.add(loadGameRadioButton);

        // 添加单选按钮到面板
        panel.add(newGameRadioButton);
        panel.add(loadGameRadioButton);

        newGameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(SetUp::new);
            }
        });

        loadGameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Tools().load();
            }
        });



        // 添加面板到窗口
        add(panel);
    }
}