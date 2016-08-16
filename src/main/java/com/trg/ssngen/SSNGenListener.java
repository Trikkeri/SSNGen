package com.trg.ssngen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SSNGenListener implements ActionListener {

    private JFormattedTextField date;
    private JRadioButton genderF;
    private JRadioButton ssnPermanent;
    private JTextField generatedSSN;
    
    public SSNGenListener(JFormattedTextField date, JRadioButton genderF, JRadioButton ssnPermanent, JTextField generatedSSN) {
        this.date = date;
        this.genderF = genderF;
        this.ssnPermanent = ssnPermanent;
        this.generatedSSN = generatedSSN;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        SSNGenerator ssngen = new SSNGenerator();
        
        char gender;
        boolean isPermanent;
        Date date;
        
        
        
        if(genderF.isSelected()) {
           gender = 'F';
        } else {
            gender = 'M';
        }
        
        isPermanent = ssnPermanent.isSelected();
        
        generatedSSN.setText(ssngen.generateSSN(isPermanent, date, gender));
    }
    
}
