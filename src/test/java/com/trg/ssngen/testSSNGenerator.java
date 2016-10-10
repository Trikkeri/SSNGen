package com.trg.ssngen;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.junit.Test;

public class testSSNGenerator {

	@Test
	public void testSSNValidity() {
		SSNGenerator ssngen = new SSNGenerator();
		String[] ssnArray = new String[9];
		ssnArray[0] = "010101-0101";
		ssnArray[1] = "010101+1234";
		ssnArray[2] = "130805A4687";
		ssnArray[3] = "130805X4687";
		ssnArray[4] = "010101-010";
		ssnArray[5] = "200898-9155";
		ssnArray[6] = "200898-915C";
		
		assertTrue(ssngen.isSSNValid(ssnArray[0]));
		assertFalse(ssngen.isSSNValid(ssnArray[1]));
		assertTrue(ssngen.isSSNValid(ssnArray[2]));
		assertFalse(ssngen.isSSNValid(ssnArray[3]));
		assertFalse(ssngen.isSSNValid(ssnArray[4]));
		assertTrue(ssngen.isSSNValid(ssnArray[5]));
		assertFalse(ssngen.isSSNValid(ssnArray[6]));
	}
	
	@Test
	public void testSSNGenerationReturnsCorrectBirthDateAndDelimiter() throws NullPointerException, ParseException {
		SSNGenerator ssngen = new SSNGenerator();
		String[] bdateArray = new String[9];
		bdateArray[0] = "1.1.2015";
		bdateArray[1] = "01.01.2015";
		bdateArray[2] = "1.01.2015";
		bdateArray[3] = "31.12.1999";
		bdateArray[4] = "31.12.1899";
		
		DateFormat format = new SimpleDateFormat("dd.MM.yyyyy", Locale.ENGLISH);
		
		String ssn1 = ssngen.generateSSN(true, format.parse(bdateArray[0]), "F");
		String ssn2 = ssngen.generateSSN(true, format.parse(bdateArray[1]), "F");
		String ssn3 = ssngen.generateSSN(true, format.parse(bdateArray[2]), "F");
		String ssn4 = ssngen.generateSSN(true, format.parse(bdateArray[3]), "F");
		String ssn5 = ssngen.generateSSN(true, format.parse(bdateArray[4]), "F");
		
		assertEquals("010115A", ssn1.substring(0, 7));
		assertEquals("010115A", ssn2.substring(0, 7));
		assertEquals("010115A", ssn3.substring(0, 7));
		assertEquals("311299-", ssn4.substring(0, 7));
		assertEquals("311299+", ssn5.substring(0, 7));
	}
}
