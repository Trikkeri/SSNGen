package com.trg.ssngen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.ImageObserver;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
        GridBagLayout gbl = new GridBagLayout();
        container.setLayout(gbl);
        GridBagConstraints gbc  = new GridBagConstraints();
        
        int i = 0;
        
        i++;
        
        // Initialize jDatePicker
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.insets = new Insets(2,2,2,2);
        container.add(datePicker, gbc);
        
        i++;
        
        // Radiobuttons & group
        ButtonGroup ssnBGroup = new ButtonGroup();
        
        jRadiobtn = new JRadioButton("Temporary SSN");
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnBGroup.add(jRadiobtn);
        container.add(jRadiobtn, gbc);
        
        i++;
        
        jRadiobtn = new JRadioButton("Permanent SSN"); 
        gbc.gridx = 0;
        gbc.gridy = i;
        ssnBGroup.add(jRadiobtn);
        container.add(jRadiobtn, gbc);
        
        i++;
        
        jbtn = new JButton("Generate SSN");
        gbc.gridx = 0;
        gbc.gridy = i;
        container.add(jbtn, gbc);
        
        i++;
        
        jtxtfield = new JTextField();
        jtxtfield.setEnabled(true);
        jtxtfield.setEditable(false);
        jtxtfield.setPreferredSize(new Dimension(83,30));
        //jtxtfield.setText("010101-0101");
        gbc.gridx = 0;
        gbc.gridy = i;
        container.add(jtxtfield, gbc);
        
        i++;
        
        jlbl = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = i;
        //gbc.insets = new Insets(0,0,0,0);
        ImageIcon okIcon = new ImageIcon("src/main/resources/icons/ok.png", "SSN is valid!");
        
        jlbl.setIcon(okIcon);
        container.add(jlbl, gbc);
    }
    
}
