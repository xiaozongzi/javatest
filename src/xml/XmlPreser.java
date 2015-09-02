package xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import java.io.File;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by ${zhangzz} on 2015/8/24.
 */
public class XmlPreser {
    public static void main(String[] args){
        readXml("D:\\fs\\out\\artifacts\\fs_war_exploded\\data\\1\\21304f2f-e004-49f0-ae98-dbc3ed7cfef8\\imsmanifest.xml");
    }
    public static void readXml(String xmlFileName){
        SAXReader saxReader = new SAXReader();
        try {
            Document document=saxReader.read(new File(xmlFileName));
            Element element=document.getRootElement();
            manifest manifest= getNodes(element,manifest.class);
            if (manifest!=null){

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static String generateSetMethod(Field field) {
        String fieldName = field.getName();
        field.setAccessible(true);
        return "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
    }

    public static <T> T getNodes(Element node,Class<T> object){


        try {
            Class<T> zoClass = (Class<T>) Class.forName(object.getName());
            Constructor<?> constructor = null;
            constructor = zoClass.getConstructor();
            T out = (T) constructor.newInstance();
            Field [] fields=out.getClass().getDeclaredFields();

           Class c1= object.getSuperclass();

            for (Field field:fields) {
                // String name=field.getName();
                Class<?> c = field.getType();

                Object c2 =null;
                //System.out.println(name+field.getType().getName());
                if (isInterface(c, List.class)) {
                    Object c3 = new Object();
                    ParameterizedType pt = (ParameterizedType) field.getGenericType();

                    List<Element> listElement = node.elements();//所有一级子节点的list
                    List list = new ArrayList();
                    for (Element e : listElement) {//遍历所有一级子节点
                        // getNodes(e,object);//递归
                        if (e.getName().equals(field.getName())) {
                            c3 = getNodes(e, (Class < ?extends Object>)pt.getActualTypeArguments()[0]);
                            list.add(c3);
                        }
                    }
                    c2=new Object();
                    c2 = list;
                } else {
                    c2=c.newInstance();
                    Element findElement = null;
                    if (!c.equals(String.class)) {
                        List<Element> listElement = node.elements();//所有一级子节点的list
                        for (Element e : listElement) {//遍历所有一级子节点
                            // getNodes(e,object);//递归
                            if (e.getName().equals(c.getSimpleName())) {
                                findElement = e;
                            }
                        }
                        if (findElement != null)
                            c2 = getNodes(findElement, c);
                    } else {

                 //       System.out.println("当前节点名称：" + node.getName());//当前节点名称
                    //    System.out.println("当前节点的内容：" + node.getTextTrim());//当前节点名称
                        if (node.getTextTrim()!=null){
                            c2=node.getTextTrim();
                        }
                        List<Attribute> listAttr = node.attributes();//当前节点的所有属性的list
                        for (Attribute attr : listAttr) {//遍历当前节点的所有属性
                            String name = attr.getName();//属性名称
                            String value = attr.getValue();//属性的值
                            if (field.getName().equals(name)) {
                                c2 = value;
                           //     System.out.println("属性名称：" + name + "属性值：" + value);
                            }

                        }
                    }
                }
                String mothed = generateSetMethod(field);
                object.getMethod(mothed, c).invoke(out, c2);

            }
            return out;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean isInterface(Class c, Class Interface) {
        String szInterface = Interface.getName();
        Class[] face = c.getInterfaces();
        if (c.getName().equals(szInterface))
            return true;
        for (Class aFace : face) {
            if (aFace.getName().equals(szInterface)) {
                return true;
            } else {
                Class[] face1 = aFace.getInterfaces();
                for (Class aFace1 : face1) {
                    if (aFace1.getName().equals(szInterface)) {
                        return true;
                    } else if (isInterface(aFace1, Interface)) {
                        return true;
                    }
                }
            }
        }
        return null != c.getSuperclass()
                && isInterface(c.getSuperclass(), Interface);
    }
}
