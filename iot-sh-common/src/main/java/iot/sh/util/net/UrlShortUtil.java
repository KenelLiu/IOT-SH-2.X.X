package iot.sh.util.net;

import iot.sh.util.encrypt.MD5;

public class UrlShortUtil {
	  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	  private static final int    BASE     = ALPHABET.length();

	 public static String encode(int num) {
	        StringBuilder sb = new StringBuilder();
	        while ( num > 0 ) {
	            sb.append( ALPHABET.charAt( num % BASE ) );
	            num /= BASE;
	        }
	        return sb.reverse().toString();   
	 }

	public static int decode(String str) {
	        int num = 0;
	        for ( int i = 0; i < str.length(); i++ )
	            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
	        return num;
	}   
	
    public static String[] shortUrl(String url) {
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
        };
        String key = "china-cos.com";                               // 可以自定义生成 MD5 加密字符传前的混合加密key
        String sMD5EncryptResult = MD5.encode(key + url);       // 对传入网址进行 MD5 加密，key是加密字符串
        String hex = sMD5EncryptResult;
 
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
 
            // 这里需要使用 long 型来转换，因为 Inteter.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                long index = 0x0000003D & lHexLong;     // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                outChars += chars[(int) index];         // 把取得的字符相加
                lHexLong = lHexLong >> 5;             // 每次循环按位右移 5 位
            }
            resUrl[i] = outChars;                       // 把字符串存入对应索引的输出数组
        }
        return resUrl;
    }
    
}
