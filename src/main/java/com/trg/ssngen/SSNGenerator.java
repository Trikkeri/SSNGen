package com.trg.ssngen;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;

public class SSNGenerator {  
    private static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(GenerateSsnListener.class);
    
    public String generateSSN(boolean isPermanent, Date date, String gender) throws NullPointerException {  
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
        
        if(cal.get(Calendar.MONTH) + 1 < 10 ) {
            sb.append("0").append(cal.get(Calendar.MONTH) + 1);
        } else {
            sb.append(cal.get(Calendar.MONTH) + 1);
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
    	Logger.debug("Checking if generated " + ssn + " is valid");
        if(ssn.isEmpty() || ssn.length() < 11) {
        	Logger.error("SSN is empty or shorter than 11 characters, SSN is invalid");
            return false;
        } 
        
        // Save delimiter for later use
    	String delimiter = ssn.substring(6, 7);
    	
    	// Return false if delimiter is not valid
        if (!delimiter.equals("+") && !delimiter.equals("-") && !delimiter.equals("A")) {
        	Logger.debug("Delimiter is incorrect, SSN is invalid");
        	return false;
        }
        
        // Remove delimiter from ssn so that only 9 first digits remain
        String modifiedSSN = removeDelimiter(ssn);     
        
        // Save checkmark for validation
        String checkmarkFromSSN = ssn.substring(10, 11);
        
        // Remove checkmark from ssn
        modifiedSSN = modifiedSSN.substring(0, 9);
        
        // Generate new checkmark based on partial ssn
        String generatedCheckmark = calculateCheckmark(modifiedSSN);
        
        Logger.debug("Original checkmark: " + checkmarkFromSSN);
        Logger.debug("Generated checkmark: " + generatedCheckmark);
        
        if(checkmarkFromSSN.equals(generatedCheckmark)) {
        	return true;
        } else {
        	return false;
        }       
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
    
    private String generateLastNumbersAndCheckmark(String partialSSN, boolean isPermanent, String gender) {
        Random rnd = new Random();
        boolean isValidRandomNumber = false;
        boolean isValidAgainstCheckmark = false;
        int lastDigits = 0;
        String zeroPadding = "";
        String checkmark = "";
        
        Logger.debug("Starting to generate last numbers and checkmark for " + partialSSN + ". Using rules: isPermanent: " + isPermanent + ", gender: " + gender) ;
        
        // Remove delimiter from ssn so that only 6 first digits remain
        
        // Save delimiter for later use
    	String delimiter = partialSSN.substring(6, 7);

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
                if(gender.equals("F") && lastDigits % 2 == 0) {
                    isValidRandomNumber = true;
                } else if(gender.equals("M") && lastDigits % 2 == 1) {
                    isValidRandomNumber = true;
                } else {
                    isValidRandomNumber = false;
                    lastDigits = 0;
                }
            }
            
            // Add leading zeroes if generated number is smaller than 100 or 10
            zeroPadding = String.format("%03d", lastDigits);
            
            checkmark = calculateCheckmark(modifiedSSN + String.valueOf(zeroPadding));
            
            isValidAgainstCheckmark = isSSNValid(modifiedSSN + delimiter + String.valueOf(zeroPadding) + String.valueOf(checkmark));
            
            loopCalc++;
            Logger.debug("isValidAgainstCheckmark has been set to " + isValidAgainstCheckmark + " after calculating verificationmark and verificationmark is " + String.valueOf(checkmark));
        }

	    Logger.debug("SSN Generation done, returning value: " + modifiedSSN + zeroPadding + String.valueOf(checkmark));
	    return zeroPadding + String.valueOf(checkmark);
    }
    
    private String calculateCheckmark(String ssn) {   
        int remainder = calculateRemainder(ssn);
        switch(remainder) {
            case 0: 
                return "0";
            case 1:
            	return "1";
            case 2:
            	return "2";
            case 3:
            	return "3";
            case 4: 
            	return "4";
            case 5: 
            	return "5";
            case 6:
            	return "6";
            case 7:
            	return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "A";
            case 11:
                return "B";
            case 12: 
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15: 
                return "F";
            case 16:
                return "H";
            case 17:
                return "J";
            case 18: 
                return "K";
            case 19: 
                return "L";
            case 20:
                return "M";
            case 21:
                return "N";
            case 22:
                return "P";
            case 23:
                return "R";
            case 24:
                return "S";
            case 25:
                return "T";
            case 26:
                return "U";
            case 27:
                return "V";
            case 28:
                return "W";
            case 29:
                return "X";
            case 30:
                return "Y";
        }
        return "";
    }

	private int calculateRemainder(String ssn) {
		int remainder = Integer.valueOf(ssn) % 31;
        Logger.debug("Checking if " + ssn + " is valid");
        Logger.debug(ssn +" % 31 = " + remainder);
		return remainder;
	}
}
