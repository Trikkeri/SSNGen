package com.trg.ssngen;

import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        Gui gui = new Gui();
        SwingUtilities.invokeLater(gui);
        
        SSNGenerator test = new SSNGenerator();
                
        System.out.println(test.generateLastNumbersAndCheckmark("010101-", true, 'M'));
    }
}
