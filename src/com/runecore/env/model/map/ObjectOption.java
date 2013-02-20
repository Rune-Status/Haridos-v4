package com.runecore.env.model.map;

/**
 * ObjectOption.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 18, 2013
 */
public enum ObjectOption {
    
    OPTION_1(19),
    OPTION_2(80),
    OPTION_3(-1),
    OPTION_4(-1);
    
    private final int opcode;
    
    ObjectOption(int opcode) {
	this.opcode = opcode;
    }
    
    public static ObjectOption get(int opcode) {
	for(ObjectOption oo : values()) {
	    if(oo.opcode == opcode) {
		return oo;
	    }
	}
	return null;
    }

}
