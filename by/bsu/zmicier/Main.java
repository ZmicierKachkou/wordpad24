package by.bsu.zmicier;

import by.bsu.zmicier.window.WordPadController;
import by.bsu.zmicier.window.WordPadModel;
import by.bsu.zmicier.window.WordPadView;

public class Main {

    public static void main(String[] args) {
        new WordPadController(
            new WordPadView("Super Text Editor"),
            new WordPadModel()
        );
    }
}
