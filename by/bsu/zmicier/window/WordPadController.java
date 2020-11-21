package by.bsu.zmicier.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class WordPadController {
    private final WordPadView view;
    private final WordPadModel model;

    public WordPadController(WordPadView view, WordPadModel model) {
        this.view = view;
        this.model = model;

        view.getNewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkUnsavedChanges()) {
                    model.setText("");
                    model.setFile(null);
                    model.setModified(false);
                    updateView();
                }
            }
        });

        view.getOpen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkUnsavedChanges()) {
                    JFileChooser chooser = new JFileChooser();
                    int result = chooser.showOpenDialog(view);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File chosenFile = chooser.getSelectedFile();
                        try {
                            model.setText(Files.readString(Paths.get(chosenFile.toURI())));
                            model.setFile(chosenFile);
                            model.setModified(false);
                            updateView();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(view, "Sorry! Cannot read file!");
                        }
                    }
                }
            }
        });

        view.getSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAction();
            }
        });

        view.getSaveAs().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsAction();
            }
        });

        view.getClose().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkUnsavedChanges()) {
                    System.exit(0);
                }
            }
        });

        view.getTextArea().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                    model.setModified(true);
                }
            }
        });

        updateView();
    }

    private void updateModel() {
        model.setText(view.getTextArea().getText());
    }

    private void updateView() {
        view.getTextArea().setText(model.getText());
        File currentFile = model.getFile();
        view.getCurrentFile().setText(currentFile == null ? "----" : currentFile.getPath());
    }

    private boolean saveAction() {
        if (model.getFile() != null) {
            saveToFile(model.getFile());
            return true;
        } else {
            return saveAsAction();
        }
    }

    private boolean saveAsAction() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File chosenFile = chooser.getSelectedFile();
            saveToFile(chosenFile);
            return true;
        }
        return false;
    }

    private void saveToFile(File chosenFile) {
        updateModel();
        try {
            Files.writeString(Paths.get(chosenFile.toURI()), model.getText());
            model.setFile(chosenFile);
            model.setModified(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Sorry! Cannot write file!");
        }
        updateView();
    }

    private boolean checkUnsavedChanges() {
        if (model.isModified()) {
            int result = JOptionPane.showConfirmDialog(view, "You have unsaved changes! Do you want to save them?");
            if (result == JOptionPane.YES_OPTION) {
                return saveAction();
            } else if (result == JOptionPane.NO_OPTION){
                return true;
            }
            return false;
        }
        return true;
    }
}
