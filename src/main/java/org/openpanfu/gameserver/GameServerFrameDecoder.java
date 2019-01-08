package org.openpanfu.gameserver;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class GameServerFrameDecoder extends DelimiterBasedFrameDecoder {
	public GameServerFrameDecoder(int maxFrameLength) {
		super(maxFrameLength, false, Unpooled.wrappedBuffer(new byte[] { '|' }),
				Unpooled.wrappedBuffer(new byte[] { '>' }));
	}
}
