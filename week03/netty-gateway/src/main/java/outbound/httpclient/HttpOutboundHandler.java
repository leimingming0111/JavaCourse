package outbound.httpclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @program: netty-gateway
 * @Date: 2021/7/9 10:05
 * @Author: leimingming
 * @Description:
 */
public class HttpOutboundHandler {
    private String backendUrl;
    private ExecutorService service;
    private CloseableHttpAsyncClient httpClient;

    public HttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;

        int cores = Runtime.getRuntime().availableProcessors()*2;

        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        service = new ThreadPoolExecutor(cores,cores,1000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(2048),
                new NamedThreadFactory("proxyService"),handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpClient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response,context) -> 6000)
                .build();
        httpClient.start();
    }

    public void handler(final ChannelHandlerContext ctx, final FullHttpRequest request) {
        final String url = backendUrl + request.uri();

        service.submit(() -> fetchGet(request,ctx,url));

    }

    private void fetchGet(final FullHttpRequest request,final ChannelHandlerContext ctx,final String url){

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);

        httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                handlerResponse(request,result,ctx);
            }
            @Override
            public void failed(Exception ex) {
                httpGet.abort();
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    private void handlerResponse(FullHttpRequest request,HttpResponse endpointResponse,ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try{
            byte[] bytes = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
            response.headers().set("Content-Type","application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));


        }catch (Exception e) {
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        }finally {
            if (request != null) {
                if (!HttpUtil.isKeepAlive(request) ){
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
