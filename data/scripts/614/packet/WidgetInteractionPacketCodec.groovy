import com.runecore.env.model.player.Player;

/**
 * WidgetInteractionPacketCodec.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
class WidgetInteractionPacketCodec implements GroovyScript, PacketCodec {
    
    @Override
    public void init(Context context) {
	context.register(0, this);
	context.register(13, this);
	context.register(14, this);
	context.register(20, this);
	context.register(24, this);
	context.register(40, this);
	context.register(48, this);
	context.register(52, this);
	context.register(55, this);
	context.register(79, this);
    }
    
    @Override
    public void execute(Player player, Message message) {
	int widgetId = message.readShort();
	int buttonId = message.readShort();
	int buttonId2 = message.readShort();
	int buttonId3 = message.readLEShortA();
	def data = [
	    widgetId,
	    buttonId,
	    buttonId2,
	    buttonId3
	].toArray();
	def adapters = Context.get().getWidgetAdapters().get(widgetId);
	if(adapters != null) {
	    adapters.handle(player, data as int[]);
	} else {
	    if(Application.DEBUG) {
		println("Unhandled widget data $widgetId $buttonId $buttonId2 $buttonId3");
	    }
	}
    }
    
}