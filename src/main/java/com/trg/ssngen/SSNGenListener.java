package com.trg.ssngen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class SSNGenListener implements ActionListener {

    private JFormattedTextField date;
    
    public SSNGenListener(JFormattedTextField date) {
        this.date = date;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        Date bdate = (Date) this.date.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        
        System.out.println(cal.get(Calendar.YEAR));
        String test = String.valueOf(cal.get(Calendar.YEAR));
        
        StringBuilder sb = new StringBuilder();
        sb.append(test.charAt(2)).append(test.charAt(3));
        System.out.println(sb.toString());
        
        int valitulos = 120464126 % 31;
        System.out.println(valitulos);
        
    }
    
}
