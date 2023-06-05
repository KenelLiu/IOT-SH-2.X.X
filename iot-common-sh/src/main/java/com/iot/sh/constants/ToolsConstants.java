package com.iot.sh.constants;
/**
 * 
 * @author KenelLiu
 *
 */
public class ToolsConstants {

	public static class HTTP{
		public static final String SocketTimeout_MS="http.socketTimeout";
		public static final String ConnectionTimeout_MS="http.connectTimeout";	
		public static final String RequestTimeout_MS="http.requestTimeout";
		public static final String DefaultMaxPerRoute="http.defaultMaxPerRoute";
		public static final String MaxTotal="http.maxTotal";
		public static final String RetryTime="http.retryTime";
		public static final String Version="http.version";
		
		public static final String Max_Connection_Lifetime_MS="http.maxConnectionLifeTime";
		public static final String IdleConnection_TimeOut_MS="http.IdleConnectionTimeout";	
		
		public static final String SelfKeyStoreFile="http.selfKeystoreFile";//自定义证书的keystore,可以是多个,由分隔符【SelfSplitChar】来分割,若没有,则表示只有一个
		public static final String SelfKeyStorePassword="http.selfKeystorePassword";//自定义证书的keystore的password，可以是多个,由分隔符【SelfSplitChar】来分割,若没有,则表示只有一个
		public static final String SelfIgnoreVerifyHost="http.selfIgnoreVerifyHost";//自定义证书的所在服务器域名或ip,可以是多个,由分隔符【SelfSplitChar】来分割,若没有,则表示只有一个
		public static final String SelfSplitChar="http.selfSplitChar";//自定义多个证书的分隔符
	}

	//--字符编码变量-------//
	public static class Encoding {
		public static final String CHARSET_UTF8="UTF-8";
		public static final String CHARSET_GBK="GBK";
		public static final String CHARSET_ISO8859="ISO-8859-1";
	}
	//byteBuffer读取字节长度
	public static class Byte{
		public final static int maxSize=2048;
		public final static int defaultSize=1024;
		public final static int minSize=512;
	}
}
