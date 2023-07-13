package iot.sh.util.token;

import iot.sh.exception.EncryptException;
import iot.sh.util.encrypt.AES;
import iot.sh.util.encrypt.RSA;
import org.apache.commons.codec.binary.Base64;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.TimeoutException;

public class SupportToken {
	private static String modulus="d4377df7de49df182da1";
	private static String publicExponent="10001";
	private static String primodulus="45039aeabd7f0c7d3231";
	
	/**
	 * 
	 * @param inputData  原始数据
	 * @return  RSA加密数据
	 * @throws EncryptException
	 */
	public static String genRSAEncrypt(String inputData) throws EncryptException {
	        byte[] b_modulus = new BigInteger(modulus,16).toByteArray();       
	        byte[] b_publicExponent = new BigInteger(publicExponent,16).toByteArray();
	        RSAPublicKey recoveryPubKey = RSA.getRSAPublicKeyBouncy(b_modulus, b_publicExponent);
	        byte[] encrypt = RSA.encryptBouncy(recoveryPubKey,inputData.getBytes());	                
	        return RSA.byte2hex(encrypt);
	}
	
	/**
	 * @param RSAData 为RSA加密数据
	 * @return
	 * @throws EncryptException
	 * @throws TimeoutException 
	 */
	public static String genRSADecrypt(String RSAData) throws EncryptException, TimeoutException{	
		try{
	        byte[] priModBytes = new BigInteger(modulus,16).toByteArray();
	        byte[] priPriExpBytes = new BigInteger(primodulus,16).toByteArray();
	        RSAPrivateKey recoveryPriKey = RSA.getRSAPrivateKeyBouncy(priModBytes, priPriExpBytes);   
	        byte[] decoder=RSA.hex2byte(RSAData);
	        byte[] decrypt = RSA.decryptBouncy(recoveryPriKey, decoder);
	        return new String(decrypt);
		}catch(EncryptException ex){
			throw new EncryptException("invalidate RSAData="+RSAData+" "+ex.getMessage(),ex);
		}		
	} 
	
	
	/**
	 * 加密
	 * @param inputStr 原始数据
	 * @param AES_Key AES算法的密钥
	 * @return
	 * @throws EncryptException
	 */
	public static String genAESEncrypt(String inputStr,byte[] AES_Key) throws EncryptException{		
		byte[] inputData = inputStr.getBytes();
		try{
			inputData = AES.encrypt(inputData, AES_Key);
		}catch(Exception ex){
			throw new EncryptException(ex.getMessage(),ex);
		}
		return Base64.encodeBase64String(inputData);
	}
	/**
	 * 解密
	 * @param AESData  AES算法的加密二进制数据,使用Base64.encodeBase64String转换成字符串
	 * @param AES_Key AES算法的密钥
	 * @return
	 * @throws EncryptException
	 */
	public static String genAESDecrypt(String AESData,byte[] AES_Key) throws EncryptException{		
		// 加密
		byte[] inputData=null;
		try{
			inputData = Base64.decodeBase64(AESData);
			inputData = AES.decrypt(inputData, AES_Key);
		}catch(Exception ex){
			throw new EncryptException("invalidate AESData="+AESData+" "+ex.getMessage(),ex);
		}
		return new String(inputData);
	}

}
