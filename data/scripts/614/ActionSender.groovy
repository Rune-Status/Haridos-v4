import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;

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
    
    public void sendMessage(SendMessageEvent event) {
	def builder = new MessageBuilder(18, PacketType.VAR_BYTE);
	builder.writeByte(event.getType());
	builder.writeInt(0);
	builder.writeByte(0);
	builder.writeString(event.getMessage());
	event.getPlayer().getSession().write(builder.toMessage());
    }
    
    public void sendLoginResponse(Player player) {
	def builder = new MessageBuilder();
	builder.writeByte(2);
	builder.writeByte(0);
	builder.writeByte(0);
	builder.writeByte(0);
	builder.writeByte(1);
	builder.writeByte(0);
	builder.writeShort(1);
	builder.writeByte(1);
	builder.writeMedium(0);
	builder.writeByte(1);
	def length = builder.position();
	def builder2 = new MessageBuilder();
	builder2.writeByte(length);
	builder2.writeBytes(builder);
	player.getSession().write(builder2.toMessage());
	sendMapRegion(player, true);
	sendMessage(new SendMessageEvent(player, "Welcome to DeadlyPKers v3!"));
    }
    
    public void sendInterface(SendInterfaceEvent event) {
	def builder = new MessageBuilder(3);
	builder.writeLEInt(event.getWindowId() * 65536 + event.getInterfaceId());
	frameIndex++;
	builder.writeShort(frameIndex);
	builder.writeZ(event.getShowId());
	builder.writeShort(event.getInterfaceId() >> 16 | event.getChildId());
	event.getPlayer().getSession().write(builder.toMessage());
    }
    
    public void sendWindowPane(SendWindowPaneEvent event) {
	def builder = new MessageBuilder(37);
	frameIndex++;
	builder.writeShort(frameIndex);
	builder.writeByteS(event.getSubPane());
	builder.writeShort(event.getPane());
	event.getPlayer().getSession().write(builder.toMessage());
    }
    
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
	    sendInterface(new SendInterfaceEvent(player, 1, 548, 215, 662));
	}
    }
    
    public void sendMapRegion(Player player, boolean login) {
	def loc = player.getLocation();
	def builder = new MessageBuilder(71, PacketType.VAR_SHORT);
	def force = true;
	if(login) {
	    builder.startBitAccess();
	    builder.writeBits(30, loc.getY() | ((loc.getZ() << 28) | (loc.getX() << 14)));
	    int playerIndex = 1;
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
    }
    
}