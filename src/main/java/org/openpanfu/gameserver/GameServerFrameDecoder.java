package org.openpanfu.gameserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class GameServerFrameDecoder extends DelimiterBasedFrameDecoder {
    public GameServerFrameDecoder(int maxFrameLength) {
        super(maxFrameLength, false, Unpooled.wrappedBuffer(new byte[] { '|'}),
                Unpooled.wrappedBuffer(new byte[] { '>'}));
    }
}
