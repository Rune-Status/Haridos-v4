package com.runecore.env.model;

import com.runecore.env.model.def.EntityDefinition;
import com.runecore.env.model.flag.Animation;
import com.runecore.env.model.flag.Graphic;
import com.runecore.env.model.flag.UpdateFlag;

/**
 * Animable.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public abstract class Animable extends Entity {

    @Override
    public abstract EntityDefinition getDefinition();
    @Override
    public abstract void tick();
    
    public void animate(Animation a) {
	getFlagManager().flag(UpdateFlag.ANIMATION);
	getFlagManager().setAnimation(a);
    }
    
    public void graphics(Graphic g) {
	getFlagManager().flag(UpdateFlag.GRAPHICS);
	getFlagManager().setGraphic(g);
    }

}
