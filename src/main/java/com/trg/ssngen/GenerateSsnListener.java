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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;

public class GenerateSsnListener implements ActionListener {
    private JTextField birthDateField;
    private JRadioButton genderFRadio;
    private JRadioButton generatePermanentSsn;
    private JTextField generatedSsnField;
    private SSNGenerator ssngen;
  
    private JTextField validateSsnField;
    private JLabel ssnValidityIcon;
    
    private JButton ssnButton;
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(GenerateSsnListener.class);
    
    public GenerateSsnListener(JTextField birthDateField, JRadioButton genderFRadio, JRadioButton generatePermanentSsn, 
    		JTextField generatedSsnField, JLabel ssnValidityIcon, JButton generateSsnButton) {
        this.birthDateField = birthDateField;
        this.genderFRadio = genderFRadio;
        this.ssnValidityIcon = ssnValidityIcon;
        this.generatePermanentSsn = generatePermanentSsn;
        this.generatedSsnField = generatedSsnField;
        this.ssnButton = generateSsnButton;
    } 
    
	public GenerateSsnListener(JTextField validateSsnField, JLabel ssnValidityIcon, JButton validateSsnButton) {
		this.validateSsnField = validateSsnField;
		this.ssnValidityIcon = ssnValidityIcon;
		this.ssnButton = validateSsnButton;
	}
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
    	ssngen = new SSNGenerator();
    	
    	if(ae.getActionCommand() == Actions.GENERATESSN.name()) {
        	String gender = "";
            boolean isPermanent = true;
            
            if(genderFRadio.isSelected()) {
               gender = "F";
            } else {
                gender = "M";
            }
            
            isPermanent = generatePermanentSsn.isSelected();
            logger.debug("Parameters for generating ssn: Permanent SSN: " + isPermanent + ", Date: " + birthDateField.getText() + ", gender: " + gender);
                   
        	String ssn = ssngen.generateSSN(isPermanent, convertString2Date(birthDateField.getText()), gender);

            generatedSsnField.setText(ssn);
            
            // Set validity icon as success if generated ssn is valid
            if(ssngen.isSSNValid(ssn)) {
                // Copy automagically to clipboard
                StringSelection stringSelection = new StringSelection(generatedSsnField.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
                generatedSsnField.setToolTipText("SSN has been automatically copied to clipboard");
                setValidityIconAsValid();
            } else {
                setValidityIconAsInvalid();
            }
    	}
    	
    	if (ae.getActionCommand() == Actions.VALIDATESSN.name()) {
    		logger.debug(validateSsnField.getText());
    		if(ssngen.isSSNValid(validateSsnField.getText())) {
    			setValidityIconAsValid();
    		} else {
    			setValidityIconAsInvalid();
    		}
    	}
    }

	private void setValidityIconAsInvalid() {
		ImageIcon icon = new ImageIcon(Main.class.getResource("/failure.png"));
		ssnValidityIcon.setIcon(icon);
		ssnValidityIcon.setToolTipText("SSN is invalid");
	}

	private void setValidityIconAsValid() {
		ImageIcon icon = new ImageIcon(Main.class.getResource("/success.png"));
		ssnValidityIcon.setIcon(icon);
		ssnValidityIcon.setToolTipText("SSN is valid");
	}
      
    private Date convertString2Date(String date) {
		Date convertedDate = null;
		          		  
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		
		try {
		    convertedDate = format.parse(date);
		} catch (ParseException ex) {
		    logger.error(ex);
		    return null;
		}
		return convertedDate;
    }
}
