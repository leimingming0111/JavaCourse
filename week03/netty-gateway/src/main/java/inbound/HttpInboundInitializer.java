package inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @program: netty-gateway
 * @Date: 2021/7/9 9:48
 * @Author: leimingming
 * @Description:
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    private final String proxyServer;
    HttpInboundInitializer(String proxyServer){
        this.proxyServer = proxyServer;
    }

    @Override
    protected void initChannel(SocketChannel serverChannel) throws Exception {
        ChannelPipeline pipeline = serverChannel.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024*1024));
        pipeline.addLast(new HttpInboundHandler(this.proxyServer));

    }
}
