package com.runecore.network;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import com.runecore.codec.ProtocolCodec;

/**
 * NetworkPipelineFactory.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class NetworkPipelineFactory implements ChannelPipelineFactory {
    
    /**
     * The ChannelPipeline to use in the factory
     */
    private final ProtocolCodec protocolCodec;
    
    /**
     * Construct the factory
     * @param pipeline The pipeline to wrap
     */
    public NetworkPipelineFactory(ProtocolCodec codec) {
	this.protocolCodec = codec;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
	ChannelPipeline pipe = Channels.pipeline();
	protocolCodec.setup(pipe);
	return pipe;
    }

}