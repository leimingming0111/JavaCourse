package inbound;

import filter.HttpRequestFilterTest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import outbound.httpclient.HttpOutboundHandler;

/**
 * @program: netty-gateway
 * @Date: 2021/7/9 10:00
 * @Author: leimingming
 * @Description:
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private final HttpOutboundHandler handler;

    HttpInboundHandler(String proxyServer){
        this.handler = new HttpOutboundHandler(proxyServer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;

        new HttpRequestFilterTest().filter(request,ctx);

        handler.handler(ctx,request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
