import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @program: httpClientDemo
 * @Date: 2021/7/3 18:42
 * @Author: leimingming
 * @Description:
 */
public class HttpClientDemo {
    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String uri = "http://localhost:8081";
        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            StatusLine statusLine = response.getStatusLine();
            System.out.println("响应状态码:"+statusLine.getStatusCode());;

            System.out.println("响应内容:"+ EntityUtils.toString(entity));
        }catch (Exception e){
            e.printStackTrace();
        } finally {

            try {
                if (httpClient !=null) {
                    httpClient.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }

            try {
                if (response != null) {
                    response.close();
                }
            }catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
