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
import com.trg.ssngen.GenerateSsnListener;

public class Gui implements Runnable {

    private JFrame JFrame;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    
    @Override
    public void run() {
        this.JFrame = new JFrame("SSN Generator");
        this.JFrame.setPreferredSize(new Dimension(320, 280));
        this.JFrame.setResizable(false);
        this.JFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createSSNGenetatorPanel(JFrame.getContentPane());
        createSSNValidationPanel(JFrame.getContentPane());
        
        this.JFrame.pack();
        this.JFrame.setVisible(true);
    }

	private void createSSNGenetatorPanel(Container container) {
		JPanel ssnGenPanel = new JPanel(new GridBagLayout());
    	JLabel birthDateLabel;
        JTextField birthDateField;
        JLabel genderLabel;
        JRadioButton genderFRadio;
        JRadioButton genderMRadio;
        JLabel ssnTypeLabel;
        JRadioButton generatePermanentSsnRadio;
        JRadioButton genenerateTemporarySsnRadi;
        JButton generateSsnButton;
        JTextField generatedSsnField;
        JLabel ssnValidityIcon;
        
        ssnGenPanel.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(), "Generate new SSN"));
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc  = new GridBagConstraints();
        
        ButtonGroup ssnGenderBGroup = new ButtonGroup();
        ButtonGroup ssnModeBGroup = new ButtonGroup();

        birthDateLabel = new JLabel("Birthdate:");
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        ssnGenPanel.add(birthDateLabel, gbc);
        
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        birthDateField = new JFormattedTextField(df);
        birthDateField.setInputVerifier(new DateVerifier());
        birthDateField.setPreferredSize(new Dimension(80,20));
        birthDateField.setText(setDefaultDate());
        // Limit input to 10 characters
        birthDateField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if(birthDateField.getText().length() >= 10) 
        			e.consume();
        	}
        });
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        ssnGenPanel.add(birthDateField, gbc);
        
        genderLabel = new JLabel("Gender:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        ssnGenPanel.add(genderLabel, gbc);
                
        genderFRadio = new JRadioButton("Female");
        genderFRadio.setSelected(true);
        gbc.gridx = 1;
        gbc.gridy = 1;
        ssnGenderBGroup.add(genderFRadio);
        ssnGenPanel.add(genderFRadio, gbc);
               
        genderMRadio = new JRadioButton("Male"); 
        gbc.gridx = 1;
        gbc.gridy = 2;
        ssnGenderBGroup.add(genderMRadio);
        ssnGenPanel.add(genderMRadio, gbc);
                
        ssnTypeLabel = new JLabel("SSN type:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        ssnGenPanel.add(ssnTypeLabel, gbc);
        
        generatePermanentSsnRadio = new JRadioButton("Permanent SSN");
        generatePermanentSsnRadio.setSelected(true);
        gbc.gridx = 2;
        gbc.gridy = 1;
        ssnModeBGroup.add(generatePermanentSsnRadio);
        ssnGenPanel.add(generatePermanentSsnRadio, gbc);
               
        genenerateTemporarySsnRadi = new JRadioButton("Temporary SSN");
        gbc.gridx = 2;
        gbc.gridy = 2;
        ssnModeBGroup.add(genenerateTemporarySsnRadi);
        ssnGenPanel.add(genenerateTemporarySsnRadi, gbc);
        
        generateSsnButton = new JButton("Generate SSN");
        generateSsnButton.setActionCommand(Actions.GENERATESSN.name());
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;    
        ssnGenPanel.add(generateSsnButton, gbc);

        generatedSsnField = new JTextField();
        generatedSsnField.setEditable(false);
        generatedSsnField.setPreferredSize(new Dimension(90,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        ssnGenPanel.add(generatedSsnField, gbc);

        ssnValidityIcon = new JLabel();
        ssnValidityIcon.setPreferredSize(new Dimension(25,25));
        gbc.anchor = GridBagConstraints.WEST;  
        gbc.gridx = 2;
        gbc.gridy = 4;
        ssnGenPanel.add(ssnValidityIcon, gbc);
        
        container.add(ssnGenPanel);
              
        // ActionListener for generate button
        generateSsnButton.addActionListener(new GenerateSsnListener(birthDateField, genderFRadio, generatePermanentSsnRadio, generatedSsnField, ssnValidityIcon, generateSsnButton));
        
        // Add FocusLostListener to date field for date validation and save original border of birthDateField for future use
		Border originalBorder = birthDateField.getBorder();
        birthDateField.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {
        	};
        	public void focusLost(FocusEvent e) {
        		String date = birthDateField.getText();
        		birthDateField.setText(date);
        		if(!e.isTemporary()) {
        			date = birthDateField.getText();
        			if(!isDateValid(date)) {
        				birthDateField.setToolTipText("Date must be within valid date between 1.1.1900 - 31.12.2099 and provided in dd.MM.yyyy format");
        	            Border bdBorder = BorderFactory.createLineBorder(Color.RED, 2);
        	            birthDateField.setBorder(bdBorder);
        	            generateSsnButton.setEnabled(false);
        			} else {
        				generateSsnButton.setEnabled(true);
        				birthDateField.setBorder(originalBorder);
        				birthDateField.setToolTipText(null);
        			}
        			
        		}
        	}
        	
        });
    }
	
    private void createSSNValidationPanel(Container container) {  
    	JPanel ssnValidationPanel = new JPanel(new GridBagLayout());
        JTextField validateSsnField;
        JLabel ssnValidityIcon;
        JButton validateSsnButton;
        GridBagConstraints gbc  = new GridBagConstraints();
        ssnValidationPanel.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(), "Validate SSN"));
        container.setLayout(new GridBagLayout());
        //ssnValidationPanel.setPreferredSize(new Dimension(400, 300));
        
        validateSsnField = new JTextField();
        validateSsnField.setEditable(true);
        validateSsnField.setPreferredSize(new Dimension(90,20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        ssnValidationPanel.add(validateSsnField, gbc);

        ssnValidityIcon = new JLabel();
        ssnValidityIcon.setPreferredSize(new Dimension(25,25));
        gbc.anchor = GridBagConstraints.WEST;  
        gbc.gridx = 1;
        gbc.gridy = 1;
        ssnValidationPanel.add(ssnValidityIcon, gbc);
        
        validateSsnButton = new JButton("Validate SSN");
        validateSsnButton.setActionCommand(Actions.VALIDATESSN.name());
        gbc.anchor = GridBagConstraints.CENTER;  
        gbc.gridx = 0;
        gbc.gridy = 2;
        ssnValidationPanel.add(validateSsnButton, gbc);
        
        validateSsnButton.addActionListener(new GenerateSsnListener(validateSsnField, ssnValidityIcon, validateSsnButton));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(ssnValidationPanel, gbc);
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

enum Actions {
    GENERATESSN,
    VALIDATESSN
  }
