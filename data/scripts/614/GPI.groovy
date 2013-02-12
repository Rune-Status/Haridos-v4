class GPI implements GroovyScript, PlayerUpdateCodec {
    
    @Override
    public void init(Context context) {
	context.setPlayerUpdateCodec(this);
    }
    
    public void updatePlayer(Player player) {
	def packet = new MessageBuilder(53, PacketType.VAR_SHORT);
	def block = new MessageBuilder();
	packet.startBitAccess();
	packet.writeBits(1, 0);
	packet.writeBits(1, 0);
	packet.finishBitAccess();
	packet.writeBytes(block.getBuffer());
	player.getSession().write(packet.toMessage());
    }
    
}