import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer extends JFrame {
    private Clip bgm;
    private boolean isPlaying = false;

    public SoundPlayer() {
        setTitle("Sound Player");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPlaying) {
                    playSound();
                    playButton.setText("Stop");
                } else {
                    stopSound();
                    playButton.setText("Play");
                }
                isPlaying = !isPlaying;
            }
        });

        add(playButton);
        setVisible(true);
    }

    private void playSound() {
        try {
            bgm = AudioSystem.getClip();
            InputStream is = SoundPlayer.class.getClassLoader().getResourceAsStream("resource/bgm.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            bgm.open(ais);
            bgm.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private void stopSound() {
        if (bgm != null && bgm.isRunning()) {
            bgm.stop();
        }
    }

    public static void main(String[] args) {
        new SoundPlayer();
    }
}
