package com.wuyuzegang.sender;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class SinaSender {
	public static final  String sinaUrl = "http://hq.sinajs.cn/list=";
	
	
	public String send(String param) {
		String url = sinaUrl+param;
		String response ="";
		int connect_timeout = 30000;
		int no_connect_timeout = 30000;
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		System.out.println("sender url:>>>>>>>>>>>>>>>>>>>>>>>>>"+url);

		// 设置编码
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(connect_timeout );
		// 链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(no_connect_timeout);
		// 禁止自动重发
		httpClient.getParams()
				.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));

		try {
			Thread.sleep(1000);
			int stateCode = httpClient.executeMethod(postMethod);
			if (stateCode != HttpStatus.SC_OK) {
				System.out.println("请求异常");
			}
			// 获取返回信息
			byte[] responseBody = postMethod.getResponseBody();
			response = new String(responseBody);
//			response = param+","+response;
			System.out.println("新浪返回报文为："+response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 最后一定释放连接
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return response;
	}

}
