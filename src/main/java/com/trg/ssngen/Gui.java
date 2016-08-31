package com.trg.ssngen;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DateFormatter;
import javax.swing.text.PlainDocument;

public class Gui implements Runnable {

    private JFrame JFrame;

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
        JFormattedTextField jTxtfieldBdate;
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
        
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        df.setLenient(false);
        jTxtfieldBdate = new JFormattedTextField(df);
 
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
    }
    
    private String setDefaultDate() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, -18);
    	Date calToDate = cal.getTime();
    	return new SimpleDateFormat("dd.MM.yyyy").format(calToDate);
    }
}
