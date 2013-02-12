import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;

import com.runecore.codec.event.RefreshLevelEvent;
import com.runecore.codec.event.SendMessageEvent;
import com.runecore.codec.event.SendSettingEvent;
import com.runecore.codec.event.SendSettingEvent.SettingType;

/**
 * ActionSender.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
class ActionSender implements GroovyScript, ActionSender {

    def frameIndex = 0;

    @Override
    public void init(Context context) {
	context.setActionSender(this);
    }
    
    @Override
    public void sendLogin(Player player) {
	sendLoginResponse(player);
	sendMapRegion(player, true);
	refreshGameInterfaces(player);
	refreshAccessMasks(player);
	sendSetting(new SendSettingEvent(player, 1240, 100 * 2, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 1584, 0, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 147, 150, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 173, 0, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 313, -1, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 802, -1, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 1085, 249852, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 1160, -1, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 1583, 511305630, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 439, 1025, SettingType.NORMAL));
	sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 130, 0, 2));
	sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 746, 130, 0, 2));
	sendSetting(new SendSettingEvent(player, 439, 0, SettingType.NORMAL));
	sendSetting(new SendSettingEvent(player, 168, 4, SettingType.B));
	sendSetting(new SendSettingEvent(player, 181, 0, SettingType.B));
	sendSetting(new SendSettingEvent(player, 234, 0, SettingType.B));
	sendSetting(new SendSettingEvent(player, 695, 0, SettingType.B));
	sendSetting(new SendSettingEvent(player, 768, 0, SettingType.B));
	sendMessage(new SendMessageEvent(player, "Welcome to DeadlyPKers v3."));
	player.getFlagManager().flag(UpdateFlag.APPERANCE);
	player.getSkills().refresh();
    }
    
    @Override
    public void sendMessage(SendMessageEvent event) {
	def builder = new MessageBuilder(18, PacketType.VAR_BYTE);
	builder.writeByte(event.getType());
	builder.writeInt(0);
	builder.writeByte(0);
	builder.writeString(event.getMessage());
	event.getPlayer().getSession().write(builder.toMessage());
    }

    @Override
    public void sendLoginResponse(Player player) {
	def builder = new MessageBuilder();
	builder.writeByte(2);
	builder.writeByte(0);
	builder.writeByte(0);
	builder.writeByte(0);
	builder.writeByte(1);
	builder.writeByte(0);
	builder.writeShort(player.getIndex());
	builder.writeByte(1);
	builder.writeMedium(0);
	builder.writeByte(1);
	def length = builder.position();
	def builder2 = new MessageBuilder();
	builder2.writeByte(length);
	builder2.writeBytes(builder);
	player.getSession().write(builder2.toMessage());
    }

    @Override
    public void sendInterface(SendInterfaceEvent event) {
	def builder = new MessageBuilder(3);
	builder.writeLEInt(event.getWindowId() * 65536 + event.getInterfaceId());
	frameIndex++;
	builder.writeShort(frameIndex);
	builder.writeZ(event.getShowId());
	builder.writeShort(event.getInterfaceId() >> 16 | event.getChildId());
	event.getPlayer().getSession().write(builder.toMessage());
    }

    @Override
    public void sendWindowPane(SendWindowPaneEvent event) {
	def builder = new MessageBuilder(37);
	frameIndex++;
	builder.writeShort(frameIndex);
	builder.writeByteS(event.getSubPane());
	builder.writeShort(event.getPane());
	event.getPlayer().getSession().write(builder.toMessage());
    }

    @Override
    public void refreshGameInterfaces(Player player) {
	def displayMode = player.getSession().getDisplayMode();
	if(displayMode == 0 || displayMode == 1) {
	    sendWindowPane(new SendWindowPaneEvent(player, 548, 0));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 62, 751));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 188, 752));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 14, 754));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 176, 748));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 178, 749));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 179, 750));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 181, 747));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 12, 745));
	    sendInterface(new SendInterfaceEvent(player, 1, 752, 9, 137));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 198, 884));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 199, 320));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 200, 190));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 201, 259));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 202, 149));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 203, 387));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 204, 271));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 205, 192));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 206, 891));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 207, 550));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 208, 551));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 209, 589));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 210, 261));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 211, 464));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 212, 187));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 213, 34));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 215, 662));
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 216, 182));
	}
    }
    
    @Override
    public void refreshAccessMasks(Player player) {
	def displayMode = player.getSession().getDisplayMode();
	if(displayMode == 0 || displayMode == 1) {
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 123, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 11, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 12, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 13, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 14, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 124, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 125, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 300, 190, 18, 0, 14));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 11, 190, 15, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 126, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 127, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 27, 149, 0, 69, 32142));
	    sendAccessMask(new SendAccessMaskEvent(player, 28, 55, 149, 0, 32, 0));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 128, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 129, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 29, 271, 8, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 130, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 93, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 94, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 95, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 96, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 97, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 98, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 99, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 1727, 187, 1, 0, 26));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 11, 187, 9, 36, 6));
	    sendAccessMask(new SendAccessMaskEvent(player, 12, 23, 187, 9, 0, 4));
	    sendAccessMask(new SendAccessMaskEvent(player, 24, 24, 187, 9, 32, 0));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 100, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, 0, 29, 34, 9, 40, 30));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 123, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 11, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 12, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 13, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 14, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 548, 123, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 11, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 12, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 13, 0, 2));
	    sendAccessMask(new SendAccessMaskEvent(player, -1, -1, 884, 14, 0, 2));
	}
    }

    @Override
    public void sendMapRegion(Player player, boolean login) {
	def loc = player.getLocation();
	def builder = new MessageBuilder(71, PacketType.VAR_SHORT);
	def force = true;
	def list = new ArrayList<Short>();
	if(login) {
	    builder.startBitAccess();
	    builder.writeBits(30, loc.getY() | ((loc.getZ() << 28) | (loc.getX() << 14)));
	    int playerIndex = player.getIndex();
	    for (int index = 1; index < 2048; index++) {
		if (index == playerIndex)
		    continue;
		builder.writeBits(18, 0);
	    }
	    builder.finishBitAccess();
	}
	builder.writeLEShortA(loc.getRegionY());
	builder.writeShortA(loc.getRegionX());
	builder.writeByteA(0);
	builder.writeByteA(1);
	if ((((loc.getRegionX() / 8) == 48) || ((loc.getRegionX() / 8) == 49)) && ((loc.getRegionY() / 8) == 48)) {
	    force = false;
	}
	if (((loc.getRegionX() / 8) == 48) && ((loc.getRegionY() / 8) == 148)) {
	    force = false;
	}
	for (int xCalc = ((loc.getRegionX() - 6) / 8); xCalc <= ((loc.getRegionX() + 6) / 8); xCalc++) {
	    for (int yCalc = ((loc.getRegionY() - 6) / 8); yCalc <= ((loc.getRegionY() + 6) / 8); yCalc++) {
		short region = (yCalc + (xCalc << 8));
		list.add(region);
		if (force || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
		    int[] keys = RegionData.get(region);
		    builder.writeInt(keys[0]);
		    builder.writeInt(keys[1]);
		    builder.writeInt(keys[2]);
		    builder.writeInt(keys[3]);
		}
	    }
	}
	player.getSession().write(builder.toMessage());
	for(short i : list) {
	    int[] key = RegionData.get(i);
	    LandscapeParser.parseLandscape(i, key);
	}
    }
    
    @Override
    public void refreshLevel(RefreshLevelEvent event) {
	def builder = new MessageBuilder(85);
	builder.writeInt((int)event.getXp());
	builder.writeByte(event.getLevel());
	builder.writeByteS(event.getIndex());
	event.getPlayer().getSession().write(builder.toMessage());
    }
    
    @Override
    public void sendSetting(SendSettingEvent event) {
	if(event.getType() == SettingType.NORMAL) {
	    if(event.getValue() < Byte.MIN_VALUE || event.getValue() > Byte.MAX_VALUE) {
		def builder = new MessageBuilder(84);
		builder.writeInt2(event.getValue());
		builder.writeShortA(event.getSetting());
		event.getPlayer().getSession().write(builder.toMessage());
	  } else {
		def builder = new MessageBuilder(25);
		builder.writeShortA(event.getSetting());
		builder.writeByteS(event.getValue());
		event.getPlayer().getSession().write(builder.toMessage());
	    }
	} else if(event.getType() == SettingType.B) {
	    if (event.getValue() < Byte.MIN_VALUE || event.getValue() > Byte.MAX_VALUE) {
		def builder = new MessageBuilder(89);
		frameIndex++;
		builder.writeLEShortA(frameIndex);
		builder.writeLEShortA(event.getSetting());
		builder.writeInt(event.getValue());
		event.getPlayer().getSession().write(builder.toMessage());
	    } else {
	    	def builder = new MessageBuilder(103);
		frameIndex++;
		builder.writeLEShort(frameIndex);
	        builder.writeByteA(event.getValue());
	        builder.writeShortA(event.getSetting());
		event.getPlayer().getSession().write(builder.toMessage());
            }
	}
    }
    
    @Override
    public void sendAccessMask(SendAccessMaskEvent event) {
	def builder = new MessageBuilder(35);
	builder.writeLEShortA(event.getSet2());
	builder.writeLEInt(event.getInter() << 16 | event.getChild());
    	frameIndex++;
    	builder.writeLEShortA(frameIndex);
	builder.writeShortA(event.getSet());
	builder.writeInt2(event.getInter2() << 16 | event.getChild2());
	event.getPlayer().getSession().write(builder.toMessage());
    }
    
}