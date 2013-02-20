package com.runecore.env.model.def;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.LinkedList;

import com.runecore.util.BufferUtils;

/**
 * ItemDefinition.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 18, 2013
 */
public final class ItemDefinition {

    public static int MAX_SIZE = 23000;
    private static ItemDefinition[] definitions;
    private boolean fullHat;
    private boolean fullMask;
    private boolean fullBody;
    private boolean tradable;
    private boolean twoHanded;
    private boolean dropable;

    private int storePrice;
    private int lowAlch;
    private int highAlch;
    private int renderAnimation = -1;

    @SuppressWarnings("resource")
    public static void init() throws IOException {
	definitions = new ItemDefinition[MAX_SIZE];
	FileChannel channel;
	try {
	    channel = new RandomAccessFile("./data/itemDefinitions.bin", "r")
		    .getChannel();
	    ByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0,
		    channel.size());
	    int length = buffer.getShort();
	    for (int i = 0; i < length; i++) {
		int id = buffer.getShort();
		if (id == -1) {
		    continue;
		}
		ItemDefinition def = ItemDefinition.forId(id);
		def.id = id;
		def.equipId = buffer.getShort();
		def.noted = buffer.get() != 0; // get() == 0 //Unnoted
		def.examine = BufferUtils.readRS2String(buffer);
		if (!def.isNoted()) {
		    def.weight = buffer.getDouble();
		    def.equipmentSlot = buffer.get();
		    def.setFullHat(buffer.get() == 1);
		    def.setFullMask(buffer.get() == 1);
		    def.fullBody = buffer.get() == 1;
		    def.tradable = buffer.get() == 1;
		    def.attackSpeed = buffer.get();
		    boolean hasBonus = buffer.get() == 1;
		    if (hasBonus) {
			for (int x = 0; x < 15; x++) {
			    def.bonus[x] = buffer.getShort();
			}
		    }
		    hasBonus = buffer.get() == 1;
		    if (hasBonus) {
			for (int x = 0; x < 3; x++) {
			    def.absorptionBonus[x] = buffer.getShort();
			}
		    }
		}
		def.highAlch = buffer.getInt();
		def.lowAlch = buffer.getInt();
		def.storePrice = buffer.getInt();
		def.dropable = buffer.get() == 1;
		def.twoHanded = buffer.get() == 1;
	    }
	    channel.close();
	} catch (Throwable t) {
	    t.printStackTrace();
	}
	loadEquipIds();
	loadRender();
	channel = null;
    }
    
    public static LinkedList<String> readFile(String directory) throws IOException {
        LinkedList<String> fileLines = new LinkedList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(directory));
            String string;
            while ((string = reader.readLine()) != null) {
                fileLines.add(string);
            }
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }
        return fileLines;
    }

    public static void loadEquipIds() throws NumberFormatException, IOException {
	for (String string : readFile("data/equipIds.txt")) {
	    String[] array = string.split(":");
	    int id = Integer.parseInt(array[0]);
	    ItemDefinition def = definitions[id];
	    if (def == null) {
		def = definitions[id] = new ItemDefinition();
	    }
	    def.setEquipId(Integer.parseInt(array[1]));
	}
    }
    
    public static void loadRender() throws NumberFormatException, IOException {
	for (String string : readFile("data/renderIds.txt")) {
	    String[] array = string.split(":");
	    int id = Integer.parseInt(array[0]);
	    ItemDefinition def = definitions[id];
	    if (def == null) {
		def = definitions[id] = new ItemDefinition();
	    }
	    def.setRenderAnimation(Integer.parseInt(array[1]));
	}
    }

    public static ItemDefinition forId(int id) {
	if (id == -1)
	    return null;
	ItemDefinition def = definitions[id];
	if (def == null) {
	    definitions[id] = new ItemDefinition();
	}
	return definitions[id];
    }

    public static ItemDefinition forName(String name) {
	for (ItemDefinition definition : definitions) {
	    if (definition.name.equalsIgnoreCase(name)) {
		return definition;
	    }
	}
	return null;
    }

    public static void clear() {
	definitions = new ItemDefinition[MAX_SIZE];
    }

    public String name;
    private String examine;
    private int id;
    public int equipId;
    private int[] bonus = new int[15];
    private boolean stackable;
    private boolean noted;
    private double weight;
    private boolean members;
    private int attackSpeed, equipmentSlot;

    private boolean extraDefinitions;
    private int[] absorptionBonus = new int[3];

    public String getName() {
	return name;
    }

    public int getId() {
	return id;
    }

    public int getEquipId() {
	return equipId;
    }

    public int getRenderId() {
	return 0;
    }

    public int[] getBonus() {
	return bonus;
    }

    public boolean isStackable() {
	return stackable || noted;
    }

    public boolean isNoted() {
	return noted;
    }

    public String getExamine() {
	return examine;
    }

    public double getWeight() {
	return weight;
    }

    public int getAttackSpeed() {
	return attackSpeed;
    }

    public int getEquipmentSlot() {
	return equipmentSlot;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setExamine(String examine) {
	this.examine = examine;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setEquipId(int equipId) {
	this.equipId = equipId;
    }

    public void setBonus(int[] bonus) {
	this.bonus = bonus;
    }

    public void setBonusAtIndex(int index, int value) {
	this.bonus[index] = value;
    }

    public void setStackable(boolean stackable) {
	this.stackable = stackable;
    }

    public void setNoted(boolean noted) {
	this.noted = noted;
    }

    public void setWeight(double weight) {
	this.weight = weight;
    }

    public void setAttackSpeed(int attackSpeed) {
	this.attackSpeed = attackSpeed;
    }

    public void setEquipmentSlot(int equipmentSlot) {
	this.equipmentSlot = equipmentSlot;
    }

    public void setMembers(boolean members) {
	this.members = members;
    }

    public boolean isMembers() {
	return members;
    }

    public void setExtraDefinitions(boolean extraDefinition) {
	this.extraDefinitions = extraDefinition;
    }

    public boolean isExtraDefinitions() {
	return extraDefinitions;
    }

    public static ItemDefinition[] getDefinitions() {
	return definitions;
    }

    public void setAbsorptionBonus(int[] absorptionBonus) {
	this.absorptionBonus = absorptionBonus;
    }

    public int[] getAbsorptionBonus() {
	return absorptionBonus;
    }

    /**
     * @return the fullHat
     */
    public boolean isFullHat() {
	return fullHat;
    }

    /**
     * @param fullHat
     *            the fullHat to set
     */
    public void setFullHat(boolean fullHat) {
	this.fullHat = fullHat;
    }

    /**
     * @return the fullMask
     */
    public boolean isFullMask() {
	return fullMask;
    }

    /**
     * @param fullMask
     *            the fullMask to set
     */
    public void setFullMask(boolean fullMask) {
	this.fullMask = fullMask;
    }

    public boolean isFullBody() {
	return fullBody;
    }

    public void setFullBody(boolean fullBody) {
	this.fullBody = fullBody;
    }

    public boolean isTradable() {
	return tradable;
    }

    public void setTradable(boolean tradable) {
	this.tradable = tradable;
    }

    public boolean isTwoHanded() {
	return twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
	this.twoHanded = twoHanded;
    }

    public boolean isDropable() {
	return dropable;
    }

    public void setDropable(boolean dropable) {
	this.dropable = dropable;
    }

    public int getLowAlch() {
	return lowAlch;
    }

    public void setLowAlch(int lowAlch) {
	this.lowAlch = lowAlch;
    }

    public int getHighAlch() {
	return highAlch;
    }

    public void setHighAlch(int highAlch) {
	this.highAlch = highAlch;
    }

    public int getStorePrice() {
	return storePrice;
    }

    public void setStorePrice(int i) {
	this.storePrice = i;
    }

    public int getRenderAnimation() {
	return renderAnimation;
    }

    public void setRenderAnimation(int renderAnimation) {
	this.renderAnimation = renderAnimation;
    }

}