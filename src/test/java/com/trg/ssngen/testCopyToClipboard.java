package com.trg.ssngen;

import static org.junit.Assert.*;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.Test;

public class testCopyToClipboard {

	@Test
	public void testCopyToClipboard() throws UnsupportedFlavorException, IOException {
		new ClipboardCopy().copyToClipBoard("Test string");
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		String clpbrdContents = (String) clpbrd.getData(DataFlavor.stringFlavor);
		
		assertEquals("Test string", clpbrdContents);
	}

}
