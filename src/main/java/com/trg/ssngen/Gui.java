package com.trg.ssngen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;
import javax.swing.text.PlainDocument;

public class Gui implements Runnable {

    private JFrame JFrame;
    private final String DATE_FORMAT = "dd.MM.yyyy";

    @Override
    public void run() {
        this.JFrame = new JFrame("SSN Generator");
        this.JFrame.setPreferredSize(new Dimension(360, 200));
        this.JFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(JFrame.getContentPane());
        
        this.JFrame.pack();
        this.JFrame.setVisible(true);
    }

    private void createComponents(Container container) {
    	JLabel jlblbirthDate;
        JTextField jTxtfieldBdate;
        JLabel jlblGender;
        JRadioButton jRadiobtnGenderF;
        JRadioButton jRadiobtnGenderM;
        JLabel jlblSsnmode;
        JRadioButton jRadiobtnPermSSN;
        JRadioButton jRadiobtnTempSSN;
        JButton jbtnGenerateSSN;
        JTextField jTxtfieldSSN;
        JLabel jlblValidtyIcon;

        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc  = new GridBagConstraints();
        
        ButtonGroup ssnGenderBGroup = new ButtonGroup();
        ButtonGroup ssnModeBGroup = new ButtonGroup();

        jlblbirthDate = new JLabel("Birthdate:");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(jlblbirthDate, gbc);
        
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        jTxtfieldBdate = new JFormattedTextField(df);
        jTxtfieldBdate.setInputVerifier(new DateVerifier());
        jTxtfieldBdate.setPreferredSize(new Dimension(80,20));
        jTxtfieldBdate.setText(setDefaultDate());
        // Limit input to 10 characters
        jTxtfieldBdate.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if(jTxtfieldBdate.getText().length() >= 10) 
        			e.consume();
        	}
        });
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        container.add(jTxtfieldBdate, gbc);  
        
        jlblGender = new JLabel("Gender:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        container.add(jlblGender, gbc);
                
        jRadiobtnGenderF = new JRadioButton("Female");
        jRadiobtnGenderF.setSelected(true);
        gbc.gridx = 1;
        gbc.gridy = 1;
        ssnGenderBGroup.add(jRadiobtnGenderF);
        container.add(jRadiobtnGenderF, gbc);
               
        jRadiobtnGenderM = new JRadioButton("Male"); 
        gbc.gridx = 1;
        gbc.gridy = 2;
        ssnGenderBGroup.add(jRadiobtnGenderM);
        container.add(jRadiobtnGenderM, gbc);
                
        jlblSsnmode = new JLabel("SSN type:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        container.add(jlblSsnmode,gbc);
        
        jRadiobtnPermSSN = new JRadioButton("Permanent SSN");
        jRadiobtnPermSSN.setSelected(true);
        gbc.gridx = 2;
        gbc.gridy = 1;
        ssnModeBGroup.add(jRadiobtnPermSSN);
        container.add(jRadiobtnPermSSN, gbc);
               
        jRadiobtnTempSSN = new JRadioButton("Temporary SSN");
        gbc.gridx = 2;
        gbc.gridy = 2;
        ssnModeBGroup.add(jRadiobtnTempSSN);
        container.add(jRadiobtnTempSSN, gbc);
        
        jbtnGenerateSSN = new JButton("Generate SSN");
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;    
        container.add(jbtnGenerateSSN, gbc);

        jTxtfieldSSN = new JTextField();
        jTxtfieldSSN.setEditable(false);
        jTxtfieldSSN.setPreferredSize(new Dimension(90,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        container.add(jTxtfieldSSN, gbc);

        jlblValidtyIcon = new JLabel();
        jlblValidtyIcon.setPreferredSize(new Dimension(25,25));
        gbc.anchor = GridBagConstraints.WEST;  
        gbc.gridx = 2;
        gbc.gridy = 4;
        container.add(jlblValidtyIcon, gbc);
        
        // ActionListener for generate button
        jbtnGenerateSSN.addActionListener(new SSNGenListener(jTxtfieldBdate, jRadiobtnGenderF, jRadiobtnPermSSN, jTxtfieldSSN, jlblValidtyIcon));
        
        // Add FocusLostListener to date field for date validation and save original border of birthdatetextfield for future use
		Border originalBorder = jTxtfieldBdate.getBorder();
        jTxtfieldBdate.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {
        	};
        	public void focusLost(FocusEvent e) {
        		String date = jTxtfieldBdate.getText();
        		jTxtfieldBdate.setText(date);
        		if(!e.isTemporary()) {
        			date = jTxtfieldBdate.getText();
        			if(!isDateValid(date)) {
        				jTxtfieldBdate.setToolTipText("Date must be provided in dd.MM.yyyy format");
        	            Border bdBorder = BorderFactory.createLineBorder(Color.RED, 2);
        	            jTxtfieldBdate.setBorder(bdBorder);
        	            jbtnGenerateSSN.setEnabled(false);
        			} else {
        				jbtnGenerateSSN.setEnabled(true);
        				jTxtfieldBdate.setBorder(originalBorder);
        				jTxtfieldBdate.setToolTipText(null);
        			}
        			
        		}
        	}
        	
        });
    }
    
    private String setDefaultDate() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, -18);
    	Date calToDate = cal.getTime();
    	return new SimpleDateFormat("dd.MM.yyyy").format(calToDate);
    }
    
    private boolean isDateValid(String date) {
    	try {
    		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    		df.setLenient(false);
    		df.parse(date);
    		return true;
    	} catch (ParseException e) {
    		return false;
    	}
    	
    }
}

class DateVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		JTextField dateField = (JTextField) input;
		String regex = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
		Border bdBorder = dateField.getBorder();
		
		if(!dateField.getText().matches(regex)) {
			dateField.setToolTipText("Date must be provided in dd.MM.yyyy format");
            bdBorder = BorderFactory.createLineBorder(Color.RED, 2);
            dateField.setBorder(bdBorder);
            return true;
		}
		
		dateField.setToolTipText(null);
		dateField.setBorder(bdBorder);
		return true;
	}
	
}
