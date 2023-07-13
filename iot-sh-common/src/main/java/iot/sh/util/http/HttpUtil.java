package iot.sh.util.http;

import iot.sh.constants.ToolsConstants;
import iot.sh.model.HttpOutStream;
import iot.sh.model.HttpResponse;
import iot.sh.exception.HttpResponseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author KenelLiu
 *
 */
@Slf4j
public class HttpUtil {	

    private HttpUtil() {
		
	}

	public static HttpClient getHttpClient() {
		HttpPool httpPool=new HttpPool45();
		return httpPool.getHttpClient();
	}	

	/**
	 * 
	 * @param url 
	 * @return  Response responseText  返回文本
	 */
	public static HttpResponse post(String url) throws HttpResponseException {
		return post(url,null);
	}
	/**
	 * 
	 * @param url
	 * @param params  普通参数格式<name,value>
	 * @return Response responseText  返回文本
	 */
	public static HttpResponse post(String url,Map<String, String> params){
		return post(url,params,null);
	}
	/**
	 * 
	 * @param url
	 * @param params  普通参数格式<name,value>
	 * @param header  头部参数格式<name,value>
	 * @returnResponse responseText  返回文本
	 * @throws HttpResponseException
	 */
	public static HttpResponse post(String url,Map<String, String> params,Map<String, String> header){
		return post(url, params, header,null);
	} 
	/**
	 * 
	 * @param url
	 * @param paramsFile  文件参数格式<name,file>
	 * @return Response responseText  返回文本
	 * @throws HttpResponseException
	 */
	public static HttpResponse postFile(String url,Map<String, File> paramsFile){
		return postFile(url,paramsFile,null);
	}
	/**
	 * 
	 * @param url
	 * @param paramsFile  文件参数格式<name,file>
	 * @param params  普通参数格式<name,value>
	 * @return Response responseText  返回文本
	 */
	public static HttpResponse postFile(String url,Map<String, File> paramsFile,Map<String, String> params){
		return postFile(url,paramsFile,params,null);
	}
	/**
	 * 
	 * @param url
	 * @param paramsFile 文件参数格式<name,file>
	 * @param params   普通参数格式<name,value>
	 * @param header 头部参数格式<name,value>
	 * @return Response responseText  返回文本
	 * @throws HttpResponseException
	 */
	public static HttpResponse postFile(String url,Map<String, File> paramsFile,Map<String, String> params,Map<String, String> header){
		return post(url,params,header,paramsFile);
	}
	/**
	 * 
	 * @param url
	 * @param nameValuePair 普通参数
	 * @param header head参数
	 * @param paramsFile 文件参数
	 * @return Response responseText  返回文本
	 * @throws HttpResponseException
	 */
	private static HttpResponse post(String url,Map<String, String> nameValuePair,Map<String, String> header,Map<String, File> paramsFile){
		iot.sh.util.http.HttpClient client=new iot.sh.util.http.HttpClient(url);
		client.setHeaders(header);
		client.setNameValuePair(nameValuePair);
		client.setUploadFiles(paramsFile);
		return client.execute();	
	}
	
	/**
	 * 
	 * @param url
	 * @param httpMethod  
	 * @param nameValuePair
	 * @param header
	 * @return
	 * @throws HttpResponseException
	 */
	public static HttpResponse doMethod(String url,String httpMethod,Map<String, String> nameValuePair,Map<String, String> header) throws HttpResponseException{
		iot.sh.util.http.HttpClient client=new iot.sh.util.http.HttpClient(url);
		client.setHeaders(header);
		client.setNameValuePair(nameValuePair);
		client.setMethod(httpMethod);
		return client.execute();	
	}
	
	//-------------------普通获取get --------------------//
	/**
	 * 
	 * @param url
	 * @return Response responseText  返回文本
	 */
	public static HttpResponse get(String url){
		 return get(url,null); 
	}
	/**
	 * 
	 * @param url
	 * @param header
	 * @return Response responseText  返回文本
	 */
	public static HttpResponse get(String url,Map<String, String> header){
		return get(url,header,null);
	}
	/**
	 *
	 * @param url
	 * @param header
	 * @return Response responseText  返回文本
	 */
	public static HttpResponse get(String url, Map<String, String> header, HttpOutStream httpOutStream){
		iot.sh.util.http.HttpClient client=new iot.sh.util.http.HttpClient(url);
		client.setMethod(iot.sh.util.http.HttpClient.METHOD_GET);
		client.setHeaders(header);
		client.setHttpOutStream(httpOutStream);
		return client.execute();
	}
	//--------------------post body-----------------------------//
	/**
	 * @param url
	 * @param body
	 * @return  Response responseText  返回文本
	 */
	public static HttpResponse postBody(String url,String body) throws HttpResponseException{
		return postBody(url,body,null);
	}
	/**
	 *
	 * @param url
	 * @param body
	 * @param header
	 * @return  Response responseText  返回文本
	 * @throws HttpResponseException
	 */
	public static HttpResponse postBody(String url,String body,Map<String, String> header) throws HttpResponseException{
		HttpClient client=null;
		HttpPost request=null;
		HttpEntity entity=null;
		try {
			// 创建POST请求
			request = new HttpPost(url);
			if(header!=null){
				Set<String> headerKeys = header.keySet();
				for (String headKey : headerKeys) {
					request.addHeader(headKey, header.get(headKey));
				}
			}
			if(body!=null && body.trim().length()>0){
				entity = new StringEntity(body, ToolsConstants.Encoding.CHARSET_UTF8);
				request.setEntity(entity);
			}
			// 发送请求
			client = getHttpClient();
			org.apache.http.HttpResponse res = client.execute(request);
			HttpResponse response=new HttpResponse();
			response.setStatusCode(res.getStatusLine().getStatusCode());
			response.setAllHeaders(res.getAllHeaders());
			response.setResponseBytes(res.getEntity()==null?null:EntityUtils.toByteArray(res.getEntity()));
			return response;
		} catch (Exception e) {
			throw new HttpResponseException(e.getMessage(),e);
		}finally{
			if(request!=null){try{request.releaseConnection();}catch(Exception ex){}}
		}
	}
	//-------------------- body-----------------------------//
	/**
	 * 
	 * @param url
	 * @param body
	 * @return  Response responseText  返回文本
	 */
	public static HttpResponse doMethodBody(String url,String body,String httpMehtod){
		return doMethodBody(url,body,httpMehtod,null);
	}
	/**
	 * 
	 * @param url
	 * @param body
	 * @param header
	 * @return  Response responseText  返回文本
	 */
	public static HttpResponse doMethodBody(String url,String body,String httpMehtod,Map<String, String> header){
		iot.sh.util.http.HttpClient client=new iot.sh.util.http.HttpClient(url);
		client.setHeaders(header);
		client.setTransMode(iot.sh.util.http.HttpClient.TRANSMODE_BODY);
		client.setBody(body);
		client.setMethod(httpMehtod);
		return client.execute();  	
	}
	
}