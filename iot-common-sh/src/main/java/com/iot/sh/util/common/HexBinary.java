package com.iot.sh.util.common;

public class HexBinary {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private HexBinary(){}
    public static String bytes2Hex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hex2Bytes(String strHex) {
        int len = strHex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(strHex.charAt(i), 16) << 4)
                    + Character.digit(strHex.charAt(i+1), 16));
        }
        return data;
    }
}
