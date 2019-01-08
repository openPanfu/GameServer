/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.openpanfu.gameserver.util.Logger;

import java.util.Arrays;
import java.util.List;

public class GameServerHandler extends SimpleChannelInboundHandler<String> {
	private GameServer gameServer;

	public GameServerHandler(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		Logger.info("Connection from: " + ctx.channel().remoteAddress());
		ctx.channel().attr(AttributeKey.valueOf("user")).set(new User(ctx.channel(), this.gameServer));
	}

	@Override
	public void channelInactive(final ChannelHandlerContext ctx) {
		User user = (User) ctx.channel().attr(AttributeKey.valueOf("user")).get();
		user.onDisconnect();
	}

	@Override
	public void channelRead0(ChannelHandlerContext channelHandlerContext, String message) {
		// Useless characters, they only make interpretation harder.
		// You can't use them in things like chat anyway, so it's safe to ignore them.
		message = message.replace("\r", "");
		message = message.replace("\n", "");
		Logger.debug("} " + message);
		User user = (User) channelHandlerContext.channel().attr(AttributeKey.valueOf("user")).get();
		if (message.startsWith("<")) {
			String policy = "<?xml version=\"1.0\"?>\r\n<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n<cross-domain-policy>\r\n   <allow-access-from domain=\"*\" to-ports=\"1-65535\" />\r\n</cross-domain-policy>\0";
			user.getChannel().writeAndFlush(policy);
			user.getChannel().close();
			return;
		}
		if (message.contains(String.valueOf('|'))) {
			String[] packets = message.split("\\|");
			for (String packet : packets) {
				try {
					String[] split = packet.split(";");
					String[] arguments = Arrays.copyOfRange(split, 1, split.length);
					PanfuPacket panfuPacket = new PanfuPacket(Integer.valueOf(split[0]));
					panfuPacket.passParameters(arguments);
					user.handlePacket(panfuPacket);
				} catch (Exception e) {
					e.printStackTrace();
					user.getChannel().close();
				}
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
