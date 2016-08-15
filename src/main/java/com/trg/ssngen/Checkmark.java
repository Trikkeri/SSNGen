package com.trg.ssngen;

public enum Checkmark {
    
    ZERO('0'),
    ONE('1'),
    TWO('2');
    
    private char checkmark;

    private Checkmark(char checkmark) {
        this.checkmark = checkmark;
    }
    
    public char getCheckmark() {
        return checkmark;
    }
    
}
