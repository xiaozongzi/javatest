package javaBase;

/**
 * Created by ${zhangzz} on 2015/11/30.
 */
public class child extends parent {
    public int i=3;
    public child() {
        i=4;
        super.i=5;
        System.out.println(12345+5432l);

    }
    public void get1(){
        System.out.println("childget");
        super.get();
    }
}
