package com.iot.sh.util.http;

import com.iot.sh.model.FileHttpResponse;
import com.iot.sh.constants.ToolsConstants;
import com.iot.sh.exception.HttpException;
import com.iot.sh.model.HttpOutStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
/**
 * 处理apache http 请求及响应
 * @author KenelLiu
 */
@Slf4j
public class HttpClient {
    public static final ContentType TEXT_PLAIN_UTF8 =ContentType.create("text/plain", Charset.forName(ToolsConstants.Encoding.CHARSET_UTF8));
	//----------传输类型---------//
	public static final int TRANSMODE_BODY = 1;//整体传输  例如SOAP xml文件 设置在body里传输
	public static final int TRANSMODE_FORM = 2;//普通form传输  包含文件传输
	//--------方法-------------//
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_DELETE = "DELETE";
	
	private org.apache.http.client.HttpClient httpClient;
	private HttpRequestBase httpRequest;
	private Map<String, String> headers;
	private Map<String, File>  uploadFiles;
	private int transMode;
	private String url;
	private String method=METHOD_POST;
	private HttpOutStream httpOutStream;

	private List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

	private String body = null;

	private HttpHost httpHost = null;

	public HttpClient() {
		this(null,METHOD_POST);
	}

	public HttpClient(String url) {
		this(url,METHOD_POST);
	}
	
	public HttpClient(String url,String method) {
		this(url,method,TRANSMODE_FORM);
	}
	
	public HttpClient(String url, String method,int transMode) {
		this(url,method,transMode,null);	
	}

	public HttpClient(String url, String method,int transMode,
			Map<String, String> headers) {
		this.url = url;
		this.transMode = transMode;
		this.method = method;
		this.headers = headers;
	}

	public HttpClient setHttpOutStream(HttpOutStream httpOutStream){
		this.httpOutStream=httpOutStream;
		return this;
	}

	public HttpClient setUrl(String url) {
		this.url = url;
		return this;
	}

	public HttpClient setTransMode(int transMode) {
		this.transMode = transMode;
		return this;
	}

	public HttpClient setBody(String body) {
		this.body = body;
		return this;
	}

	public HttpClient setMethod(String method) {
		this.method = method;
		return this;
	}

	public HttpClient setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public HttpClient setUploadFiles(Map<String, File> uploadFiles) {
		this.uploadFiles = uploadFiles;
		return this;
	}	
	
	public HttpClient setNameValuePair(Map<String, String> param) {
		if (param == null)
			return this;
		for (Entry<String,String> entry : param.entrySet()) {
			if ((entry.getKey()  == null) || (entry.getKey().trim().equals("")))
				continue;
			this.paramsList.add(new BasicNameValuePair(entry.getKey().trim(), entry.getValue()));
		}
		return this;
	}

	public FileHttpResponse execute(){
		HTTPResponse handler=new HTTPResponse();
		if(this.httpOutStream!=null){
			handler.setOutputStream(this.httpOutStream.getOutputStream());
			handler.setAutoCloseStream(this.httpOutStream.isAutoCloseStream());
		}
		return handleResponse(handler);
	}
	
	/**
	 * 执行Http Request,并得到响应
	 * @param handler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected  <T>T handleResponse(ResponseHandler<T> handler) {
		Object response = null;
		try {
			setRequestParam();	
			this.httpClient = HttpUtil.getHttpClient();	
			if (this.httpHost != null)
				response = this.httpClient.execute(this.httpHost,this.httpRequest, handler);
			else 
				response = this.httpClient.execute(this.httpRequest, handler);
			
			return (T)response;
		} catch (Exception e) {			
			if(httpRequest!=null){try{httpRequest.abort();}catch(Exception em){}}
			log.error(e.getMessage(), e);
			throw new HttpException("httpClient send request error:"+ e.getMessage());
		}finally{			
			//释放资源
			if(this.httpRequest!=null){try{this.httpRequest.releaseConnection();}catch(Exception ex){}}
		}
	}
	/**
	 * 设置请求参数
	 */
	private void setRequestParam() {
		HttpEntity  entity = null;		
		try {
			if (this.url == null || this.url.trim().length()==0) {
				throw new HttpException("http url is null");
			}				
			if (this.transMode ==TRANSMODE_BODY) {
				//作为body传输
				if (this.body!= null) {
					entity=new StringEntity(this.body, ToolsConstants.Encoding.CHARSET_UTF8);
				}
			} else {
				if(this.uploadFiles!=null){
					//有文件传输    必须使用  MultipartEntity		 							
					entity=entity==null?new MultipartEntity():entity;
					if(this.uploadFiles!=null){
						  for (Entry<String,File> entry : this.uploadFiles.entrySet()) {
								if ((entry.getKey()  == null) || (entry.getKey().trim().equals("")))
									continue;		
								((MultipartEntity)entity).addPart(entry.getKey(),  new FileBody(entry.getValue()));
							}
					  }								
					//设置普通一般参数 
					for(NameValuePair pair:this.paramsList){	
							((MultipartEntity)entity).addPart(pair.getName(), new StringBody(pair.getValue(),Charset.forName(ToolsConstants.Encoding.CHARSET_UTF8)));
					}
				}else{
					//普通请求
					entity = new UrlEncodedFormEntity(paramsList,Charset.forName(ToolsConstants.Encoding.CHARSET_UTF8));
				}								
				/**4.5
				 * 
				 *  MultipartEntityBuilder reqEntity=MultipartEntityBuilder.create();				 * 
				 * 	reqEntity.addBinaryBody(entry.getKey(),  entry.getValue());		
				 * 	reqEntity.addTextBody(pair.getName(),pair.getValue(),TEXT_PLAIN_UTF8);
				 *  entity=reqEntity.build();
				 */
			}
			if (this.method.equals(METHOD_PUT)) {
				this.httpRequest = new HttpPut(this.url);
				((HttpEntityEnclosingRequestBase) this.httpRequest).setEntity(entity);
			} else if (this.method.equals(METHOD_GET)) {
				this.httpRequest = new HttpGet(this.url);
			} else if (this.method.equals(METHOD_DELETE)) {
				this.httpRequest = new HttpDelete(this.url);
			} else {
				this.httpRequest = new HttpPost(this.url);
				((HttpPost)this.httpRequest).setEntity(entity);			
			}
			if (this.headers != null) {
				for (Entry<String,String> entry : this.headers.entrySet()) {
					if ((entry.getKey()  == null) || (entry.getKey().trim().equals("")))
						continue;
					this.httpRequest.setHeader(entry.getKey().trim(), entry.getValue());
				}			
			}		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new HttpException("http 参数发生异常:"+e.getMessage(),e);
		}
	}

