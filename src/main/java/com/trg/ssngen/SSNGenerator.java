package com.trg.ssngen;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.apache.logging.log4j.LogManager;

public class SSNGenerator {  
    private char checkmark;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SSNGenListener.class);
    
    public String generateSSN(boolean isPermanent, Date date, char gender) {  
        StringBuilder sb = new StringBuilder();
        
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
        //Logger.logMsg(Logger.DEBUG, "removeDelimiter called, parameter: " + ssn);
        //Logger.logMsg(Logger.DEBUG, "Starting to rebuild ssn without delimiter");
        String modifiedSSN = "";
        for(char ch : ssn.toCharArray()) {
            if(ch != '-' && ch != '+' && ch != 'A') {
                modifiedSSN += String.valueOf(ch);
            }
        }
        //Logger.logMsg(Logger.DEBUG, "SSN rebuilding done, return value: " + modifiedSSN);
        return modifiedSSN;
    }
    
    public String generateLastNumbersAndCheckmark(String partialSSN, boolean isPermanent, char gender) {
        Random rnd = new Random();
        boolean isValidRandomNumber = false;
        boolean isValidAgainstCheckmark = false;
        int lastDigits = 000;
        
        // Remove delimiter from ssn so that only 6 first digits remain
        String modifiedSSN = removeDelimiter(partialSSN);

        while(!isValidAgainstCheckmark) {
            
            while(!isValidRandomNumber) {
                if(isPermanent) {
                    lastDigits = rnd.nextInt(900 - 2 + 1) + 2;
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
            isValidAgainstCheckmark = isValidSSN(modifiedSSN + String.valueOf(lastDigits));
        }
        // Add leading zeroes if generated number is smaller than 100 or 10
        String returnValue = "";

        if(lastDigits <= 99 && lastDigits >= 10) {
            returnValue = String.format("%02d", lastDigits);
            return returnValue + String.valueOf(checkmark);
        } else if(lastDigits <= 9) {
            returnValue = String.format("%03d", lastDigits);
            return returnValue + String.valueOf(checkmark);
        } else {
            return lastDigits + String.valueOf(checkmark);
        }
    }
    
    private boolean isValidSSN(String ssn) {   
        int result = Integer.valueOf(ssn) % 31;

        switch(result) {
            case 0: 
                checkmark = '0';
                return true;
            case 1:
                checkmark = '1';
                return true;
            case 2:
                checkmark = '2';
                return true;
            case 3:
                checkmark = '3';
                return true;
            case 4: 
                checkmark = '4';
                return true;
            case 5: 
                checkmark = '5';
                return true;
            case 6:
                checkmark = '6';
                return true;
            case 7:
                checkmark = '7';
                return true;
            case 8:
                checkmark = '8';
                return true;
            case 9:
                checkmark = '9';
                return true;
            case 10:
                checkmark = 'A';
                return true;
            case 11:
                checkmark = 'B';
                return true;
            case 12: 
                checkmark = 'C';
                return true;
            case 13:
                checkmark = 'D';
                return true;
            case 14:
                checkmark = 'E';
                return true;
            case 15: 
                checkmark = 'F';
                return true;
            case 16:
                checkmark = 'H';
                return true;
            case 17:
                checkmark = 'J';
                return true;
            case 18: 
                checkmark = 'K';
                return true;
            case 19: 
                checkmark = 'L';
                return true;
            case 20:
                checkmark = 'M';
                return true;
            case 21:
                checkmark = 'N';
                return true;
            case 22:
                checkmark = 'P';
                return true;
            case 23:
                checkmark = 'R';
                return true;
            case 24:
                checkmark = 'S';
                return true;
            case 25:
                checkmark = 'T';
                return true;
            case 26:
                checkmark = 'I';
                return true;
            case 27:
                checkmark = 'V';
                return true;
            case 28:
                checkmark = 'W';
                return true;
            case 29:
                checkmark = 'X';
                return true;
            case 39:
                checkmark = 'Y';
                return true;
            default:
                return false;
        }
    }
}
