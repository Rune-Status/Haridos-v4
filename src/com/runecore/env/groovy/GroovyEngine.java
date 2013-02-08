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
    
    /**
     * Starts the Engine
     * @param context
     */
    public void init(Context context) throws Exception {
	engine = new GroovyScriptEngine(new String[] { "./data/scripts/" });
	ImportCustomizer imports = new ImportCustomizer();
	imports.addImport("GroovyScript", "com.runecore.env.groovy.GroovyScript");
	imports.addImport("Context", "com.runecore.env.Context");
	imports.addImport("NetworkContext", "com.runecore.network.NetworkContext");
	engine.getConfig().addCompilationCustomizers(imports);
	for(File f : new File("./data/scripts/").listFiles()) {
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