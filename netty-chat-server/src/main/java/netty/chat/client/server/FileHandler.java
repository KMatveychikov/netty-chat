package netty.chat.client.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;

public class FileHandler extends SimpleChannelInboundHandler<File> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, File file) throws Exception {

    }
}
