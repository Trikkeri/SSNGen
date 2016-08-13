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
        JLabel jlbl;
        JButton jbtn;
        JRadioButton jRadiobtn;
        JTextField jtxtfield;
        JFormattedTextField jftxtfield;
        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);
        GridBagConstraints gbc  = new GridBagConstraints();
               
        int i = 0;
        
        i++;
        
        jftxtfield = new JFormattedTextField(new SimpleDateFormat("dd.MM.yyyy"));
        jftxtfield.setText("01.12.2015");
        jftxtfield.setPreferredSize(new Dimension(75,20));
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.insets = new Insets(2,2,2,2);
        container.add(jftxtfield, gbc);
        
        i++;
        
        // Radiobuttons & group for selecting gender
        ButtonGroup ssnGenderBGroup = new ButtonGroup();
        
        jRadiobtn = new JRadioButton("Female");
        jRadiobtn.setSelected(true);
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnGenderBGroup.add(jRadiobtn);
        container.add(jRadiobtn, gbc);
        
        i++;
        
        jRadiobtn = new JRadioButton("Male"); 
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnGenderBGroup.add(jRadiobtn);
        container.add(jRadiobtn, gbc);
        
        i++;
        
        // Radiobuttons & group for selecting SSN type
        ButtonGroup ssnModeBGroup = new ButtonGroup();
        
        jRadiobtn = new JRadioButton("Temporary SSN");
        jRadiobtn.setSelected(true);
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnModeBGroup.add(jRadiobtn);
        container.add(jRadiobtn, gbc);
        
        i++;
        
        jRadiobtn = new JRadioButton("Permanent SSN"); 
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnModeBGroup.add(jRadiobtn);
        container.add(jRadiobtn, gbc);
        
        i++;
        
        SSNGenListener ssgl = new SSNGenListener(jftxtfield);
        
        jbtn = new JButton("Generate SSN");
        gbc.gridx = 0;
        gbc.gridy = i;
        jbtn.addActionListener(ssgl);
        container.add(jbtn, gbc);
        
        i++;
        
        jtxtfield = new JTextField();
        jtxtfield.setEditable(false);
        jtxtfield.setPreferredSize(new Dimension(83,20));
        //jtxtfield.setText("010101-0101");
        gbc.gridx = 0;
        gbc.gridy = i;
        container.add(jtxtfield, gbc);
        
        jlbl = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        ImageIcon okIcon = new ImageIcon("src/main/resources/icons/ok.png", "SSN is valid!");
        
        jlbl.setIcon(okIcon);
        container.add(jlbl, gbc);
    }
    
}
