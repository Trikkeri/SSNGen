package com.trg.ssngen;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Gui gui = new Gui();
        SwingUtilities.invokeLater(gui);
    }
}