	public void setProxy(String url, int port) {
		this.httpHost = new HttpHost(url, port);
	}

	private class HTTPResponse implements ResponseHandler<FileHttpResponse>{
		private OutputStream outStream;
		private boolean autoCloseStream=true;
		public HTTPResponse(){
			
		}
		public void setOutputStream(OutputStream outStream){
			this.outStream=outStream;
		}
		public void setAutoCloseStream(boolean autoCloseStream){
			this.autoCloseStream=autoCloseStream;
		}
		
		public FileHttpResponse handleResponse(HttpResponse response)
				throws IOException {
			FileHttpResponse fileHttpResponse=new FileHttpResponse();
			fileHttpResponse.setAllHeaders(response.getAllHeaders());
			fileHttpResponse.setStatusCode(response.getStatusLine().getStatusCode());
			try{
				//=========判断响应是否为下载文件请求==========//
				Header header = response.getFirstHeader("Content-Disposition");
				if(header==null){
					//普通响应数据,非文件下载
					fileHttpResponse.setResponseBytes(response.getEntity()==null?null:EntityUtils.toByteArray(response.getEntity()));
				}else{
					//文件输出
					HttpEntity resEntity = response.getEntity();
					if(resEntity==null){//body 为 empty
						log.warn("download http entity is empty ....");
						return fileHttpResponse;
					}
					download(header, response, fileHttpResponse);
				}
			  return fileHttpResponse;
			}catch(Exception ex){
		    		 throw new IOException(ex.getMessage(),ex);
		   }
	 }

		//--------------下载到文件，临时存储的位置-------------------//
		private String getTmpPath(){
	   		 String tmpDirPath=System.getProperty("java.io.tmpdir");			            
		     File tmpDir= new File(tmpDirPath); 
		     if(!tmpDir.exists() || !tmpDir.isDirectory())  
		           tmpDir.mkdirs();  
		     return tmpDirPath;
		}
		
		public void download(Header header, HttpResponse response, FileHttpResponse cdoHttpResponse) throws IOException{
			 InputStream inStream=null;
			 try{						 
				 inStream = response.getEntity().getContent();	
				 HeaderElement[] values = header.getElements();
				 for(int i=0;i<values.length;i++){
					 NameValuePair param = values[i].getParameterByName("filename");
					 if(param!=null){
							   try {
									String fileName = param.getValue();
									if(this.outStream==null){
										//输出到临时文件
										outFile(inStream, fileName,cdoHttpResponse);
									}else{
										try{
											//输出到指定流
											outStream(inStream, this.outStream);
										}finally{
											if(this.autoCloseStream){
												try{if(outStream!=null)outStream.close();}catch(Exception ex){};
											}
										}
									}
								break;
						   } catch (Exception e) {
							log.error(e.getMessage(), e);
						 }
					 }
   				 }
			 }catch(Exception ex){
				 log.error(ex.getMessage(),ex);
				 throw new IOException(ex.getMessage(),ex);
			 }finally{
				 if(inStream!=null)try{inStream.close();}catch(Exception e){}
			 }			
		}
		//--------------------下载文件流完成后，把文件句柄返回给fileHTTPResponse-------------//
		private void outFile(InputStream inStream, String fileName, FileHttpResponse fileHTTPResponse) throws IOException{
			  String tmpDirPath=getTmpPath();
			  FileOutputStream fileout=null;
			  try{	   				
	   			  String suffix=fileName.substring(fileName.lastIndexOf("."),fileName.length());
	   			  String date=new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date());
	   		      String tempPath =tmpDirPath+"/"+fileName.substring(0,fileName.lastIndexOf("."))+"_"+date+ suffix;				    				 
	   			  File tmpFile=new File(tempPath);
	   			  if(!tmpFile.exists())
	   			    	tmpFile.createNewFile();
	   			    
	   			 fileout= new FileOutputStream(tmpFile);  
	   			 outStream(inStream, fileout);
				 fileHTTPResponse.addFile(fileName, tmpFile);
	         } catch (Exception e) {  
	        	throw new IOException(e.getMessage(),e);
	         }finally{
	        	 if(fileout!=null)try{fileout.close();}catch(Exception ex){}        	 
	        } 	  
		}
		//-------------------输出文件流-----------------------//
		private void outStream(InputStream inStream,OutputStream outputStream) throws IOException{
			  try{	   				 
	             byte[] buffer=new byte[ToolsConstants.Byte.maxSize*2];
	             int ch = 0;  
	             while ((ch = inStream.read(buffer)) != -1) {  
	            	 outputStream.write(buffer,0,ch);  
	             }  	            
	             outputStream.flush(); 		         
	         } catch (Exception e) {  
	        	throw new IOException(e.getMessage(),e);
	         }	  
		}
	}
}