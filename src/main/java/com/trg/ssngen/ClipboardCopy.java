package com.trg.ssngen;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;

public class ClipboardCopy {

    private static final org.apache.logging.log4j.Logger Logger = LogManager.getLogger(ClipboardCopy.class);
	
	public boolean copyToClipBoard(String str) {
		Logger.debug("Copying " + str + " to clipboard");
		StringSelection stringSelection = new StringSelection(str);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);

		//Check if copy was successful
		String clpbrdContents = "";
		try {
			clpbrdContents = (String) clpbrd.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			Logger.error("Something went wrong with copying + " + str + " to clipboard: " + e.toString());
		} catch (IOException e) {
			Logger.error("Something went wrong with copying + " + str + " to clipboard: " + e.toString());
		}
		if(clpbrdContents.isEmpty()) {
			Logger.error(str + " could not be copied to clipboard, does JRE security.policy file allows programmatic access to clipboard?");
			return false;
		}
		Logger.debug("Copying " + str + " to clipboard was successful");
		return true;
	}
}
