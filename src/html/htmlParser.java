package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

/**
 * Created by ${zhangzz} on 2015/9/14.
 */
public class htmlParser {
    static     String data=
            "\"<p><span style=\"text-decoration: underline;\">地理空间flak大家</span><br/></p><p><span style=\"text-decoration: underline;\"><video class=\"edui-upload-video  vjs-default-skin video-js\" controls=\"\" preload=\"none\" width=\"420\" height=\"280\" src=\"http://192.168.2.147:8021//Files/0/6ce94cc0ba084e7db8f881c85761126a.mp4\" data-setup=\"{}\"><source src=\"http://192.168.2.147:8021//Files/0/6ce94cc0ba084e7db8f881c85761126a.mp4\" type=\"video/mp4\"/></video></span></p><p><span style=\"text-decoration: underline;\">年大量的</span></p><p><span style=\"text-decoration: underline;\"><video class=\"edui-upload-video  vjs-default-skin video-js\" controls=\"\" preload=\"none\" width=\"420\" height=\"280\" src=\"http://192.168.2.147:8021//Files/0/0a9fedde1ba74d5e9d6ff243948981d3.mp4\" data-setup=\"{}\"><source src=\"http://192.168.2.147:8021//Files/0/0a9fedde1ba74d5e9d6ff243948981d3.mp4\" type=\"video/mp4\"/></video></span></p><p><span style=\"text-decoration: underline;\">拉开大接访的</span></p><p><span style=\"text-decoration: underline;\"><video class=\"edui-upload-video  vjs-default-skin video-js\" controls=\"\" preload=\"none\" width=\"420\" height=\"280\" src=\"http://192.168.2.147:8021//Files/0/77d4c78995dd48938906c109bab9cdc2.mp4\" data-setup=\"{}\"><source src=\"http://192.168.2.147:8021//Files/0/77d4c78995dd48938906c109bab9cdc2.mp4\" type=\"video/mp4\"/></video></span></p><p><span style=\"text-decoration: underline;\"><video class=\"edui-upload-video  vjs-default-skin video-js\" controls=\"\" preload=\"none\" width=\"420\" height=\"280\" src=\"http://192.168.2.147:8021//Files/0/661bae8b503747f094622acc327c316a.mp4\" data-setup=\"{}\"><source src=\"http://192.168.2.147:8021//Files/0/661bae8b503747f094622acc327c316a.mp4\" type=\"video/mp4\"/></video></span></p><p><span style=\"text-decoration: underline;\">打发打发打发</span></p>\"";

    public static void main(String[] s){
            Document document= Jsoup.parse(data);
            Elements element=document.getElementsByTag("p");
//            if (element.size()<=0)

           // Elements elementsRemove= element.remove();
            for (Element element1:element){

                Elements elements=element1.children();
                getNode(element1.childNodes());
              /*  Elements element2=element1.getElementsByTag("video");
                if (element2.size()>=1){
                    String src=element2.attr("src");
                    System.out.println(src);
                }
                Elements elements3=element1.getElementsByTag("img");
                if (elements3.size()>=1){
                    for (int i=0;i<elements3.size();i++) {
                        String src=elements3.attr("src");
                        System.out.println(src);
                    }
                }
                if (element1.hasText()) {
                    String s2 = element1.text();
                    System.out.println(s2);
                }*/

            }
    }

    private static void getNode( List<Node> list) {

        for (Node node:list){
            getNode(node.childNodes());
            System.out.println(node.attr("text"));
            if (node.nodeName().equals("img"))
            System.out.println(node.attr("src"));
            if (node.nodeName().equals("video"))
            System.out.println(node.attr("src"));
        }
    }

    private static void prase(){
        try {
            InputStream stream=new StringBufferInputStream(data);
            SAXParser parser = SAXParserFactory.newInstance().newInstance().newSAXParser();

            parser.parse(stream,new SaxHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
