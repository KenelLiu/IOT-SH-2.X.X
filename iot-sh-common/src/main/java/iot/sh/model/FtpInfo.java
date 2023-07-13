package iot.sh.model;

import lombok.Data;

/**
 * ftp认证信息
 * @author Kenel Liu
 */
@Data
public class FtpInfo {
    private String RSAFile;
    private String passphrase;
    private String user;
    private String password;
    private String host;
    private int port;

    public FtpInfo(){
    }

    public FtpInfo(String host,int port){
        this(null,null,host,port);
    }

    public FtpInfo(String user,String password,String host,int port){
        this(null,null,user,password,host,port);
    }

    public FtpInfo(String RSAFile,String  passphrase,String user,String password,String host,int port){
        this.RSAFile=RSAFile;
        this.passphrase=passphrase;
        this.user=user;
        this.password=password;
        this.host=host;
        this.port=port;
    }
}
