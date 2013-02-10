package com.runecore.env.model.def;

/**
 * PlayerDefinition.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class PlayerDefinition implements EntityDefinition {
    
    private final String name;
    private final int combatLevel;
    
    public PlayerDefinition(String name, int combatLevel) {
	this.name = name;
	this.combatLevel = combatLevel;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public int getCombatLevel() {
	return combatLevel;
    }

}
