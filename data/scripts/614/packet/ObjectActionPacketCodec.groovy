import com.runecore.env.model.map.GameObject;
import com.runecore.env.model.map.ObjectOption;
import com.runecore.env.model.player.Player;
import com.runecore.env.core.Tick;

/**
 * ObjectActionPacketCodec.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 18, 2013
 */
class ObjectActionPacketCodec implements GroovyScript, PacketCodec {
    
    @Override
    public void init(Context context) {
	context.register(19, this);
	context.register(80, this);
    }
    
    @Override
    public void execute(Player player, Message message) {
	def option = ObjectOption.get(message.getOpcode());
	def object = null;
	def location = null;
	if(option == ObjectOption.OPTION_1) {
	    int y = message.readLEShortA();
	    int x = message.readLEShortA();
	    int objectId = message.readLEShortA();
	    message.readByteC();
	    location = Location.locate(x, y, player.getLocation().getZ());
	    object = new GameObject(objectId, location);
	} else if(option == ObjectOption.OPTION_2) {
	    int id = message.readLEShortA();
	    int x = message.readShort();
	    int y = message.readLEShortA();
	    message.readByteC();
	    location = Location.locate(x, y, player.getLocation().getZ());
	    object = new GameObject(id, location);
	}
	PathFinderExecutor.walkTo(player, location);
	def tick = new Tick(1) {
	    @Override
	    public void execute() {
		if(!player.getWalking().isMoving()) {
		    for(ObjectAdapter adapter : Context.get().getObjectAdapters()) {
			if(adapter.accept(player, object, option)) {
			    adapter.handle(player, object, option);
			    return;
			}
		    }
		    if(Application.DEBUG) {
			println("Unhandled object id: $object.getIdentifier()");
		    }
		    stop();
		    return;
		}
	    }  
	};
    	player.register("coord_tick", tick, true);
    }
    
}