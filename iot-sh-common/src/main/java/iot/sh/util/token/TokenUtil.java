package iot.sh.util.token;

import iot.sh.exception.EncryptException;

import java.util.concurrent.TimeoutException;

public class TokenUtil {

    /**
     *
     * @param userId  用户Id
     * @param expireTime 有效时间,单位：秒
     * @return
     * @throws EncryptException
     */
    public static String genEncryptToken(String userId,long expireTime) throws EncryptException {
        long endTime=System.currentTimeMillis()/1000+ expireTime;
        return genEncryptTokenEndTime(userId,endTime);
    }

    /**
     *
     * @param userId 用户Id
     * @param endTime 结束时间,转换成秒
     * @return
     * @throws EncryptException
     */
    public static String genEncryptTokenEndTime(String userId,long endTime) throws EncryptException{
        userId=userId+";"+endTime;
        String token= SupportToken.genRSAEncrypt(userId);
        return token;
    }
    /**
     * @param token  为 终端客服传过来的密文
     * @return
     * @throws EncryptException
     * @throws TimeoutException
     */
    public static String genDecryptToken(String token) throws EncryptException, TimeoutException{
        long endTime=0;
        String userId="";
        try{
            String decToken=SupportToken.genRSADecrypt(token);
            userId=decToken.split(";")[0];
            endTime=Long.parseLong(decToken.split(";")[1]);
        }catch(Exception ex){
            throw new EncryptException("非法的Cookie token值,"+ex.getMessage());
        }
        if(endTime>=0){
            //则判断 token值 在有效时间里
            long curTime=System.currentTimeMillis()/1000;
            if(curTime>endTime){
                //超过了cookie有效期,需要重新登录
                throw new TimeoutException("Token Cookie已过期,系统需要重新登录");
            }
        }
        return userId;
    }

}
