import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * The type Tools.
 */
public class Tools {
    /**
     * The constant Points.
     */
    public static final int[] Points = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    /**
     * The constant Letter.
     */
    public static final char[] Letter = {'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'D', 'D', 'E', 'E', 'E',
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'F', 'F', 'G', 'G', 'G', 'H', 'H', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'J', 'K',
            'L', 'L', 'L', 'L', 'M', 'M', 'N', 'N', 'N', 'N', 'N', 'N', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'P', 'P', 'Q', 'R', 'R', 'R', 'R',
            'R', 'R', 'S', 'S', 'S', 'S', 'T', 'T', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'V', 'V', 'W', 'W', 'X', 'Y', 'Y', 'Z'};
    private int num = 0;
    private String boardFileName = "scrabble_board.txt";
    private String playerFileName = "scrabble_player_tiles.txt";
    private String basicInfoFileName = "scrabble_basicInfo.txt";
    private int playerIndex = 0;

    /**
     * Tile generate string.
     *
     * @return the string
     */
    public String tileGenerate() {
        Random random = new Random();
        char randomLetter = Letter[random.nextInt(Letter.length)];
        return String.valueOf(randomLetter);
    }

    /**
     * Tile refresh.
     *
     * @param tile the tile
     */
    public void tileRefresh(String[] tile) {
        for (int i = 0; i < tile.length; i++) {
            if (tile[i] == null) {
                tile[i] = tileGenerate();
            }
            ChessBoardGUI.tiles[i].setText(tile[i]);
        }
    }

    /**
     * Score calculate int.
     *
     * @return the int
     */
    public int scoreCalculate() {
        int score = 0;
        for (int i = 0; i < Check.verWords.size(); i++) {
            for (int j = 0; j < Check.verWords.get(i).length(); j++) {
                score += Points[(int) (Check.verWords.get(i).charAt(j)) - 65];
            }
        }
        for (int i = 0; i < Check.horWords.size(); i++) {
            for (int j = 0; j < Check.horWords.get(i).length(); j++) {
                score += Points[(int) (Check.horWords.get(i).charAt(j)) - 65];
            }
        }

        return score;
    }

