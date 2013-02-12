package com.runecore.env.groovy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.customizers.ImportCustomizer;

import com.runecore.env.Context;

import groovy.util.GroovyScriptEngine;

/**
 * GroovyEngine.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class GroovyEngine {
    
    /**
     * The GroovyScriptEngine instance
     */
    private GroovyScriptEngine engine;
    
    /**
     * A map of loaded scripts
     */
    private final Map<String, Class<?>> scripts = new HashMap<String, Class<?>>();
    
    public GroovyEngine() {
	try {
	    engine = new GroovyScriptEngine(new String[] { "./data/scripts/", "./data/scripts/614/"});
	    ImportCustomizer imports = new ImportCustomizer();
	    imports.addImport("PlayerUpdateCodec", "com.runecore.codec.PlayerUpdateCodec");
	    imports.addImport("World", "com.runecore.env.world.World");
	    imports.addImport("LandscapeParser", "com.runecore.cache.format.LandscapeParser");
	    imports.addImport("RegionData", "com.runecore.util.RegionData");
	    imports.addImport("Location", "com.runecore.env.world.Location");
	    imports.addImport("Player", "com.runecore.env.model.player.Player");
	    imports.addImport("Skills", "com.runecore.env.model.player.Skills");
	    imports.addImport("GameSession", "com.runecore.network.GameSession");
	    imports.addImport("MessageBuilder","com.runecore.network.io.MessageBuilder");
	    imports.addImport("PacketType", "com.runecore.network.io.Message.PacketType");
	    imports.addImport("UpdateFlag", "com.runecore.env.model.flag.UpdateFlag");
	    imports.addImport("SettingType", "com.runecore.codec.event.SendSettingEvent.SettingType");
	    imports.addImport("SendMessageEvent", "com.runecore.codec.event.SendMessageEvent");
	    imports.addImport("SendInterfaceEvent", "com.runecore.codec.event.SendInterfaceEvent");
	    imports.addImport("SendWindowPaneEvent", "com.runecore.codec.event.SendWindowPaneEvent");
	    imports.addImport("RefreshLevelEvent", "com.runecore.codec.event.RefreshLevelEvent");
	    imports.addImport("SendSettingEvent", "com.runecore.codec.event.SendSettingEvent");
	    imports.addImport("SendAccessMaskEvent", "com.runecore.codec.event.SendAccessMaskEvent");
	    imports.addImport("ActionSender", "com.runecore.codec.ActionSender");
	    imports.addImport("GroovyScript", "com.runecore.env.groovy.GroovyScript");
	    imports.addImport("Context", "com.runecore.env.Context");
	    imports.addImport("NetworkContext", "com.runecore.network.NetworkContext");
	    engine.getConfig().addCompilationCustomizers(imports);
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Starts the Engine
     * @param context
     */
    public void init(Context context) throws Exception {
	for(File f : new File("./data/scripts/").listFiles()) {
	    if(f.isDirectory())
		continue;
	    String scriptName = f.getName().replaceAll(".groovy", "");
	    GroovyScript script = initScript(scriptName);
	    script.init(context);
	}
    }
    
    @SuppressWarnings("unchecked")
    /**
     * Loads a script from file/cache
     * @param scriptName The script name
     * @return The loaded script
     */
    public <S> Class<S> loadScript(String scriptName) {
	Class<S> clazz = (Class<S>) scripts.get(scriptName);
	if (clazz == null) {
	    try {
		clazz = engine.loadScriptByName(scriptName.replace('.', '/')+ ".groovy");
	    } catch (Exception e) {
		e.printStackTrace();
		return null;
	    }
	    scripts.put(scriptName, clazz);
	}
	return clazz;
    }
    
    /**
     * Starts a script
     * @param scriptName the script name
     * @param args The parameters
     * @return The script
     */
    public <S> S initScript(String scriptName, Object... args) {
	try {
	    Class<S> clazz = loadScript(scriptName);

	    Class<?> types[] = new Class[args.length];
	    for (int i = 0; i < args.length; i++) {
		types[i] = args[i].getClass();
	    }
	    return (S) clazz.getConstructor(types).newInstance(args);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

}