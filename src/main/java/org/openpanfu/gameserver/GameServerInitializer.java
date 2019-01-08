/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GameServerInitializer extends ChannelInitializer {
	private GameServer gameServer;

	public GameServerInitializer(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	@Override
	public void initChannel(Channel channel) {
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast("framer", new GameServerFrameDecoder(1000));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());
		pipeline.addLast(new GameServerHandler(this.gameServer));
	}
}
