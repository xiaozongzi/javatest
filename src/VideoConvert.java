import it.sauronsoftware.jave.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhangzz} on 2015/8/28.
 */
public class VideoConvert {
    public static void main(String[] s){
        String ffmpegPath="D:\\ffmpeg\\jave-1.0.2-src\\jave-1.0.2-src\\it\\sauronsoftware\\jave\\ffmpeg.exe";
        String upFilePath="D:\\360安全浏览器下载\\392e23f2c378f5e6335d86f50bbdd0b2.mp4";
        String codcFilePath="D:\\360安全浏览器下载\\392e23f2c378f5e6335d86f50bbdd0b2.mp4.mp4";
        Process process=null;
       /* try {
            if (executeCodecs(ffmpegPath,upFilePath,codcFilePath,null,process)) {
                System.out.println("成功");
            }
            if (process!=null){
                do {
                    System.out.println(process.toString());
                } while (process.isAlive());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        execute(upFilePath,codcFilePath);
    }
    public static  void execute(String upPath,String codePath){
        File source = new File(upPath);
        File target = new File(codePath);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("mp2");
        audio.setBitRate(new Integer(64000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(22050));
        VideoAttributes video = new VideoAttributes();
        video.setCodec("mpeg4");
        video.setBitRate(new Integer(900000));
        video.setFrameRate(new Integer(15));
        video.setSize(new VideoSize(640, 480));


        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
      
        Encoder encoder = new Encoder();

        try {
            encoder.encode(source, target, attrs);
            MultimediaInfo info=encoder.getInfo(target);
            long duration=info.getDuration();
            System.out.println("转换完成"+duration);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
    public static boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
                                 String mediaPicPath,Process process) throws Exception {
        // 创建一个List集合来保存转换视频文件为flv格式的命令

        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); // 添加转换工具路径
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
        convert.add("-qscale");     //指定转换的质量
        convert.add("6");
        convert.add("-ab");        //设置音频码率
        convert.add("64");
        convert.add("-ac");        //设置声道数
        convert.add("2");
        convert.add("-ar");        //设置声音的采样频率
        convert.add("22050");
        convert.add("-r");        //设置帧频
        convert.add("24");
        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);



        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("17"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*280"); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
             process= builder.start();
          //  process.waitFor();
/*
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();*/
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }

}
