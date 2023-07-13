package iot.sh.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileHttpResponse extends HttpResponse {

	public FileHttpResponse() {
		
	}	
	private static final long serialVersionUID = -6353099618301387046L;
	private Map<String, File> mapFile=new HashMap<String, File>();

	public void addFile(String strField,File fileValue){
		mapFile.put(strField, fileValue);
	}
	public File getFile(String strField){
		return mapFile.get(strField);
	}
	public Map<String, File> mapFile(){
		return mapFile;
	}
}
