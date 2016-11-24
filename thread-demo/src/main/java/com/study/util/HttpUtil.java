package com.study.util;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtil
{
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * httpClient 连接池管理器
	 */
	private static PoolingHttpClientConnectionManager connMgr;

	/**
	 * httpclient request 配置
	 */
	private static RequestConfig requestConfig;

	/*请求重试处理*/
	private static HttpRequestRetryHandler httpRequestRetryHandler;

	/**
	 * 类加载初始化连接池管理器和请求配置
	 */
	static{
		/*分别设置建立连接超时时间、从链接管理器请求一个连接的超时时间、数据交互的超时时间*/
		requestConfig = RequestConfig.custom().setConnectTimeout(3000)
				.setConnectionRequestTimeout(3000).setSocketTimeout(2000).build();

		httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int retryCount, HttpContext httpContext) {
				if (retryCount >= 5) {// 如果已经重试了5次，就放弃
					return false;
				}

				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}

				return false;
			}
		};

		/*初始化connectionMgr*/
		try {
			SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(null,new TrustStrategy() {
				/*信任所有*/
				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					return true;
				}
			}).build();
			ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", plainsf).register("https", sslsf).build();

			connMgr = new PoolingHttpClientConnectionManager(registry);
			connMgr.setMaxTotal(100);
			connMgr.setDefaultMaxPerRoute(20);
			connMgr.setValidateAfterInactivity(5000);
		} catch (KeyManagementException | NoSuchAlgorithmException
				| KeyStoreException e) {
			log.error("创建SSLContext出错",e);
		}

	}

	public static CloseableHttpClient getConnection(){
		return HttpClients.custom().setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).setRetryHandler(httpRequestRetryHandler).build();
	}

	/**
	 * 
	 * @param url 发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(String url)
	{
		CloseableHttpResponse httpResponse = null;
		try{
			// 创建HttpGet对象。
			HttpGet get = new HttpGet(url);
			CloseableHttpClient httpClient = getConnection();
			// 发送GET请求
			httpResponse = httpClient.execute(get);
			// 如果服务器成功地返回响应
			if (httpResponse.getStatusLine()
				.getStatusCode() == 200)
			{
				// 获取服务器响应字符串
				String result = EntityUtils
					.toString(httpResponse.getEntity());
				return result;
			}
		}catch(IOException e){
			log.error("请求连接:"+url+"  异常", e); 
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					log.error("关闭响应异常", e);
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param url 发送请求的URL
	 * @param rawParams 请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(String url, Map<String ,String> rawParams)
	{
		CloseableHttpResponse httpResponse = null;
		try {

			// 创建HttpPost对象。
			HttpPost post = new HttpPost(url);

			// 如果传递参数个数比较多的话可以对传递的参数进行封装
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : rawParams.entrySet()) {
				//封装请求参数
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			// 设置请求参数
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// 发送POST请求
			CloseableHttpClient httpClient = getConnection();
			httpResponse = httpClient.execute(post);
			// 如果服务器成功地返回响应
			if (httpResponse.getStatusLine()
					.getStatusCode() == 200) {
				//头部测试
//		System.out.println(httpResponse.getFirstHeader("status").getValue()+" 长度:"+httpResponse.getFirstHeader("status").getValue().length());	 
				// 获取服务器响应字符串
				String result = EntityUtils
						.toString(httpResponse.getEntity());
				return result;
			}
		} catch (IOException e) {
			log.error("请求连接:"+url+"  异常", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					log.error("关闭响应异常", e);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
 
 
		
		
		
		
		
	}
 
	
	
	
	
	
	
	
	
	
}
