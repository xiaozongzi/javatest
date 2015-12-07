package javaBase;

/**
 * Created by ${zhangzz} on 2015/11/30.
 */
public class parent {
    public int i=1;
    public parent() {

        System.out.println("parent"+i);
    }
    public void get(){
        System.out.println("parentget");
    }
    public void get2(){
        get();
    }
}
