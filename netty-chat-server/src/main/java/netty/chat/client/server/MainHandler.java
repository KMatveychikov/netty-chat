package netty.chat.client.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static final List<Channel> channels = new ArrayList<>();
    private String clientName;
    private static int newClientIndex = 1;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился: " + ctx);
        channels.add(ctx.channel());
        clientName = "Клиент №" + newClientIndex;
        newClientIndex++;
        broadCastMessage(clientName," подключился");
    }

public void broadCastMessage(String clientName, String message){
    String out = String.format("[%s]: %s\n", clientName, message );
    System.out.println(out);
    for (Channel c : channels){
        c.writeAndFlush(out);
    }
}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
       if (s.startsWith("/")){
           if(s.startsWith("/name")){
               clientName = s.split("\\s",2)[1];
           }
           return;
       }
       broadCastMessage(clientName, s);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        broadCastMessage("srvr: ",clientName+" отключился");
        channels.remove(ctx.channel());
        ctx.close();
    }
}