    /**
     * Round refresh.
     *
     * @param round the round
     */
//加分且补充手牌
    public void roundRefresh(int round) {
//        if (round % StartGUI.playerCount == 0) {
//            tileRefresh(ChessBoardGUI.players[StartGUI.playerCount - 1].ownedTiles);
//        } else if (round % StartGUI.playerCount == 1) {
//            tileRefresh(ChessBoardGUI.players[0].ownedTiles);
//        } else if (StartGUI.playerCount >= 3) {
//            if ((round & StartGUI.playerCount) == 2) {
//                tileRefresh(ChessBoardGUI.players[1].ownedTiles);
//            }
//        }

        switch (round % SetUp.playerCount) {
            case 0:
                playerIndex = SetUp.playerCount - 1;
//                System.out.println("现在" + ChessBoardGUI.tiles[0].getText());
                for (int i = 0; i < ChessBoardGUI.tiles.length; i++) {
                    ChessBoardGUI.players[playerIndex - 1].ownedTiles[i] = ChessBoardGUI.tiles[i].getText();
                }
                tileRefresh(ChessBoardGUI.players[playerIndex].ownedTiles);
                ChessBoardGUI.players[playerIndex - 1].setScore(Check.tempScore + ChessBoardGUI.players[playerIndex - 1].getScore());
                System.out.println((playerIndex) + ":  " + ChessBoardGUI.players[playerIndex - 1].getScore());
                ChessBoardGUI.labels[playerIndex - 1].setText("player" + (playerIndex) + ": "
                        + SetUp.playerNames.get(playerIndex - 1) + "--score:   " + ChessBoardGUI.players[playerIndex - 1].getScore());
//                ChessBoardGUI.labels[0].setForeground(Color.RED);
                break;
            case 1:
                playerIndex = 0;
                for (int i = 0; i < ChessBoardGUI.tiles.length; i++) {
                    ChessBoardGUI.players[SetUp.playerCount - 1].ownedTiles[i] = ChessBoardGUI.tiles[i].getText();
                }
                tileRefresh(ChessBoardGUI.players[0].ownedTiles);
                ChessBoardGUI.players[SetUp.playerCount - 1].setScore(Check.tempScore + ChessBoardGUI.players[SetUp.playerCount - 1].getScore());
                System.out.println((SetUp.playerCount) + ":  " + ChessBoardGUI.players[SetUp.playerCount - 1].getScore());
                ChessBoardGUI.labels[SetUp.playerCount - 1].setText("player" + (SetUp.playerCount) + ": "
                        + SetUp.playerNames.get(SetUp.playerCount - 1) + "--score:   " + ChessBoardGUI.players[SetUp.playerCount - 1].getScore());
//                ChessBoardGUI.labels[1].setForeground(Color.RED);
                break;
            case 2:
                playerIndex = 1;
                for (int i = 0; i < ChessBoardGUI.tiles.length; i++) {
                    ChessBoardGUI.players[0].ownedTiles[i] = ChessBoardGUI.tiles[i].getText();
                }
                tileRefresh(ChessBoardGUI.players[1].ownedTiles);
                ChessBoardGUI.players[0].setScore(Check.tempScore + ChessBoardGUI.players[0].getScore());
                System.out.println(1 + ":  " + ChessBoardGUI.players[0].getScore());
                ChessBoardGUI.labels[0].setText("player" + (1) + ": "
                        + SetUp.playerNames.get(0) + "--score:   " + ChessBoardGUI.players[0].getScore());
//                ChessBoardGUI.labels[SetUp.playerCount - 1].setForeground(Color.RED);
                break;
            case 3:
                playerIndex = 2;
                for (int i = 0; i < ChessBoardGUI.tiles.length; i++) {
                    ChessBoardGUI.players[1].ownedTiles[i] = ChessBoardGUI.tiles[i].getText();
                }
                tileRefresh(ChessBoardGUI.players[2].ownedTiles);
                ChessBoardGUI.players[1].setScore(Check.tempScore + ChessBoardGUI.players[1].getScore());
                System.out.println(2 + ":  " + ChessBoardGUI.players[1].getScore());
                ChessBoardGUI.labels[1].setText("player" + (2) + ": "
                        + SetUp.playerNames.get(1) + "--score:   " + ChessBoardGUI.players[1].getScore());
//                ChessBoardGUI.labels[SetUp.playerCount - 2].setForeground(Color.RED);
                break;
        }
        //刷新计时器
        ChessBoardGUI.startTimer();
    }

    /**
     * Cancel.
     */
    public void cancel() {
        //滞空棋盘上的字母 这些字母已被保存在letters Arraylist里了
        for (int i = 0; i < ChessBoardGUI.boardSize; i++) {
            for (int j = 0; j < ChessBoardGUI.boardSize; j++) {
                if (ChessBoardGUI.grids[i][j].getText() != "" && ChessBoardGUI.grids[i][j].isEnabled()) {
                    ChessBoardGUI.grids[i][j].setText("");
                }
            }
        }
        //将letters里的元素赋给tiles里为null的text
        num = 0;
        for (int i = 0; i < ChessBoardGUI.tiles.length; i++) {
            if (ChessBoardGUI.tiles[i].getText() == null) {
                ChessBoardGUI.tiles[i].setText(ChessBoardGUI.letters.get(num));
                num++;
            }
        }
        ChessBoardGUI.letters.clear();
    }

    /**
     * Skip.
     */
    public void skip() {
        cancel();
        ChessBoardGUI.round++;
        Check.tempScore = 0;
        roundRefresh(ChessBoardGUI.round);
    }

    /**
     * End.
     */
    public void end() {
        ChessBoardGUI.numEnd++;
        skip();
        if (ChessBoardGUI.numEnd >= 4) {
            System.exit(0);
        }
    }

