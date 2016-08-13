package com.trg.ssngen;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SSNGenerator {
    
    public String generateSSN(boolean permanentSSN, Date date) {
        
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        // Build 6 first digits based on the date user has given
        if(cal.get(Calendar.DAY_OF_MONTH) < 10 ) {
            sb.append("0").append(cal.get(Calendar.DAY_OF_MONTH));
        } else {
            sb.append(cal.get(Calendar.DAY_OF_MONTH));
        }
        
        if(cal.get(Calendar.MONTH) < 10 ) {
            sb.append("0").append(cal.get(Calendar.MONTH));
        } else {
            sb.append(cal.get(Calendar.MONTH));
        }
        
        // Grab two last digits from year
        String year = String.valueOf(cal.get(Calendar.YEAR));
        sb.append(year.charAt(2)).append(year.charAt(3));
        
        // Delimiter
        if(cal.get(Calendar.YEAR) >= 2000) {
            sb.append("A");
        } else if(cal.get(Calendar.YEAR) >= 1900 && cal.get(Calendar.YEAR) <= 1999) {
            sb.append("-");
        } else {
            sb.append("+");
        }
        
        // Generate random portion of ssn until valid ssn is generated
        String unfinishedSSN = "";
        
        while(checkSSNValidity(unfinishedSSN)) {
            unfinishedSSN = sb.toString();
            if(permanentSSN) {
                unfinishedSSN += String.valueOf(rnd.nextInt(900));
            } else {
            
            }
        }
        
        return sb.toString();
    }
    
    public boolean checkSSNValidity(String ssn) {
        
        if(ssn.isEmpty()) {
            return false;
        }
        
        // Remove delimiter from ssn so that only 9 first digits remain
        String modifiedSSN = "";
        for(char ch : ssn.toCharArray()) {
            if(ch != '-' || ch != '+' || ch != 'A') {
                modifiedSSN += String.valueOf(ch);
            }
        }
        
        if(Integer.valueOf(modifiedSSN) % 31 == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    private char generateCheckmark(String partialSSN) {
        
        // Remove delimiter from ssn so that only 9 first digits remain
        String modifiedSSN = "";
        for(char ch : partialSSN.toCharArray()) {
            if(ch != '-' || ch != '+' || ch != 'A') {
                modifiedSSN += String.valueOf(ch);
            }
        }
        
        while(true) {
            int result = 0;
            
            result = Integer.valueOf(modifiedSSN) / 31;
            
            
            
        }
    }
}
