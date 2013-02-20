import com.runecore.env.model.player.Player;

/**
 * LogoutWidgetAdapter.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 17, 2013
 */
class LogoutWidgetAdapter implements GroovyScript, WidgetAdapter {
    
    @Override
    public void init(Context context) {
	context.register(182, this);
    }
    
    @Override
    public boolean handle(Player player, int[] data) {
	if(data[1] == 7 || data[1] == 9) {
	    Context.get().getActionSender().sendLogout(player);
	    return true;
	}
	return false;
    }
    
}