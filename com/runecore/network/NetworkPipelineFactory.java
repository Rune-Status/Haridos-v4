package com.runecore.network;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

/**
 * NetworkPipelineFactory.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class NetworkPipelineFactory implements ChannelPipelineFactory {
    
    /**
     * The ChannelPipeline to use in the factory
     */
    private final ChannelPipeline channelPipeline;
    
    /**
     * Construct the factory
     * @param pipeline The pipeline to wrap
     */
    public NetworkPipelineFactory(ChannelPipeline pipeline) {
	this.channelPipeline = pipeline;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
	return channelPipeline;
    }

}