    /**
     * Save.
     */
    public void save() {
        cancel();
        //保存基本信息
        try (FileWriter writer = new FileWriter(basicInfoFileName)) {
            //将玩家数量和回合数和玩家姓名写入文件
            writer.write(String.valueOf(SetUp.playerCount));
            writer.write('\n');
            writer.write(String.valueOf(ChessBoardGUI.round));
            writer.write('\n');
            for (int i = 0; i < SetUp.playerCount; i++) {
                writer.write(SetUp.playerNames.get(i));
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //保存玩家手牌和分数写入文件
        try (FileWriter writer = new FileWriter(playerFileName)) {

            for (int i = 0; i < SetUp.playerCount; i++) {
                writer.write(String.valueOf(ChessBoardGUI.players[i].getScore()));
                writer.write('\n');
            }
            for (int i = 0; i < SetUp.playerCount; i++) {
                for (int j = 0; j < 7; j++) {
                    if (ChessBoardGUI.players[i].ownedTiles[j] == null) {
                        writer.write("0");
                    } else {
                        writer.write(ChessBoardGUI.players[i].ownedTiles[j]);
                    }
                }
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //保存棋盘
        try (FileWriter writer = new FileWriter(boardFileName)) {
            // 将游戏板的状态写入文件
            for (int i = 0; i < ChessBoardGUI.boardSize; i++) {
                for (int j = 0; j < ChessBoardGUI.boardSize; j++) {
                    if (ChessBoardGUI.grids[i][j].getText() == "") {
                        writer.write("0");
                    } else {
                        writer.write(ChessBoardGUI.grids[i][j].getText());
                    }
                }
                writer.write('\n'); // 换行分隔每一行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PopupMessage.showMessage("Save successfully");
    }

    /**
     * Load.
     */
    public void load() {
        //加载基本信息 加载玩家名字
        try (BufferedReader reader = new BufferedReader(new FileReader(basicInfoFileName))) {
            SetUp.playerCount = Integer.parseInt(reader.readLine());
            ChessBoardGUI.round = Integer.parseInt(reader.readLine());
            for (int i = 0; i < SetUp.playerCount; i++) {
                SetUp.playerNames.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建棋盘
        ChessBoardGUI chessBoard = new ChessBoardGUI();
        SwingUtilities.invokeLater(() -> {

            //加载棋盘
            try (BufferedReader reader = new BufferedReader(new FileReader(boardFileName))) {
                String line;

                for (int row = 0; row < chessBoard.boardSize; row++) {
                    line = reader.readLine();
                    char[] chars = line.toCharArray();
                    for (int col = 0; col < chessBoard.boardSize; col++) {
                        if (chars[col] != '0') {
                            chessBoard.grids[row][col].setText(String.valueOf(chars[col]));
                            chessBoard.grids[row][col].setEnabled(false);
                        }
                    }
                }
                roundRefresh(ChessBoardGUI.round);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //加载手牌 (以及分数)
            try (BufferedReader reader = new BufferedReader(new FileReader(playerFileName))) {

                for (int i = 0; i < SetUp.playerCount; i++) {
                    ChessBoardGUI.players[i].setScore(Integer.parseInt(reader.readLine()));
                }

                for (int i = 0; i < ChessBoardGUI.labels.length; i++) {
                    ChessBoardGUI.labels[i].setText("player" + (i + 1) + ": "
                            + SetUp.playerNames.get(i) + "--score:   " + ChessBoardGUI.players[i].getScore());
                }

                String line;
                for (int row = 0; row < SetUp.playerCount; row++) {
                    line = reader.readLine();
                    char[] chars = line.toCharArray();
                    for (int col = 0; col < 7; col++) {
                        if (chars[col] == '0') {
                            ChessBoardGUI.players[row].ownedTiles[col] = null;
                        } else {
                            ChessBoardGUI.players[row].ownedTiles[col] = String.valueOf(chars[col]);
                        }
                    }
                }

                for (int i = 0; i < ChessBoardGUI.tiles.length; i++) {
                    ChessBoardGUI.tiles[i].setText(ChessBoardGUI.players[playerIndex].ownedTiles[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
