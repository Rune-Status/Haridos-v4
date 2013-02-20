class GPI implements GroovyScript, PlayerUpdateCodec {
    
    @Override
    public void init(Context context) {
	context.setPlayerUpdateCodec(this);
    }
    
    public void updatePlayer(Player player) {
	if(player.getFlagManager().isMapRegionChanged()) {
	    Context.get().getActionSender().sendMapRegion(player, false);
	}
	def packet = new MessageBuilder(53, PacketType.VAR_SHORT);
	def block = new MessageBuilder();
	updatePlayerBlock(block, player, false);
	packet.startBitAccess();
	if(player.getFlagManager().updateNeeded()) {
	    packet.writeBits(1, 1);
	    updateLocalPlayer(packet, player);
	} else {
	    packet.writeBits(1, 0);
	}
	Iterator<Player> it$ = player.getLocalPlayers().iterator();
	while(it$.hasNext()) {
	    Player p = it$.next();
	    if(p != null && p.getLocation().withinDistance(player.getLocation(), 16) && World.get().getPlayers().contains(p)) {
		updatePlayerBlock(block, p, false);
		if(p.getFlagManager().updateNeeded()) {
		    packet.writeBits(1, 1);
		    packet.writeBits(11, p.getIndex());
		    updateLocalPlayer(packet, p);
		    continue;
		}
		packet.writeBits(1, 0);
	    } else {
		player.getPlayerExists()[p.getIndex()] = false;
		packet.writeBits(1, 1);
		packet.writeBits(11, p.getIndex());
		removeLocalPlayer(packet, p);
		it$.remove();
	    }
	}
	int added = 0;
	for(Player p : World.get().getPlayers()) {
	    if(added >= 10 || player.getLocalPlayers().size() >= 150) {
		break;
	    }
	    if(p == null) {
		continue;
	    }
	    if(p.getIndex() == player.getIndex() || player.getPlayerExists()[p.getIndex()] || !player.getLocation().withinDistance(p.getLocation(), 16)) {
		continue;
	    }
	    added++;
	    player.getLocalPlayers().add(p);
	    packet.writeBits(1, 1);
	    packet.writeBits(11, p.getIndex());
	    addLocalPlayer(packet, p);
	    updatePlayerBlock(block, p, true);
	    player.getPlayerExists()[p.getIndex()] = true;
	}
	packet.writeBits(1, 0);
	packet.finishBitAccess();
	packet.writeBytes(block.getBuffer());
	player.getSession().write(packet.toMessage());
    }
    
    private void addLocalPlayer(MessageBuilder packet, Player player) {
	packet.writeBits(2, 0);
	boolean hash = false;
	packet.writeBits(1, hash ? 0 : 1);
	if(!hash) {
	    packet.writeBits(2, 3);
	    packet.writeBits(18, player.getLocation().get18BitsHash());
	}
	def loc = player.getLocation();
	packet.writeBits(6, loc.getX() - (loc.getRegionX() << 6));
	packet.writeBits(6, loc.getY() - (loc.getRegionY() << 6));
	packet.writeBits(1, 1);
    }
    
    private void updateLocalPlayer(MessageBuilder packet, Player p) {
	if(p.getFlagManager().teleportUpdate()) {
	    updateLocalPlayerTeleport(packet, p);
	    return;
	}
	int walk = p.getWalking().getWalkDir();
	int run = p.getWalking().getRunDir();
	updateLocalPlayerStatus(packet, walk > -1 ? 1 : run > -1 ? 2 : 0, true);
	if(walk < 0 && run < 0)
		return;
	packet.writeBits(walk > -1 ? 3 : 4, walk > -1 ? walk : run);
    }
    
    private void updateLocalPlayerTeleport(MessageBuilder packet, Player p) {
	updateLocalPlayerStatus(packet, 3, true);
	packet.writeBits(1, 1);
	packet.writeBits(30, p.getLocation().get30BitsHash());
    }
    
    private void updateLocalPlayerStatus(MessageBuilder packet, int type, boolean status) {
	packet.writeBits(1, status ? 1 : 0);
	packet.writeBits(2, type);
    }
    
    private void removeLocalPlayer(MessageBuilder packet, Player player) {
	updateLocalPlayerStatus(packet, 0, false);
	packet.writeBits(1, 0);
    }
    
    private void updatePlayerBlock(MessageBuilder block, Player player, boolean force) {
	if(!player.getFlagManager().updateNeeded() && !force) {
	    return;
	}
	int maskData = 0;
	if (player.getFlagManager().flagged(UpdateFlag.ANIMATION)) {
	    maskData |= 0x80;
	}
	if(player.getFlagManager().flagged(UpdateFlag.GRAPHICS)) {
	    maskData |= 0x1000;
	}
	if(player.getWalking().getWalkDir() != -1 || player.getWalking().getRunDir() != -1) {
	    maskData |= 0x10;
	}
	if(force || player.getFlagManager().flagged(UpdateFlag.APPERANCE)) {
	    maskData |= 0x20;
	}
	if (maskData > 128)
	maskData |= 0x1;
	if (maskData > 32768)
	    maskData |= 0x800;
	block.writeByte(maskData);
	if (maskData > 128)
	    block.writeByte(maskData >> 8);
	if (maskData > 32768)
	   block.writeByte(maskData >> 16);
	if (player.getFlagManager().flagged(UpdateFlag.ANIMATION)) {
	    block.writeShortA(player.getFlagManager().getAnimation().getId());
	    block.writeByteA(player.getFlagManager().getAnimation().getDelay());
	}
	if(player.getFlagManager().flagged(UpdateFlag.GRAPHICS)) {
	    block.writeShortA(player.getFlagManager().getGraphic().getId());
	    block.writeInt(player.getFlagManager().getGraphic().getDelay());
	    block.writeByte(player.getFlagManager().getGraphic().getHeight());
	}
	if(player.getWalking().getWalkDir() != -1 || player.getWalking().getRunDir() != -1) {
	    block.writeByteC(player.getWalking().getWalkDir() != -1 ? 1 : 2);
	}
	if(force || player.getFlagManager().flagged(UpdateFlag.APPERANCE)) {
	    def app = player.getLooks().generate(player);
	    block.writeByteS(app.position());
	    block.addBytes128(app.getBuffer());
	}
    }
    
}