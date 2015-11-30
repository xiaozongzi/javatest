package Secret;

/**
 * Created by ${zhangzz} on 2015/11/24.
 */
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public  class AESHelper {
    public static final String TAG = AESHelper.class.getSimpleName();

    Runtime mRuntime = Runtime.getRuntime();

    @SuppressWarnings("resource")
    public boolean AESCipher(int cipherMode, String sourceFilePath,
                             String targetFilePath, String seed) {
        boolean result = false;
        FileChannel sourceFC = null;
        FileChannel targetFC = null;

        try {

            if (cipherMode != Cipher.ENCRYPT_MODE
                    && cipherMode != Cipher.DECRYPT_MODE) {

                return false;
            }

            Cipher mCipher = Cipher.getInstance("AES/CBC/NoPadding");

            byte[] rawkey =seed.getBytes();
            File sourceFile = new File(sourceFilePath);
            File targetFile = new File(targetFilePath);

            sourceFC = new RandomAccessFile(sourceFile, "r").getChannel();
            targetFC = new RandomAccessFile(targetFile, "rw").getChannel();

            SecretKeySpec secretKey = new SecretKeySpec(rawkey, "AES");
            byte[] f="1234567812345678".getBytes();
            mCipher.init(cipherMode, secretKey, new IvParameterSpec(f));

            ByteBuffer byteData = ByteBuffer.allocate(1024*1024);

            while (sourceFC.read(byteData) != -1) {
                // 通过通道读写交叉进行。
                // 将缓冲区准备为数据传出状态
                byteData.flip();


                byte[] byteList = new byte[byteData.remaining()];
               /* int len = byteList.length;
        *//* 计算补0后的长度 *//*
                while(len % 16 != 0) len ++;
                byte[] sraw = new byte[len];
        *//* 在最后补0 *//*
                for (int i = 0; i < len; ++i) {
                    if (i < byteList.length) {
                        sraw[i] = byteList[i];
                    } else {
                        sraw[i] = 0;
                    }
                }
*/
               byteData.get(byteList, 0, byteList.length);
                //System.out.println( new String(byteList));
//此处，若不使用数组加密解密会失败，因为当byteData达不到1024个时，加密方式不同对空白字节的处理也不相同，从而导致成功与失败。

                byte[] bytes = mCipher.doFinal(byteList);
                targetFC.write(ByteBuffer.wrap(bytes));
                byteData.clear();
            }

            result = true;
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (sourceFC != null) {
                    sourceFC.close();
                }
                if (targetFC != null) {
                    targetFC.close();
                }
            } catch (IOException e) {

            }
        }

        return result;
    }
    public static String Decrypt(String sourceFilePath,String decryptFilePath,String skey,String sIv) throws Exception {
        try {

            File sourceFile = new File(sourceFilePath);
            File decryptFile = new File(decryptFilePath);
            if (!decryptFile.getParentFile().exists()) {
                decryptFile.getParentFile().mkdirs();
            }
            decryptFile.createNewFile();
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(decryptFile);

            byte[] key = skey.getBytes("utf-8");
            byte[] iv = sIv.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            IvParameterSpec _iv = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,_iv);
            CipherOutputStream cout = new CipherOutputStream(out, cipher);

            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                cout.write(cache, 0, nRead);
                cout.flush();
            }
            cout.close();
            out.close();
            in.close();
            System.out.print("解密成功");
            return "解密成功";
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return "解密失败";
        }
    }

    /**
     * 加密后的字符串
     *
     * @param seed
     * @param
     * @return
     */
    public String encrypt(String seed, String source) {
        // Log.d(TAG, "加密前的seed=" + seed + ",内容为:" + clearText);
        byte[] result = null;
        try {
            byte[] rawkey = getRawKey(seed.getBytes());
            result = encrypt(rawkey, source.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = toHex(result);
        return content;

    }

    /**
     * 解密后的字符串
     *
     * @param seed
     * @param encrypted
     * @return
     */
    public String decrypt(String seed, String encrypted) {
        byte[] rawKey;
        try {
            rawKey = getRawKey(seed.getBytes());
            byte[] enc = toByte(encrypted);
            byte[] result = decrypt(rawKey, enc);
            String coentn = new String(result);
            return coentn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用一个安全的随机数来产生一个密匙,密匙加密使用的
     *
     * @param seed
     * @return
     * @throws NoSuchAlgorithmException
     */
    private byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException {
        // 获得一个随机数，传入的参数为默认方式。
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        // 设置一个种子,一般是用户设定的密码
        sr.setSeed(seed);
        // 获得一个key生成器（AES加密模式）
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        // 设置密匙长度128位
        keyGen.init(128, sr);
        // 获得密匙
        SecretKey key = keyGen.generateKey();
        // 返回密匙的byte数组供加解密使用
        byte[] raw = key.getEncoded();
        return raw;
    }

    /**
     * 结合密钥生成加密后的密文
     *
     * @param raw
     * @param input
     * @return
     * @throws Exception
     */
    private byte[] encrypt(byte[] raw, byte[] input) throws Exception {
        // 根据上一步生成的密匙指定一个密匙
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // Cipher cipher = Cipher.getInstance("AES");
        // 加密算法，加密模式和填充方式三部分或指定加密算
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 初始化模式为加密模式，并指定密匙
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(input);
        return encrypted;
    }

    /**
     * 根据密钥解密已经加密的数据
     *
     * @param raw
     * @param encrypted
     * @return
     * @throws Exception
     */
    private byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public String toHex(byte[] buf) {
        if (buf == null || buf.length <= 0)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
