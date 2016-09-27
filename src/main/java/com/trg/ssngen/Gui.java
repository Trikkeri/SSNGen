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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Gui implements Runnable {

    private JFrame JFrame;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    
    @Override
    public void run() {
        this.JFrame = new JFrame("SSN Generator");
        this.JFrame.setPreferredSize(new Dimension(360, 250));
        this.JFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createSSNGenetatorPanel(JFrame.getContentPane());
        createSSNValidationPanel(JFrame.getContentPane());
        
        this.JFrame.pack();
        this.JFrame.setVisible(true);
    }

	private void createSSNGenetatorPanel(Container container) {
		JPanel ssnGenPanel = new JPanel(new GridBagLayout());
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
        JLabel jlblValidityIcon;
        
        ssnGenPanel.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(), "Generate new SSN"));
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc  = new GridBagConstraints();
        
        ButtonGroup ssnGenderBGroup = new ButtonGroup();
        ButtonGroup ssnModeBGroup = new ButtonGroup();

        jlblbirthDate = new JLabel("Birthdate:");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        ssnGenPanel.add(jlblbirthDate, gbc);
        
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
        ssnGenPanel.add(jTxtfieldBdate, gbc);
        
        jlblGender = new JLabel("Gender:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        ssnGenPanel.add(jlblGender, gbc);
                
        jRadiobtnGenderF = new JRadioButton("Female");
        jRadiobtnGenderF.setSelected(true);
        gbc.gridx = 1;
        gbc.gridy = 1;
        ssnGenderBGroup.add(jRadiobtnGenderF);
        ssnGenPanel.add(jRadiobtnGenderF, gbc);
               
        jRadiobtnGenderM = new JRadioButton("Male"); 
        gbc.gridx = 1;
        gbc.gridy = 2;
        ssnGenderBGroup.add(jRadiobtnGenderM);
        ssnGenPanel.add(jRadiobtnGenderM, gbc);
                
        jlblSsnmode = new JLabel("SSN type:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        ssnGenPanel.add(jlblSsnmode, gbc);
        
        jRadiobtnPermSSN = new JRadioButton("Permanent SSN");
        jRadiobtnPermSSN.setSelected(true);
        gbc.gridx = 2;
        gbc.gridy = 1;
        ssnModeBGroup.add(jRadiobtnPermSSN);
        ssnGenPanel.add(jRadiobtnPermSSN, gbc);
               
        jRadiobtnTempSSN = new JRadioButton("Temporary SSN");
        gbc.gridx = 2;
        gbc.gridy = 2;
        ssnModeBGroup.add(jRadiobtnTempSSN);
        ssnGenPanel.add(jRadiobtnTempSSN, gbc);
        
        jbtnGenerateSSN = new JButton("Generate SSN");
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;    
        ssnGenPanel.add(jbtnGenerateSSN, gbc);

        jTxtfieldSSN = new JTextField();
        jTxtfieldSSN.setEditable(false);
        jTxtfieldSSN.setPreferredSize(new Dimension(90,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        ssnGenPanel.add(jTxtfieldSSN, gbc);

        jlblValidityIcon = new JLabel();
        jlblValidityIcon.setPreferredSize(new Dimension(25,25));
        gbc.anchor = GridBagConstraints.WEST;  
        gbc.gridx = 2;
        gbc.gridy = 4;
        ssnGenPanel.add(jlblValidityIcon, gbc);
        
        container.add(ssnGenPanel);
              
        // ActionListener for generate button
        jbtnGenerateSSN.addActionListener(new SSNGenListener(jTxtfieldBdate, jRadiobtnGenderF, jRadiobtnPermSSN, jTxtfieldSSN, jlblValidityIcon));
        
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
        				jTxtfieldBdate.setToolTipText("Date must be within valid date between 1.1.1900 - 31.12.2099 and provided in dd.MM.yyyy format");
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
	
    private void createSSNValidationPanel(Container container) {  
    	JPanel jPanelSSNValidation = new JPanel(new GridBagLayout());
        JTextField jTxtfieldSSNValidationField;
        JLabel jlblValidtyIcon2;
        GridBagConstraints gbc  = new GridBagConstraints();
        
        jTxtfieldSSNValidationField = new JTextField();
        jTxtfieldSSNValidationField.setEditable(false);
        jTxtfieldSSNValidationField.setPreferredSize(new Dimension(90,20));
        gbc.gridx = 1;
        gbc.gridy = 6;
        jPanelSSNValidation.add(jTxtfieldSSNValidationField, gbc);

        jlblValidtyIcon2 = new JLabel();
        jlblValidtyIcon2.setPreferredSize(new Dimension(25,25));
        gbc.anchor = GridBagConstraints.WEST;  
        gbc.gridx = 2;
        gbc.gridy = 6;
        jPanelSSNValidation.add(jlblValidtyIcon2, gbc);
        
        container.add(jPanelSSNValidation, gbc);
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
    		Date d = df.parse(date);

    		// Is date between 1900 and 2099
            return isDateWithinRange(d);
    		
    	} catch (ParseException e) {
    		return false;
    	}
    	
    }
    
    private boolean isDateWithinRange(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(cal.get(Calendar.YEAR) >= 1900 && cal.get(Calendar.YEAR) <= 2099) {
        	return true;
        }
        return false;
    }
    
}

class DateVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		JTextField dateField = (JTextField) input;
		String regex = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
		Border bdBorder = dateField.getBorder();
		
		if(!dateField.getText().matches(regex)) {
			dateField.setToolTipText("Date must be within valid date between 1.1.1900 - 31.12.2099 and provided in dd.MM.yyyy format");
            bdBorder = BorderFactory.createLineBorder(Color.RED, 2);
            dateField.setBorder(bdBorder);
            return true;
		}
		
		dateField.setToolTipText(null);
		dateField.setBorder(bdBorder);
		return true;
	}
	
}
