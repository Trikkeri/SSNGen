package com.trg.ssngen;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SSNGenerator {
    
    public String generateSSN(boolean isPermanent, Date date, char gender) {
        
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
        String threeDigitsAndCheckmark = generateLastNumbersAndCheckmark(sb.toString(), isPermanent, gender);
        
        sb.append(threeDigitsAndCheckmark);
        
        return sb.toString();
    }
    
    public boolean checkSSNValidity(String ssn) {
        
        if(ssn.isEmpty()) {
            return false;
        }
        
        // Remove delimiter from ssn so that only 9 first digits remain
        String modifiedSSN = removeDelimiter(ssn);
        
        // Remove checkmark from the end of ssn
        
        
        if(Integer.valueOf(modifiedSSN) % 31 == 1) {
            return true;
        } else {
            return false;
        }
    }

    private String removeDelimiter(String ssn) {
        String modifiedSSN = "";
        for(char ch : ssn.toCharArray()) {
            if(ch != '-' || ch != '+' || ch != 'A') {
                modifiedSSN += String.valueOf(ch);
            }
        }
        return modifiedSSN;
    }
    
    public String generateLastNumbersAndCheckmark(String partialSSN, boolean isPermanent, char gender) {
        Random rnd = new Random();
        boolean isValidRandomNumber = false;
        boolean isValidAgainstCheckmark = false;
        int lastDigits = 000;
        char checkmark = 0;
        
        // Remove delimiter from ssn so that only 6 first digits remain
        String modifiedSSN = removeDelimiter(partialSSN);

        while(!isValidAgainstCheckmark) {
            
            while(!isValidRandomNumber) {
                if(isPermanent) {
                    lastDigits = rnd.nextInt(900);
                } else {
                    lastDigits = rnd.nextInt(999 - 900 + 1) + 900;
                }

                if(gender == 'F' && lastDigits % 2 == 0) {
                    isValidRandomNumber = true;
                } else if(gender == 'M' && lastDigits % 2 == 1) {
                    isValidRandomNumber = true;
                } else {
                    isValidRandomNumber = false;
                    lastDigits = 0;
                }
            }
            
            int result = 0;
            
            result = (lastDigits + Integer.valueOf(modifiedSSN)) / 31;
            
            for(Enum chk : Checkmark.values()) {
                if(chk.equals(result)) {
                    isValidAgainstCheckmark = true;
                    checkmark = (char) result;
                }
            }
        }
        
        return String.valueOf(lastDigits) + String.valueOf(checkmark);
    }
}
