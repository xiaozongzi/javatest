package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by ${zhangzz} on 2015/9/14.
 */
public class htmlParser {
    static String data="<div style=\"text-indent: 21.75pt;\"><span style=\"font-size: 14px;\">一、事故概况</span></div>\n" +
            "    <p><span style=\"font-size: 14px;\"> </span></p>\n" +
            "    <div style=\"text-indent: 21.75pt;\"><span style=\"font-size: 14px;\">6月26日14时50分，澄县太青乡煤矿田某持失效的B型实习驾驶证驾驶东风140型大货车装载两千块碳化砖，由漕县甘溪滩镇石灰厂运往渲县太青乡。行驶途中，田某先后三次停车搭载19人，驾驶室乘坐5人。当车行至甘太公路3K + 100米处即东门乡东门村一组路段时，与对方车辆会车。因估计不足，过于靠右，车辆压塌公路路肩，翻入公路右侧深坎下的水田，造成搭乘在货厢砖上的6人死亡、1人重伤,1人轻伤的特大交通事故。</span></div>\n" +
            "    <p><span style=\"font-size: 14px;\"> </span></p>\n" +
            "    <p style=\"text-align: center;\"><span style=\"font-size: 14px;\">\n" +
            "    <object width=\"400\" height=\"300\" data=\"http://192.168.2.111:8261/data/1/2015/July/8/retechwing/392e23f2c378f5e6335d86f50bbdd0b2.mp4.mp4\" type=\"application/x-shockwave-flash\">\n" +
            "    <param name=\"flashvars\" value=\"file=http://192.168.2.111:8261/data/1/2015/July/8/retechwing/392e23f2c378f5e6335d86f50bbdd0b2.mp4.mp4\" />\n" +
            "    <param name=\"src\" value=\"http://192.168.2.111:8261/data/1/2015/July/8/retechwing/392e23f2c378f5e6335d86f50bbdd0b2.mp4.mp4\" />\n" +
            "    </object>\n" +
            "    </span></p>\n" +
            "    <p><span style=\"font-size: 14px;\"> </span></p>\n" +
            "    <div style=\"text-indent: 23.5pt;\"><span style=\"font-size: 14px;\">二、事故原因及结论</span></div>\n" +
            "    <p><span style=\"font-size: 14px;\"> </span></p>\n" +
            "    <div style=\"text-indent: 23.5pt;\"><span style=\"font-size: 14px;\">驾驶人田某交通法制 观念淡薄，安全思想极端麻痹。田某于1997年11月学习汽车驾驶，1998年8月取得实习驾驶证，1999年8月因交通肇事被吊扣实习证。因没有申请复 验和办理实习转正考试，一直持失效的实习驾驶证驾车。因技术生疏，会车时无法判断车辆所处位置，致使车辆过于靠右，翻入公路右侧水田。在事故中田某在车辆 已经严重超载的情况下，又先后搭载19人乘坐于货厢和驾驶室，且驾驶室超载2人，严重影响了正常的驾驶操作，货车违章搭乘人员加重了事故的损害后果。</span></div>\n" +
            "    <p><span style=\"font-size: 14px;\"> </span></p>\n" +
            "    <div style=\"text-indent: 23.5pt;\"><span style=\"font-size: 14px;\">公安交通管理机关经现场勘查和调查取证，依法对此次事故的责任进行了认定:</span></div>\n" +
            "    <p><span style=\"font-size: 14px;\"> </span></p>\n" +
            "    <div style=\"text-indent: 23.5pt;\"><span style=\"font-size: 14px;\">驾驶人田某使用失效 驾驶证驾车、超载、人货混装，违反了《中华人民共和国道路交通安全法》第十九条&ldquo;驾驶机动车，应当依法取得机动车驾驶证&rdquo;，第四十九条&ldquo;机动车载人不得超 过核定的人数，客运机动车不得违反规定载货。&rdquo;，第五十条&ldquo;禁止货运机动车载客。&rdquo;的规定，这是造成这起事故的直接原因，他应负事故的全部责任，搭乘在货 车上的死伤者不负事故责任。而且田某严重违反交通法规，造成6人死亡、2人受伤的特大交通事故，其行为已构成交通肇事罪，依照《中华人民共和国刑法》第一 百一十三条之规定，当地人民法院依法判处田某有期徒刑x年。</span></div>";
    public static void main(String[] s){
            Document document= Jsoup.parse(data);
            Elements element=document.getElementsByTag("object");
            Elements elementsRemove= element.remove();
            for (Element element1:element){
                   Attributes attributes= element1.attributes();
            }


    }
}
