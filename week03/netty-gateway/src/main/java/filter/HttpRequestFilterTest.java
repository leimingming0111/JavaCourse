package filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @program: netty-gateway
 * @Date: 2021/7/9 13:39
 * @Author: leimingming
 * @Description:
 */
public class HttpRequestFilterTest implements HttpRequestFilter{
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        fullRequest.headers().set("token","leimingming");
    }
}
