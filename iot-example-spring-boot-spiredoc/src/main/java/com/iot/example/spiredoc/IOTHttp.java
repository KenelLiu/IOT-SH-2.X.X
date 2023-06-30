package com.iot.example.spiredoc;

import com.iot.sh.util.http.HttpUtil;
import com.iot.sh.model.HttpResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kenel Liu
 */
public class IOTHttp {

    public static void main(String[] args){
        //uploadFile();
        signPdf();
    }
    public  static void  uploadFile(){
        try{
            String url="http://10.27.122.36:8002/assess/general/file/add";
            Map<String,String> header=new HashMap<>();
            header.put("assess_access_token", "5A2AB8335871171CEB9C17B995A0EB8823EC0A4D8850C2C1964B8A4BAD29");
            Map<String,String> params=new HashMap<>();
            params.put("type", "sign");
            Map<String, File> paramsFile=new HashMap<>();
            paramsFile.put("file", new File("E:\\2022.png"));
            HttpResponse respone= HttpUtil.postFile(url, paramsFile, params, header);
            System.out.println(respone);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public  static void  signPdf(){
        try{
            String url="http://test.yzpt.sheca.com:9082/sync/create/ukey";
            long timestamp=System.currentTimeMillis();
            String accesstoken="Bl4UNjFYq7";
            String accessSecret="DRAWAGtejwvmpfb8qOKZUZcGTjYylD";
            String value=accesstoken+accessSecret+timestamp;
            String signature=null;//MD5.encode(value).toLowerCase();

            timestamp=1688023229523L;
            signature="21a5b4eb6099eb58f5d9a40e9093651f";
            accesstoken="Bl4UNjFYq7";
            Map<String,String> header=new HashMap<>();
            header.put("x-qys-timestamp", timestamp+"");
            header.put("x-qys-signature", signature);
            header.put("x-qys-accesstoken", accesstoken);
            //header.put("Content-Type", "multipart/form-data;charset=UTF-8");

            Map<String,String> params=new HashMap<>();
            params.put("title", "闵行区政务外网用户访问互联网审核表");
            params.put("stamperStr", "[{\"offsetX\":0,\"offsetY\":-0.05,\"keyIndex\":1,\"keyword\":\"单位（公章）\"}]");

            Map<String,File> paramsFile=new HashMap<>();
            paramsFile.put("file", new File("D:\\app\\file\\activiti\\netManagePdf\\WL22-0824-013.pdf"));

            HttpResponse respone=HttpUtil.postFile(url, paramsFile, params, header);
            System.out.println(respone);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
