package com.iot.sh.util.file;

import com.iot.sh.constants.ToolsConstants;

import java.io.*;

public class StreamUtil {
	private StreamUtil(){}

	public static byte[] readStream(InputStream in) throws IOException{
		return readStream(in,true);
	}

	public static byte[] readStream(InputStream in,boolean bClose) throws IOException{
		try{
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			byte[] buf = new byte[ToolsConstants.Byte.defaultSize];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			return out.toByteArray();
		}catch(Exception ex){
			throw new IOException(ex.getMessage(), ex);
		}finally{
			if(bClose){
				if(in!=null){try{ in.close();}catch(Exception ex){}}
			}
		}
	}

	public static String read(InputStream is) throws IOException {
		return read(is,true);
	}
	public static String read(InputStream is,boolean bClose) throws IOException{
		StringBuilder  mulLine= new StringBuilder(100);
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			while ((line = in.readLine()) != null){
				mulLine.append(line);
			}
			return mulLine.toString();
		}catch(Exception ex){
			throw new IOException(ex);
		}finally {
			if(bClose){
				if (in != null) {try{in.close();}catch(Exception ex){}}
			}
		}
	}
}
