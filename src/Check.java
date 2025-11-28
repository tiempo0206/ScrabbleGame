import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Check.
 */
public class Check {

    /**
     * The Tool.
     */
    Tools tool = new Tools();
    /**
     * The constant tempScore.
     */
    public static int tempScore = 0;
    /**
     * The Ver words.
     */
    static ArrayList<String> verWords = new ArrayList<>();
    /**
     * The Hor words.
     */
    static ArrayList<String> horWords = new ArrayList<>();
    /**
     * The constant index.
     */
    public static int index = 0;
    /**
     * The Arr x.
     */
    ArrayList<Integer> arrX = new ArrayList<>();
    /**
     * The Arr y.
     */
    ArrayList<Integer> arrY = new ArrayList<>();

    /**
     * Is legal boolean.
     *
     * @return the boolean
     */
    public boolean isLegal() {
        //找到玩家放置的每一个字母的坐标
        findLetterCoord();

        if (!isFormatLegal() || !isLengthLegal() || !isCenter() || !isWordLegal()) {
            tool.cancel();
            clearArraylist();
            return false;
        }


        //把分数存到temp里用于在别的类中叠加(主要是趁这个时候arraylist还没清空)
        tempScore = tool.scoreCalculate();

        //输出竖直单词
        for (int i = 0; i < verWords.size(); i++) {
            System.out.println(verWords.get(i));
        }
        //输出水平单词
        for (int i = 0; i < horWords.size(); i++) {
            System.out.println(horWords.get(i));
        }

        clearArraylist();
        return true;
    }

