package com.trg.ssngen;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.apache.logging.log4j.LogManager;

public class SSNGenerator {  
    private char verificationmark;
    private static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(SSNGenListener.class);
    
    public String generateSSN(boolean isPermanent, Date date, char gender) {  
        Logger.debug("Starting to generate SSN");
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
        
        Logger.debug("Rebuilt date for ssn: " + sb.toString());
        
        // Generate random portion of ssn until valid ssn is generated
        String threeDigitsAndCheckmark = generateLastNumbersAndCheckmark(sb.toString(), isPermanent, gender);
        
        sb.append(threeDigitsAndCheckmark);
        Logger.debug("SSN Generation DONE: " + sb.toString());
        return sb.toString();
    }
    
    public boolean isSSNValid(String ssn) {
        if(ssn.isEmpty() || ssn.length() < 11) {
            return false;
        }
        
        // Remove delimiter from ssn so that only 9 first digits remain
        String modifiedSSN = removeDelimiter(ssn);
 
        modifiedSSN = modifiedSSN.substring(0, 9);
        boolean isValid = isValidSSN(modifiedSSN);
        Logger.debug("Checking if generated ssn is valid: " + isValid);
        return isValid;
    }

    private String removeDelimiter(String ssn) {
        Logger.debug("Starting to rebuild " + ssn + " without delimiter");
        String modifiedSSN = "";
        for(char ch : ssn.toCharArray()) {
            if(ch != '-' && ch != '+' && ch != 'A') {
                modifiedSSN += String.valueOf(ch);
            }
        }
        Logger.debug("SSN rebuilding done, return value: " + modifiedSSN);
        return modifiedSSN;
    }
    
    public String generateLastNumbersAndCheckmark(String partialSSN, boolean isPermanent, char gender) {
        Random rnd = new Random();
        boolean isValidRandomNumber = false;
        boolean isValidAgainstCheckmark = false;
        int lastDigits = 000;
        String zeroPadding = "";
        
        Logger.debug("Starting to generate last numbers and checkmark for " + partialSSN + ". Using rules: isPermanent: " + isPermanent + ", gender: " + gender) ;
        
        // Remove delimiter from ssn so that only 6 first digits remain
        String modifiedSSN = removeDelimiter(partialSSN);
        int loopCalc = 0;
        while(!isValidAgainstCheckmark) {
            Logger.debug("Generated ssn is valid against calculated checksum? " + isValidAgainstCheckmark);
            Logger.debug("Starting while loop " + String.valueOf(loopCalc));
            while(!isValidRandomNumber) {
            	Logger.debug("Generated last numbers are valid? " + isValidRandomNumber);
                if(isPermanent) {
                    lastDigits = rnd.nextInt(900 - 2 + 1) + 2;
                } else {
                    lastDigits = rnd.nextInt(999 - 900 + 1) + 900;
                }
                Logger.debug("Generated last digits for ssn: " + lastDigits);
                if(gender == 'F' && lastDigits % 2 == 0) {
                    isValidRandomNumber = true;
                } else if(gender == 'M' && lastDigits % 2 == 1) {
                    isValidRandomNumber = true;
                } else {
                    isValidRandomNumber = false;
                    lastDigits = 0;
                }
            }
            
            // Add leading zeroes if generated number is smaller than 100 or 10
            zeroPadding = String.format("%03d", lastDigits);
            
            isValidAgainstCheckmark = isValidSSN(modifiedSSN + String.valueOf(zeroPadding));
            loopCalc++;
            Logger.debug("isValidAgainstCheckmark has been set to " + isValidAgainstCheckmark + " after calculating verificationmark and verificationmark is " + this.verificationmark);
        }

	    Logger.debug("SSN Generation done, returning value: " + modifiedSSN + zeroPadding);
	    return zeroPadding + String.valueOf(verificationmark);
    }
    
    private boolean isValidSSN(String ssn) {   
        int remainder = calculateRemainder(ssn);
        switch(remainder) {
            case 0: 
                verificationmark = '0';
                return true;
            case 1:
                verificationmark = '1';
                return true;
            case 2:
                verificationmark = '2';
                return true;
            case 3:
                verificationmark = '3';
                return true;
            case 4: 
                verificationmark = '4';
                return true;
            case 5: 
                verificationmark = '5';
                return true;
            case 6:
                verificationmark = '6';
                return true;
            case 7:
                verificationmark = '7';
                return true;
            case 8:
                verificationmark = '8';
                return true;
            case 9:
                verificationmark = '9';
                return true;
            case 10:
                verificationmark = 'A';
                return true;
            case 11:
                verificationmark = 'B';
                return true;
            case 12: 
                verificationmark = 'C';
                return true;
            case 13:
                verificationmark = 'D';
                return true;
            case 14:
                verificationmark = 'E';
                return true;
            case 15: 
                verificationmark = 'F';
                return true;
            case 16:
                verificationmark = 'H';
                return true;
            case 17:
                verificationmark = 'J';
                return true;
            case 18: 
                verificationmark = 'K';
                return true;
            case 19: 
                verificationmark = 'L';
                return true;
            case 20:
                verificationmark = 'M';
                return true;
            case 21:
                verificationmark = 'N';
                return true;
            case 22:
                verificationmark = 'P';
                return true;
            case 23:
                verificationmark = 'R';
                return true;
            case 24:
                verificationmark = 'S';
                return true;
            case 25:
                verificationmark = 'T';
                return true;
            case 26:
                verificationmark = 'U';
                return true;
            case 27:
                verificationmark = 'V';
                return true;
            case 28:
                verificationmark = 'W';
                return true;
            case 29:
                verificationmark = 'X';
                return true;
            case 30:
                verificationmark = 'Y';
                return true;
        }
        verificationmark = (Character) null;
        return false;
    }

	private int calculateRemainder(String ssn) {
		int remainder = Integer.valueOf(ssn) % 31;
        Logger.debug("Checking if " + ssn + " is valid");
        Logger.debug(ssn +" % 31 = " + remainder);
		return remainder;
	}
}
