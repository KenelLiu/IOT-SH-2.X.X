package iot.sh.ffmpeg;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;
import ws.schild.jave.info.VideoSize;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author Kenel Liu
 */
@Slf4j
public class FfmpegUtil {
    private static String exePath=null;

    static{
        DefaultFFMPEGLocator locator= new  DefaultFFMPEGLocator();
        exePath=locator.getExecutablePath();
        if(log.isInfoEnabled())
            log.info("ffmpeg executable found in [{}]",exePath);
    }

    private FfmpegUtil(){

    }
    /**
     * 测试 C:\Users\KenelLiu\AppData\Local\Temp\jave\ffmpeg-amd64-3.3.1.exe -f concat -i F:/video/file.txt -c:v copy -c:a copy f:/merging1.mp4
     * ffmpeg 合并
     * 1.MPEG格式的视频:ffmpeg -i "concat:input1.mpg|input2.mpg|input3.mpg" -c copy output.mpg
     * 2.非MPEG格式容器,是MPEG编码器视频:ffmpeg -i "concat:input1.ts|input2.ts|input3.ts" -c copy -bsf:a aac_adtstoasc -movflags +faststart output.mp4
     * 3.FFmpeg concat分离器【重要】
     *     先创建一个文本文件 filelist.txt：
     *     file 'input1.mkv'
     *     file 'input2.mkv'
     *     file 'input3.mkv'
     *  ffmpeg -f concat -i filelist.txt -c copy output.mkv
     * 4.Mencoder连接文件并重建索引,无 MPEG 编码器的视频
     *   mencoder -forceidx -of lavf -oac copy -ovc copy -o output.flv input1.flv input2.flv input3.flv
     * 5.使用 FFmpeg concat过滤器重新编码【备用】
     *  ffmpeg -i input1.mp4 -i input2.webm -i input3.avi -filter_complex '[0:0] [0:1] [1:0] [1:1] [2:0] [2:1] concat=n=3:v=1:a=1 [v] [a]' -map '[v]' -map '[a]' <编码器选项> output.mkv
     * @param txtFile 内容为需要合并视频文件
     * file 'input1.mkv'
     * file 'input2.mkv'
     * file 'input3.mkv'
     * @param mergeVideoFile   input1.mkv，input2.mkv，input3.mkv 合并后的视频
     * @return
     */
    public static boolean mergeVideoFiles(File txtFile,File mergeVideoFile){
        Process process=null;
        try{
            String command=exePath+" -f concat -i "+txtFile.getAbsolutePath()
                    +" -c:v copy -c:a copy "+mergeVideoFile.getAbsolutePath();
            if(log.isDebugEnabled())
                log.debug("exec merge cmd="+command);
            process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Merging video was successful!");
                return true;
            } else {
                log.error("Merging video failed.");
                return  false;
            }
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if(process!=null){try {process.destroy();}catch(Exception ignored){}}
        }
        return false;
    }

    /**
     *  转换成同一种格式视频，可以快速合成一个视频.
     * @param inputVideo
     * @param outputVideo
     * @throws IllegalArgumentException
     */
    public static void convertVideo(File inputVideo, File outputVideo) throws IllegalArgumentException, EncoderException {
        /* Step 1. Set Audio Attrributes for conversion*/
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");//audio.setCodec("libmp3lame");
        audio.setBitRate(44100); //here 48kbit/s is 48000
        audio.setChannels(1);
        audio.setSamplingRate(44100);//采样率44100
        /* Step 2. Set Video Attributes for conversion*/
        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");// video.setCodec("libx264");video.setCodec("mpeg4");
        video.setX264Profile(X264_PROFILE.HIGH);
        video.setBitRate(960000);//原视频 1469000/1328000  Here 960 kbps video is 960000
        // More the frames more quality and size, but keep it low based on devices like mobile
        video.setFrameRate(24);
        video.setSize(new VideoSize(720, 1280));//720,1280;
        convertVideo(inputVideo,outputVideo,audio,video);
    }

    public static void convertVideo(File inputVideo,File outputVideo,AudioAttributes audioAttributes, VideoAttributes videoAttributes) throws IllegalArgumentException, EncoderException {
        convertVideo(inputVideo,outputVideo,audioAttributes,videoAttributes,"mp4");
    }

    public static void convertVideo(File inputVideo,File outputVideo,AudioAttributes audioAttributes, VideoAttributes videoAttributes,String outputFormat) throws IllegalArgumentException, EncoderException {
        boolean isVideo=isVideo(inputVideo);
        if(!isVideo)
            throw new IllegalArgumentException("文件["+inputVideo.getAbsolutePath()+"]不是视频格式");
        /* Step 3. Set Encoding Attributes*/
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat(outputFormat);
        attrs.setAudioAttributes(audioAttributes);
        attrs.setVideoAttributes(videoAttributes);
        /* Step 4.  Encoding output file*/
        MultimediaObject source = new MultimediaObject(inputVideo);
        Encoder encoder = new Encoder();
        encoder.encode(source, outputVideo, attrs);
    }

    /**
     * 使用 FFmpeg 命令行工具获取文件信息
     * @param inputVideo
     * @return
     */
    public static boolean isVideo(File inputVideo) {
        String filePath=inputVideo.getAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(exePath, "-i", filePath);
        BufferedReader reader = null;
        Process process=null;
        String line;
        try {
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                line=line.toLowerCase();
                if (line.contains("video:")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        } finally {
            try {if (reader != null) {reader.close();}} catch (Exception e) {}
            try {if (process != null) {process.destroy();}} catch (Exception e) {}
        }
    }
}
