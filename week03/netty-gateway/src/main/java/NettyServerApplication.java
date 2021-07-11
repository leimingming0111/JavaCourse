import inbound.HttpInboundServer;

/**
 * @program: netty-gateway
 * @Date: 2021/7/9 9:36
 * @Author: leimingming
 * @Description:
 */
public class NettyServerApplication {
    public static void main(String[] args) {
        String proxyServer = System.getProperty("proxyServer", "http://localhost:8088");
        String proxyPort = System.getProperty("proxyPort", "8888");

        int port = Integer.parseInt(proxyPort);

        HttpInboundServer httpInboundServer = new HttpInboundServer(port,proxyServer);

        httpInboundServer.run();

    }
}
