package com.trg.ssngen;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class testTest {

	@Test
	public void testSSNValidity() {
		SSNGenerator ssngen = new SSNGenerator();
		String[] ssnArray = new String[5];
		ssnArray[0] = "010101-0101";
		ssnArray[1] = "010101+1234";
		ssnArray[2] = "130805A4687";
		ssnArray[3] = "130805X4687";
		
		assertTrue(ssngen.isSSNValid(ssnArray[0]));
		assertFalse(ssngen.isSSNValid(ssnArray[1]));
		assertTrue(ssngen.isSSNValid(ssnArray[2]));
		assertFalse(ssngen.isSSNValid(ssnArray[3]));
	}

}
