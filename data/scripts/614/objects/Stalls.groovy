/**
 * Stalls.groovy
 * @author Harry Andreas<harry@runecore.org>
 * Feb 18, 2013
 */
class Stalls implements GroovyScript, ObjectAdapter {
    
    @Override
    public void init(Context context) {
	context.register(this);
    }
    
    @Override
    public boolean accept(Player player, GameObject object, ObjectOption option) {
	if(object.getIdentifier() == 4151) {
	    return true;
	}
    }
    
    @Override
    public void handle(Player player, GameObject object, ObjectOption option) {
	
    }
    
}