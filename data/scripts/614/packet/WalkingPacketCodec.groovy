import com.runecore.env.model.player.Player;
import com.runecore.network.io.Message;

/**
 * ActionSender.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
class WalkingPacketCodec implements GroovyScript, PacketCodec {
    
    @Override
    public void init(Context context) {
	context.register(60, this);
	context.register(71, this);
    }
    
    @Override
    public void execute(Player player, Message message) {
	if(message.getOpcode() == 60) {
	    int size = message.getLength();
	    int steps = (size - 5) / 2;
	    if (steps > 25)
	    return;
	    int firstX = message.readShort() - (player.getLocation().getRegionX() - 6)  * 8;
	    int firstY = message.readShort() - (player.getLocation().getRegionY() - 6) * 8;
	    boolean runSteps = message.readByteC() == -1;
	    player.getWalking().reset();
	    player.getWalking().setIsRunning(runSteps);
	    player.getWalking().addToWalkingQueue(firstX, firstY);
	    for (int i = 0; i < steps; i++) {
		int localX = message.readByte() + firstX;
		int localY = message.readByte() + firstY;
		player.getWalking().addToWalkingQueue(localX, localY);
	    }
	} else if(message.getOpcode() == 71) {
	    int size = message.getLength();
	    size -= 14;
	    int steps = (size - 5) / 2;
	    int firstY = message.readShort() - (player.getLocation().getRegionY() - 6)* 8;
	    boolean runSteps = message.readByteC() == -1;
	    int firstX = message.readShort() - (player.getLocation().getRegionX() - 6)* 8;
	    player.getWalking().reset();
	    player.getWalking().setIsRunning(runSteps);
	    player.getWalking().addToWalkingQueue(firstX, firstY);
	    for (int i = 0; i < steps; i++) {
		int localX = message.readByte() + firstX;
		int localY = message.readByte() + firstY;
		player.getWalking().addToWalkingQueue(localX, localY);
	    }
	}
    }
    
}