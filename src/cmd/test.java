package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhangzz on 2016/11/3.
 */
public class test {
    static String cmd="adb -s 9c7994a9 shell monkey -s 184 -p com.android.contacts -v  --throttle 300 --pct-trackball 0 --pct-syskeys 5 --pct-nav 0 --pct-anyevent 0 4000000";
    public static void main(String[] args) {
        try {
            Process exec = Runtime.getRuntime().exec(cmd);
            InputStreamReader reader=new InputStreamReader(exec.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(reader);
            String s;
            do {
                 s = bufferedReader.readLine();
                System.out.println(s);
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
