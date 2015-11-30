import java.util.*;

/**
 * Created by ${zhangzz} on 2015/8/7.
 */
public class test {
    public static void main(String[] a){
       /* testArray();
        testLinkedlist();*/
       // testMap();
        //testSet();
        //testArrayList();
        //testReplace();
        ChangeUrl();

    }

    private static void testReplace(){

        String s="##"+"\u002A"+"\u002A"+"##";
        String re="we"+s+"e";

        String[] ss=re.split(s);
        System.out.print(s);

    }
    private static void ChangeUrl(){
        String url="http://cshj.anpeiwang.com/ELN/Mobile/GetCourseCategorys.action?tid=1";
        url=url.replace("?",".action?");
        System.out.print(url);
    }
    private static void testSet(){
        Set s=new TreeSet();
        s.add(1);
        s.add(2);
        Map map=new TreeMap();
        map.put("fe",1);
        map.put("fer",12);
        System.out.println(map.toString()+"/"+s.toString());
    }
    private static void testMap(){
        Map map=new HashMap();
        map.put("fe",1);
        map.put("fe2",1);

        System.out.println(map.toString());
    }
    private static void  testLinkedlist(){
        List l=new LinkedList();
        for (int i=0;i<10000000;i++){
            l.add(i);
        }
        long startTime=System.currentTimeMillis();
       // int s= (Integer) l.remove(8000);
        l.add(2);
        long endTime=System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
    private static void testArray(){
        List l= new ArrayList();
        for (int i=0;i<10000000;i++){
            l.add(i);
        }
        long startTime=System.currentTimeMillis();
       // int s= (Integer) l.remove(5000000);
        l.add(2);
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }
    private void testStack(){
        Stack<String> strings=new Stack<String>();
        Stack m=new Stack();
        for(int i=0;i<10;i++){
            m.push(i);
        }
        while (!m.isEmpty()){
            System.out.println(m.pop());
        }
    }
    private static String test="se";
    private static void testArrayList(){
        People people=new People();
        people.setName("222");
        String ss="1212";
        int i=111111;
        System.out.println(ss);
        System.out.println(i);
        System.out.println(people.getName());
        change(people);
        change(ss);
        change(i);
        System.out.println(ss);
        System.out.println(i);
        System.out.println(people.getName());
    }
    private static void change(String s){
        s="refe";
    }
    private static void change(int s){
        s=121222111;
    }
    private static void change(People s){
        s.setName("dfd");
    }
    private ArrayList<String> get(){
        ArrayList<String> strings=new ArrayList<String>();
        strings.add("232");
        return strings;
    }
}
