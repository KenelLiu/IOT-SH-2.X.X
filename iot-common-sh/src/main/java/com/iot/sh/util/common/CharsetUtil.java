package com.iot.sh.util.common;

import com.iot.sh.constants.ToolsConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * 
 * @author KenelLiu
 *
 */
@Slf4j
public class CharsetUtil{

  public static boolean isGBK(char c){
    Character ch = new Character(c);
    String sCh = ch.toString();
    try {
      byte[] bb = sCh.getBytes(ToolsConstants.Encoding.CHARSET_GBK);
      if (bb.length > 1)
        return true;
    }catch (UnsupportedEncodingException ex) {
      return false;
    }
    return false;
  }
  
  public static String encodeGBK(String str){
    try{
	      String strEncode = getEncoding(str);
	      return new String(str.getBytes(strEncode), ToolsConstants.Encoding.CHARSET_GBK);
     } catch (IOException ex){
    	 
     }
    return null;
  }

  public static String getEncoding(String str){
    String[] encodes = {ToolsConstants.Encoding.CHARSET_ISO8859,
    		ToolsConstants.Encoding.CHARSET_GBK, ToolsConstants.Encoding.CHARSET_UTF8};
    for(int i=0;i<encodes.length;i++){
    	try{
    	    if (str.equals(new String(str.getBytes(encodes[i]), encodes[i]))) {
    	          return encodes[i];
    	     }
    	}catch(Exception ex){
    		log.warn("unsupport encoding "+encodes[i]);
    	}
    }   
    return "";
  }
  
  
  public static byte[] getBytes (char[] chars){
	  return getBytes(chars, ToolsConstants.Encoding.CHARSET_UTF8);
  } 
  /**
   * 将字符转为字节(编码)
   * @param chars 字符数组
   * @param strCharset 指定字符
   * @return
   */
  public static byte[] getBytes (char[] chars,String strCharset){
		   Charset charset = Charset.forName (strCharset);
		   CharBuffer charBuf = CharBuffer.allocate (chars.length);
		   charBuf.put (chars);
		   charBuf.flip ();		   
		   return charset.encode (charBuf).array();
   }
 
  public static char[] getChars (byte[] bytes){
	  return getChars(bytes, ToolsConstants.Encoding.CHARSET_UTF8);
  }
  /**
   * 将字节转为字符(解码)
   * @param bytes 字节数组
   * @param strCharset 字符
   * @return
   */
  public static char[] getChars (byte[] bytes,String strCharset) {
		      Charset charset = Charset.forName (strCharset);
		      ByteBuffer byteBuf = ByteBuffer.allocate (bytes.length);
		      byteBuf.put (bytes);
		      byteBuf.flip ();		  
		   return charset.decode (byteBuf).array();
		}	
		
}

