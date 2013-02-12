package com.runecore.env.model.flag;

import java.util.BitSet;

/**
 * FlagManager.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 12, 2013
 */
public class FlagManager {
    
    /**
     * A BitSet of Flagged UpdateFlags
     */
    private final BitSet bitSet = new BitSet();
    
    /**
     * Is the UpdateFlag flagged
     * @param flag The UpdateFlag to check
     * @return
     */
    public boolean flagged(UpdateFlag flag) {
	return bitSet.get(flag.ordinal());
    }
    
    /**
     * Is an update needed? 
     * @return If an update is needed
     */
    public boolean updateNeeded() {
	return !bitSet.isEmpty();
    }
    
    /**
     * Flag an update flag
     * @param flag The UpdateFlag to flag
     */
    public void flag(UpdateFlag flag) {
	bitSet.flip(flag.ordinal());
    }
    
    /**
     * Sets the cache
     */
    public void reset() {
	bitSet.clear();
    }

}
