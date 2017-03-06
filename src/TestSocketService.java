import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhangzz on 2017/3/3.
 */
public class TestSocketService {
    public static void main(String[] args) {
        String data = "Toobie ornaught toobie";
        try {
            ServerSocket srvr = new ServerSocket(1234);
            Socket       skt  = srvr.accept();
            System.out.print("Server has connected!\n");
            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
            skt.shutdownOutput();
            System.out.print("Sending string: '" + data + "'\n");
            BufferedReader reader =new BufferedReader(new InputStreamReader(skt.getInputStream()));
            String line;
            String total="";
            while ((line = reader.readLine()) != null){
                total+=line;
                System.out.println(line);
            }
            System.out.println(total);
            out.print(data);
            out.close();
            skt.close();
            srvr.close();
        }
        catch(Exception e) {
            System.out.print("Whoops! It didn't work!\n");
            e.printStackTrace();
        }
    }

}
