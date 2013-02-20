import com.runecore.env.model.player.Player;

/**
 * CommunicationPacketCodec.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
class CommunicationPacketCodec implements GroovyScript, PacketCodec {
    
    @Override
    public void init(Context context) {
	context.register(25, this);
    }
    
    @Override
    public void execute(Player player, Message message) {
	int effects = message.readShort();
	int numChars = message.readByte();
	if(numChars < 1)
		return;
	def text = Misc.decryptPlayerChat(message, numChars);
	if (text == null || text.isEmpty())
		return;
	if (text.startsWith("::") || text.startsWith(">>")) {
	    player.animate(Animation.create(7074));
	    player.graphics(Graphic.create(1222));
	    return;
	}
	def newText = new StringBuilder();
	boolean wasSpace = true;
	for (int i = 0; i < text.length(); i++) {
		if (wasSpace) {
			newText.append(("" + text.charAt(i)).toUpperCase());
			if (!String.valueOf(text.charAt(i)).equals(" "))
				wasSpace = false;
		} else
			newText.append(("" + text.charAt(i)).toLowerCase());
		if (String.valueOf(text.charAt(i)).contains(".")
			|| String.valueOf(text.charAt(i)).contains("!")
			|| String.valueOf(text.charAt(i)).contains("?"))
			wasSpace = true;
	}
	text = newText.toString();
	println text;
    }

    
    
}