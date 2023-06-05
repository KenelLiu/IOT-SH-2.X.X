package com.iot.sh.util.http;
import com.iot.sh.model.HttpResponse;
import com.iot.sh.constants.ToolsConstants;
public class HttpMainTest {

	public static void main(String[] args) {
		testSelfSSL();
	}

	private static  void testSelfSSL(){
		try {
			System.setProperty(ToolsConstants.HTTP.SelfKeyStoreFile, "E:/download/my.keystore,E:/download/my2.keystore");
			System.setProperty(ToolsConstants.HTTP.SelfKeyStorePassword, "123456,234567");
			System.setProperty(ToolsConstants.HTTP.SelfIgnoreVerifyHost, "10.83.66.74,192.168.1.103");
			HttpResponse res=HttpUtil.get("https://10.83.66.74:3306");
			System.out.println(res.getResponseText());

			res=HttpUtil.get("https://www.baidu.com");
			System.out.println(res.getResponseText());

			res=HttpUtil.get("https://192.168.1.103");
			System.out.println(res.getResponseText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
