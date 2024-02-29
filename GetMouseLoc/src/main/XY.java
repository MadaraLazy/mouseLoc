package main;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class XY implements KeyListener {

    public static int x;
    public static int y;

    public static void main(String[] args) throws AWTException, InterruptedException {
        XY xy = new XY();
        xy.startTracking();
    }

    private void startTracking() throws AWTException, InterruptedException {
        Robot robot = new Robot();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JLabel label;

        label = new JLabel("\tX=" + x + "," + "Y=" + y);
        label.setLocation(0, 0);
        JWindow window = new JWindow();
        window.addKeyListener(this);
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                window.requestFocusInWindow();
            }
        });

        while (true) {
            window.setSize(100, 50);
            window.add(label);
            window.setVisible(true);
            Thread.sleep(1);
            window.setEnabled(true);
            x = (int) MouseInfo.getPointerInfo().getLocation().getX();
            y = (int) MouseInfo.getPointerInfo().getLocation().getY();
            label.setText("   X=" + x + "," + "Y=" + y);

            if (x > (screenSize.width - 116)) {
                window.setLocation((x + 10) - 120, (y + 10));
            } else {
                window.setLocation(x + 10, y + 10);
            }

            window.setEnabled(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'x' || e.getKeyChar() == 'X') {
            copyToClipboard("X: " + x + ", Y: " + y);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
