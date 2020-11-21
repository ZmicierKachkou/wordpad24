package by.bsu.zmicier.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WordPadView extends JFrame {

    private JMenuItem newButton, open, save, saveAs, close;
    private JTextArea textArea;
    private JLabel currentFile;

    public WordPadView(String title) {
        super(title);

        this.setLayout(new BorderLayout());
        textArea = new JTextArea();
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);

        this.add(createStatusBar(), BorderLayout.SOUTH);

        this.setJMenuBar(createMenuBar());
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public JMenuItem getNewButton() {
        return newButton;
    }

    public JMenuItem getOpen() {
        return open;
    }

    public JMenuItem getSave() {
        return save;
    }

    public JMenuItem getSaveAs() {
        return saveAs;
    }

    public JMenuItem getClose() {
        return close;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getCurrentFile() {
        return currentFile;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        newButton = new JMenuItem("New");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save As");
        close = new JMenuItem("Close");
        file.add(newButton);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(close);
        menuBar.add(file);
        return menuBar;
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        currentFile = new JLabel("");
        statusBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        statusBar.add(currentFile);
        return statusBar;
    }
}
