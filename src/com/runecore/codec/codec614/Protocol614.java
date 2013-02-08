package com.runecore.codec.codec614;

import java.math.BigInteger;

import org.jboss.netty.channel.ChannelPipeline;

import com.runecore.cache.Cache;
import com.runecore.codec.ProtocolCodec;
import com.runecore.codec.codec614.net.EventHandler;
import com.runecore.codec.codec614.net.HandshakeDecoder;
import com.runecore.network.NetworkEncoder;

/**
 * Protocol614.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class Protocol614 implements ProtocolCodec {
    
    /**
     * The rsa modulus key.
     */
    public static final BigInteger MODULUS_KEY = new BigInteger(
	    "114506592844826837966011874920176588210516543861985385477233447440431136870205946513486065239750163784278104438280349487933836531711517266143772822150724949016979546670702568106083968152149257769592940638142105838419452069943331913623032451535902713511234253410639630045741534975082425016806370403493700588427");

    /**
     * The rsa private key.
     */
    public static final BigInteger PRIVATE_KEY = new BigInteger(
	    "44518790693595798272334445778203144294123343113102333368324888853352844461187535547303430769013445431182478616466779146933093593359620671396971657359971789296138486712131788137090106100242356655870066775209454345406715549037496133615532851495799549110654229626598058105052802323852689576319989196892279872513");

    /* (non-Javadoc)
     * @see com.runecore.codec.ProtocolCodec#setup(org.jboss.netty.channel.ChannelPipeline)
     */
    @Override
    public void setup(ChannelPipeline pipeline) {
	Cache.init();
	pipeline.addLast("decoder", new HandshakeDecoder());
	pipeline.addLast("encoder", new NetworkEncoder());
	pipeline.addLast("handler", new EventHandler());
    }

}