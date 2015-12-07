package Secret.MD5;


import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${zhangzz} on 2015/11/30.
 */
public class MD5Test {
   public static void main(String[] s){
       getMD5("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
   }
    public static void getMD5(String s){
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
           byte[] bytes= messageDigest.digest(s.getBytes());
            System.out.println(bytesToHex(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
