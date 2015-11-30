package Secret;


import javax.crypto.Cipher;

/**
 * Created by ${zhangzz} on 2015/11/24.
 */
public class AESTest {
    public static void main(String[] a){
       /* testArray();
        testLinkedlist();*/
        // testMap();
        //testSet();
        //testArrayList();
        //testReplace();
            test();
    }
    private static void test(){
        String sourceFile="C:\\Users\\user.WX-711701-00345\\Downloads\\5e039a43-01df-4c8f-8b4b-badae7657a19.mp4";
        String targeFile="C:\\Users\\user.WX-711701-00345\\Downloads\\test\\2015444444.mp4";
        String sF="C:\\Users\\user.WX-711701-00345\\Downloads\\37473df4762739a2f700161c967fd11c.mp4.mp4";
        String TF="C:\\Users\\user.WX-711701-00345\\Downloads\\20151111111.mp4";
        String TF2="C:\\Users\\user.WX-711701-00345\\Downloads\\201522222.mp4";
        String TF3="D:\\360安全浏览器下载\\xiao20151126.mp4";
        String TF4="D:\\360安全浏览器下载\\xiao20151126111111.mp4";
        AESHelper aesHelper=new AESHelper();
       // aesHelper.AESCipher(Cipher.DECRYPT_MODE,sourceFile,targeFile,"c4a8b770844d4115");
       // aesHelper.AESCipher(Cipher.ENCRYPT_MODE,sF,TF,"c4a8b770844d4115");
       // aesHelper.AESCipher(Cipher.DECRYPT_MODE,TF3,TF4,"1234567812345678");
        try {
            aesHelper.Decrypt(TF3,TF4,"1234567812345678","1234567812345678");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
