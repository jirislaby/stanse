package cz.muni.stanse.gui;

import javax.swing.JTextArea;

final class ConsoleManager {

    // package-private section

    ConsoleManager(final javax.swing.JTextArea consoleTextArea) {
        this.consoleTextArea = consoleTextArea;
    }

    void clear() {
        getConsoleTextArea().setText("");
    }

    void appendText(final String text) {
        getConsoleTextArea().append(text);
        getConsoleTextArea().setCaretPosition(getConsoleTextArea().getDocument().getLength());
    }

    // private section

    private JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    private final javax.swing.JTextArea consoleTextArea;
}
