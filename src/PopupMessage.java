import javax.swing.JOptionPane;

/**
 * The type Popup message.
 */
public class PopupMessage {

    private static JOptionPane messageDialog;

    /**
     * Show message.
     *
     * @param message the message
     */
    public static void showMessage(String message) {
        messageDialog = new JOptionPane();
        messageDialog.showMessageDialog(null, message);
    }

//    public static void closeMessage() {
//        if (messageDialog != null) {
//            messageDialog.setVisible(false);
//        }
//    }

}