    /**
     * Is first boolean.
     *
     * @return the boolean
     */
//检查是否为第一次
    public boolean isFirst() {
        for (int i = 0; i < ChessBoardGUI.boardSize; i++) {
            for (int j = 0; j < ChessBoardGUI.boardSize; j++) {
                if (!ChessBoardGUI.grids[i][j].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Is center boolean.
     *
     * @return the boolean
     */
//检查第一次是否在中间
    public boolean isCenter() {
        if (isFirst()) {
            for (int i = 0; i < index; i++) {
                if (arrX.get(i) == 7 && arrY.get(i) == 7) {
                    return true;
                }
            }
            PopupMessage.showMessage("The first word must cover the center of the board");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Is format legal boolean.
     *
     * @return the boolean
     */
//检查是否格式合法
    public boolean isFormatLegal() {
        //先判断是否是上下左右有字母相连
        if (!isConnected()) {
            PopupMessage.showMessage("Please connect your tiles to at least one letter already placed on the board");
            return false;
        }

        //判断是行还是列 来将单词纳入单词arraylist中 顺便检查是否都连在一起没有空挡
        if (isSameRow()) {
            for (int i = 0; i < index; i++) {
                if (ChessBoardGUI.grids[arrX.get(0)][arrY.get(0) + i].getText() != "") {
                    verWords.add(findVerWord(arrX.get(0), arrY.get(i)));
                    verWords.removeAll(Collections.singleton(null));
                } else {
                    PopupMessage.showMessage("Empty spaces between new words aren't allowed");
                    return false;
                }
            }
            horWords.add(findHorWord(arrX.get(0), arrY.get(0)));
        } else if (isSameCol()) {
            for (int i = 0; i < index; i++) {
                if (ChessBoardGUI.grids[arrX.get(0) + i][arrY.get(0)].getText() != "") {
                    horWords.add(findHorWord(arrX.get(i), arrY.get(0)));
                    horWords.removeAll(Collections.singleton(null));
                } else {
                    PopupMessage.showMessage("Empty spaces between new words aren't allowed");
                    return false;
                }
            }
            verWords.add(findVerWord(arrX.get(0), arrY.get(0)));
        } else {
            PopupMessage.showMessage("All new tiles must be placed in the same row or column");
            return false;
        }

        return true;
    }

    /**
     * Is connected boolean.
     *
     * @return the boolean
     */
//检查是否和棋盘上固定字符相邻
    public boolean isConnected() {
        if (isFirst()) {
            return true;
        }
        // Loop through all cells, including edges and corners
        for (int i = 0; i < ChessBoardGUI.boardSize; i++) {
            for (int j = 0; j < ChessBoardGUI.boardSize; j++) {
                if (ChessBoardGUI.grids[i][j].getText() != "" && ChessBoardGUI.grids[i][j].isEnabled()) {
                    if (numOfNeighbors(i, j) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //获得所有字符上下左右有固定的字符的数量
    private int numOfNeighbors(int x, int y) {
        int count = 0;
        // Directions to check: Down, Up, Right, Left
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int dx = x + dir[0], dy = y + dir[1];
            // Ensure we don't go out of the grid boundaries
            if (dx >= 0 && dx < ChessBoardGUI.boardSize && dy >= 0 && dy < ChessBoardGUI.boardSize) {
                if (!ChessBoardGUI.grids[dx][dy].isEnabled()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Is length legal boolean.
     *
     * @return the boolean
     */
//检查第一次放置时的单词长度是否大于1
    public boolean isLengthLegal() {
        if (isFirst()) {
            if (index > 1) {
                return true;
            } else {
                PopupMessage.showMessage("Please use more words to create a word");
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Is word legal boolean.
     *
     * @return the boolean
     */
//检查单词是否在字典内
    public boolean isWordLegal() {
        String filePath = "src/scrabbleDictionary.txt";
        Dictionary.loadDictionary(filePath);
        for (int i = 0; i < verWords.size(); i++) {
            if (verWords.get(i) != null && !Dictionary.isWordInDictionary(verWords.get(i))) {
                System.out.println(verWords.get(i) + " isn't in the dictionary.");
                PopupMessage.showMessage(verWords.get(i) + " isn't in the dictionary.");
                return false;
            }
        }

        for (int i = 0; i < horWords.size(); i++) {
            if (horWords.get(i) != null && !Dictionary.isWordInDictionary(horWords.get(i))) {
                System.out.println(horWords.get(i) + " isn't in the dictionary.");
                PopupMessage.showMessage(horWords.get(i) + " isn't in the dictionary.");
                return false;
            }
        }
        return true;
    }

    /**
     * Find letter coord.
     */
//找到玩家放置的每一个字母的坐标 并将xy坐标分别存入arrX和arrY arraylist中
    public void findLetterCoord() {
        index = 0;
        for (int i = 0; i < ChessBoardGUI.boardSize; i++) {
            for (int j = 0; j < ChessBoardGUI.boardSize; j++) {
                if (ChessBoardGUI.grids[i][j].getText() != "" && ChessBoardGUI.grids[i][j].isEnabled()) {
                    arrX.add(i);
                    arrY.add(j);
                    index++;
                }
            }
        }
    }

    /**
     * Find ver word string.
     *
     * @param px the px
     * @param py the py
     * @return the string
     */
//获取竖直单词
    public String findVerWord(int px, int py) {
        int verWordInitX = px;
        int verWordInitY = py;
        String verWord = "";
        //获取该字母竖直向上找的单词的首字母
        for (int i = px; i > 0; i--) {
            if (ChessBoardGUI.grids[i][py].getText() != "") {
                verWordInitX = i;
            } else {
                break;
            }
        }
        //根据首字母开始向下获得单词
        for (int i = verWordInitX; i < ChessBoardGUI.boardSize; i++) {
            if (ChessBoardGUI.grids[i][verWordInitY].getText() != "") {
                verWord += ChessBoardGUI.grids[i][verWordInitY].getText();
            } else {
                break;
            }
        }
        if (verWord.length() > 1) {
            return verWord;
        } else {
            return null;
        }
    }

    /**
     * Find hor word string.
     *
     * @param px the px
     * @param py the py
     * @return the string
     */
//获取水平单词
    public String findHorWord(int px, int py) {
        int horWordInitX = px;
        int horWordInitY = py;
        String horWord = "";
        //获取该字母水平向左找的单词的首字母
        for (int i = py; i > 0; i--) {
            if (ChessBoardGUI.grids[px][i].getText() != "") {
                horWordInitY = i;
            } else {
                break;
            }
        }
        //根据首字母开始向右获得单词
        for (int i = horWordInitY; i < ChessBoardGUI.boardSize; i++) {
            if (ChessBoardGUI.grids[horWordInitX][i].getText() != "") {
                horWord += ChessBoardGUI.grids[horWordInitX][i].getText();
            } else {
                break;
            }
        }
        if (horWord.length() > 1) {
            return horWord;
        } else {
            return null;
        }
    }

    /**
     * Is same row boolean.
     *
     * @return the boolean
     */
//判断是不是同一行
    public boolean isSameRow() {
        if (index == 1) {
            if (arrY.get(0) < ChessBoardGUI.boardSize - 1 && ChessBoardGUI.grids[arrX.get(0)][arrY.get(0) + 1].getText() != "") {
                return true;
            } else if (arrY.get(0) >= 1 && ChessBoardGUI.grids[arrX.get(0)][arrY.get(0) - 1].getText() != "") {
                return true;
            }
            return false;
        } else if (index > 1) {
            for (int i = 1; i < index; i++) {
                if (arrX.get(i) != arrX.get(0)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Is same col boolean.
     *
     * @return the boolean
     */
//判断是不是同一列
    public boolean isSameCol() {
        if (index == 1) {
            if (arrX.get(0) < ChessBoardGUI.boardSize - 1 && ChessBoardGUI.grids[arrX.get(0) + 1][arrY.get(0)].getText() != "") {
                return true;
            } else if (arrX.get(0) >= 1 && ChessBoardGUI.grids[arrX.get(0) - 1][arrY.get(0)].getText() != "") {
                return true;
            }
            return false;
        } else if (index > 1) {
            for (int i = 1; i < index; i++) {
                if (arrY.get(i) != arrY.get(0)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Clear arraylist.
     */
    public void clearArraylist() {
        verWords.clear();
        horWords.clear();
        arrX.clear();
        arrY.clear();
    }

}
