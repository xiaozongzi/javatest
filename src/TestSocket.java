import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by zhangzz on 2017/3/2.
 */
public class TestSocket {
    static Socket socket;
    static PrintWriter pw;
    static BufferedReader br;
    static boolean Beat;
    public static void main(String[] args) {
//
        testSocket();
    }

    private static void testSocket() {
        try {
            socket =new Socket("192.168.0.73", 8991);
            OutputStream  os     =socket.getOutputStream();
            pw     =new PrintWriter(os);

//            socket.shutdownOutput();
            InputStream is=socket.getInputStream();
            br=new BufferedReader(new InputStreamReader(is));
            new MyThread("1").start();
//
//            br.close();
//            is.close();
//            pw.close();
//            os.close();
//            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread {

        public String txt1;
        String buffer;
        public MyThread(String str) {
            txt1 = str;
        }

        @Override
        public void run() {
            while (true) {
                pw.write("{\"RFIDTagList\":[{\"EPC\":\"BBB0\",\"TID\":\"E2003412013DF60007891C4C\",\"TotalCount\":3," +
                                 "\"UserData\":\"AAAA11111111111111111111\"},{\"EPC\":\"CCBBB000\"," +
                                 "\"TID\":\"E2003412013BF60007892013\",\"TotalCount\":3," +
                                 "\"UserData\":\"BBBBAADDEEDCCBBAABCDDEBA\"}]}");
                pw.flush();

                String info=null;
                String total="";
                try {
                    while((info=br.readLine())!=null){

                        total+=info;
//                        System.out.println("Says："+info);
                        if(info.indexOf("<ENDOF>")>=0)break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("total："+total);
            }
        }
    }
}
