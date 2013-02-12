package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * SendSettingEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public class SendSettingEvent {
    
    public enum SettingType {
	NORMAL, B
    }
    
    private final Player player;
    private final int setting, value;
    private final SettingType type;
    
    public SendSettingEvent(Player player, int setting, int value, SettingType type) {
	this.player = player;
	this.setting = setting;
	this.value = value;
	this.type = type;
    }

    public Player getPlayer() {
	return player;
    }

    public int getSetting() {
	return setting;
    }

    public int getValue() {
	return value;
    }

    public SettingType getType() {
	return type;
    }
    
    @Override
    public String toString() {
	return "[Setting "+setting+" => "+value+" - Type: "+type.name()+"]";
    }

}
