package com.trg.ssngen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import com.trg.ssngen.GenerateSsnListener;

public class Gui implements Runnable {

    private JFrame JFrame;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    
    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	
        this.JFrame = new JFrame("SSN Generator");
        this.JFrame.setPreferredSize(new Dimension(325, 315));
        this.JFrame.setResizable(false);
        this.JFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createSSNGenetatorPanel(JFrame.getContentPane());
        createSSNValidationPanel(JFrame.getContentPane());
        createAboutPanel(JFrame.getContentPane());
        
        this.JFrame.pack();
        this.JFrame.setVisible(true);
    }

	private void createAboutPanel(Container contentPane) {
		// TODO Auto-generated method stub
		
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
        JLabel clipboardCopyStatus;
		Border originalBorder;
        
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
        
        birthDateField = new JTextField();
        birthDateField.setPreferredSize(new Dimension(80,20));
        birthDateField.setText(setDefaultDate());     
        gbc.gridx = 0;
        gbc.gridy = 1;
        ssnGenPanel.add(birthDateField, gbc);
        originalBorder = birthDateField.getBorder();
        
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
        
        generateSsnButton = new JButton("Generate");
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
        gbc.insets = new Insets(3,3,3,3);
        ssnValidityIcon.setPreferredSize(new Dimension(25,25));
        gbc.anchor = GridBagConstraints.WEST;  
        gbc.gridx = 2;
        gbc.gridy = 4;
        ssnGenPanel.add(ssnValidityIcon, gbc);
        
        clipboardCopyStatus = new JLabel();
        gbc.anchor = GridBagConstraints.CENTER;  
        gbc.gridx = 1;
        gbc.gridy = 5;
        ssnGenPanel.add(clipboardCopyStatus, gbc);
        clipboardCopyStatus.setPreferredSize(new Dimension(108, 26));
        container.add(ssnGenPanel);
        
        // Add timer for clearing label text after displaying a messsage
        Timer labelTimer = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				clipboardCopyStatus.setText("");
			}
        });
        
        // Limit birthDateField to 10 characters
        birthDateField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if(birthDateField.getText().length() >= 10) 
        			e.consume();
        	}
        });
                     
        // ActionListener for generate button
        generateSsnButton.addActionListener(new GenerateSsnListener(birthDateField, genderFRadio, generatePermanentSsnRadio, generatedSsnField, ssnValidityIcon, clipboardCopyStatus));
        
        // Listener for making sure that date is filled in certain format
        birthDateField.setInputVerifier(new InputVerifier() {
        	@Override
        	public boolean verify(JComponent input) {
        		JTextField dateField = (JTextField) input;
        		String regex = "(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d";
        		
        		if(!dateField.getText().matches(regex) || !isDateValid(dateField.getText())) {
        			setGuiAsIsInvalidDatePresent(true, birthDateField, generateSsnButton, originalBorder);
                    return false;
        		}
        		setGuiAsIsInvalidDatePresent(false, birthDateField, generateSsnButton, originalBorder);
        		return true;
        	}
        });        
 
        // Two listeners for selecting all input in generatedSsnField if user interacts with the field
        generatedSsnField.addFocusListener(new FocusListener() {

			@Override 
			public void focusGained(FocusEvent arg0) {
				generatedSsnField.selectAll();
				if(!generatedSsnField.getText().isEmpty()) {
					copyToClipboard(generatedSsnField.getText(), clipboardCopyStatus);
					labelTimer.start();
			}}

			@Override 
			public void focusLost(FocusEvent e) {}
        	
        });
        
        generatedSsnField.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				generatedSsnField.selectAll();
				if(!generatedSsnField.getText().isEmpty()) {
					copyToClipboard(generatedSsnField.getText(), clipboardCopyStatus);
					labelTimer.start();
				}
			}

			@Override public void mouseEntered(MouseEvent e) {}

			@Override public void mouseExited(MouseEvent e) {}

			@Override public void mousePressed(MouseEvent e) {}

			@Override public void mouseReleased(MouseEvent e) {} 	
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
        
        validateSsnField = new JTextField();
        validateSsnField.setEditable(true);
        validateSsnField.setPreferredSize(new Dimension(90,20));
        gbc.insets = new Insets(3,3,3,3);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        ssnValidationPanel.add(validateSsnField, gbc);

        ssnValidityIcon = new JLabel();
        ssnValidityIcon.setPreferredSize(new Dimension(25,25));

        gbc.anchor = GridBagConstraints.EAST;  
        gbc.gridx = 1;
        gbc.gridy = 1;
        ssnValidationPanel.add(ssnValidityIcon, gbc);
        
        validateSsnButton = new JButton("Validate");
        validateSsnButton.setActionCommand(Actions.VALIDATESSN.name());
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        ssnValidationPanel.add(validateSsnButton, gbc);
        
        validateSsnButton.addActionListener(new GenerateSsnListener(validateSsnField, ssnValidityIcon));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(ssnValidationPanel, gbc);
        
	}
    
    private String setDefaultDate() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, -18);
    	Date calToDate = cal.getTime();
    	return new SimpleDateFormat(DATE_FORMAT).format(calToDate);
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
    
    private void setGuiAsIsInvalidDatePresent(boolean isDateValid, JTextField birthDateField, JButton generateSsnButton, Border originalBorder) {
		
    	if(isDateValid) {
			birthDateField.setToolTipText("Date must be within valid date between 01.01.1900 - 31.12.2099 and provided in dd.MM.yyyy format");
            Border bdBorder = BorderFactory.createLineBorder(Color.RED, 2);
            birthDateField.setBorder(bdBorder);
            generateSsnButton.setEnabled(false);
    	} else {
			generateSsnButton.setEnabled(true);
			birthDateField.setBorder(originalBorder);
			birthDateField.setToolTipText(null);
    	}
    }
    
    public void copyToClipboard(String str, JLabel label) {
		ClipboardCopy cbc = new ClipboardCopy();
		label.setFont (label.getFont().deriveFont (10.0f));
		if(cbc.copyToClipBoard(str)) {
			label.setText("<html><center>SSN copied to clipboard</center></html>");
			label.setForeground(new Color(0,100,0));
		} else {
			label.setText("<html><center>Copy to clipboard failed<br/>See log for more details</center></font><html>");
			label.setForeground(new Color(178,34,34));
		}
    }
}

enum Actions {
    GENERATESSN,
    VALIDATESSN
}