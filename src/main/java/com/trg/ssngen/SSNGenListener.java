package com.trg.ssngen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;

public class SSNGenListener implements ActionListener {
    private JFormattedTextField date;
    private JRadioButton genderF;
    private JRadioButton ssnPermanent;
    private JTextField generatedSSN;
    private SSNGenerator ssngen;
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SSNGenListener.class);
    
    public SSNGenListener(JFormattedTextField date, JRadioButton genderF, JRadioButton ssnPermanent, JTextField generatedSSN) {
        this.date = date;
        this.genderF = genderF;
        this.ssnPermanent = ssnPermanent;
        this.generatedSSN = generatedSSN;
        ssngen = new SSNGenerator();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        char gender = '0';
        boolean isPermanent = true;
        Date convertedDate = null;
                
        String str = this.date.getText();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            convertedDate = format.parse(str);
        } catch (ParseException ex) {
            logger.error(ex.toString());
        }
        
        if(genderF.isSelected()) {
           gender = 'F';
        } else {
            gender = 'M';
        }
        
        isPermanent = ssnPermanent.isSelected();
        logger.debug("Parameters for generating ssn: Permanent SSN: " + isPermanent + ", Date: " + convertedDate + ", gender: " + gender);
        generatedSSN.setText(ssngen.generateSSN(isPermanent, convertedDate, gender));
    }
    
}
