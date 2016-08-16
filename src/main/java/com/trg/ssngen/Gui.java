package com.trg.ssngen;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Gui implements Runnable {

    private JFrame JFrame;

    @Override
    public void run() {
        this.JFrame = new JFrame("SSN Generator");
        //this.JFrame.setPreferredSize(new Dimension(280, 200));
        this.JFrame.setPreferredSize(new Dimension(300, 300));
        this.JFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        createComponents(this.JFrame.getContentPane());
        
        this.JFrame.pack();
        this.JFrame.setVisible(true);
    }

    private void createComponents(Container container) {
        JFormattedTextField jTtxtfieldDate;
        JRadioButton jRadiobtnGenderF;
        JRadioButton jRadiobtnGenderM;
        JRadioButton jRadiobtnPermSSN;
        JRadioButton jRadiobtnTempSSN;
        JButton jbtnGenerateSSN;
        JTextField jTxtfieldSSN;
        JLabel jlblValidtyIcon;

        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);
        GridBagConstraints gbc  = new GridBagConstraints();
        
        ButtonGroup ssnGenderBGroup = new ButtonGroup();
        ButtonGroup ssnModeBGroup = new ButtonGroup();
               
        int i = 0;
        
        i++;
        
        jTtxtfieldDate = new JFormattedTextField(new SimpleDateFormat("dd.MM.yyyy"));
        jTtxtfieldDate.setText("01.12.2015");
        jTtxtfieldDate.setPreferredSize(new Dimension(75,20));
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.insets = new Insets(2,2,2,2);
        container.add(jTtxtfieldDate, gbc);
        
        i++;     
        
        jRadiobtnGenderF = new JRadioButton("Female");
        jRadiobtnGenderF.setSelected(true);
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnGenderBGroup.add(jRadiobtnGenderF);
        container.add(jRadiobtnGenderF, gbc);
        
        i++;
        
        jRadiobtnGenderM = new JRadioButton("Male"); 
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnGenderBGroup.add(jRadiobtnGenderM);
        container.add(jRadiobtnGenderM, gbc);
        
        i++;
        
        jRadiobtnPermSSN = new JRadioButton("Permanent SSN"); 
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnModeBGroup.add(jRadiobtnPermSSN);
        container.add(jRadiobtnPermSSN, gbc);
        
        i++;
        
        jRadiobtnTempSSN = new JRadioButton("Temporary SSN");
        jRadiobtnTempSSN.setSelected(true);
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnModeBGroup.add(jRadiobtnTempSSN);
        container.add(jRadiobtnTempSSN, gbc);
        
        i++;
               
        jbtnGenerateSSN = new JButton("Generate SSN");
        gbc.gridx = 0;
        gbc.gridy = i;
        jbtnGenerateSSN.addActionListener(ssgl);
        SSNGenListener ssgl = new SSNGenListener(jTtxtfieldDate, jRadiobtnGenderF, jRadiobtnPermSSN, jTextField jTxtfieldSSN);
        container.add(jbtnGenerateSSN, gbc);

        i++;
        
        jTxtfieldSSN = new JTextField();
        jTxtfieldSSN.setEditable(false);
        jTxtfieldSSN.setPreferredSize(new Dimension(83,20));
        //jtxtfield.setText("010101-0101");
        gbc.gridx = 0;
        gbc.gridy = i;
        container.add(jTxtfieldSSN, gbc);
        
        jlblValidtyIcon = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        ImageIcon okIcon = new ImageIcon("src/main/resources/icons/ok.png", "SSN is valid!");
        
        jlblValidtyIcon.setIcon(okIcon);
        container.add(jlblValidtyIcon, gbc);
    }
    
}
