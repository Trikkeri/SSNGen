package com.trg.ssngen;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;

public class SSNGenListener implements ActionListener {
    private JFormattedTextField date;
    private JRadioButton genderF;
    private JRadioButton ssnPermanent;
    private JTextField generatedSSN;
    private SSNGenerator ssngen;
    private JLabel jlblValidityIcon;
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SSNGenListener.class);
    
    public SSNGenListener(JFormattedTextField date, JRadioButton genderF, JRadioButton ssnPermanent, JTextField generatedSSN, JLabel jlblValidityIcon) {
        this.date = date;
        this.genderF = genderF;
        this.jlblValidityIcon = jlblValidityIcon;
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
        // Add error handling!
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
        
        String ssn = ssngen.generateSSN(isPermanent, convertedDate, gender);
        generatedSSN.setText(ssn);
        
        // Set validity icon as success if generated ssn is valid
        if(ssngen.isSSNValid(ssn)) {
        	
            // Copy automagically to clipboard
            StringSelection stringSelection = new StringSelection(generatedSSN.getText());
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);
            
            ImageIcon okIcon = new ImageIcon(Main.class.getResource("/success.png"));
            jlblValidityIcon.setIcon(okIcon);        	
        } else {
            ImageIcon okIcon = new ImageIcon(Main.class.getResource("/failure.png"));
            jlblValidityIcon.setIcon(okIcon);
        }
    }
    
}
