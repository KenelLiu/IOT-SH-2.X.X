package iot.sh.javacv.util;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.bytedeco.opencv.global.opencv_core.countNonZero;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_UNCHANGED;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * 图片视角分析
 * 通过分析灰阶及黑白像素,来处理图片
 * @author Kenel Liu
 */
@Slf4j
public class ImageUtil {


    public static boolean isSignature(String pathFile){
        Mat image = imread(pathFile,IMREAD_UNCHANGED);//IMREAD_GRAYSCALE 则不需要转换成灰阶
        return isSignature(image);
    }
    /**
     * threshold是OpenCV（开源计算机视觉库）中的一个图像处理函数。该函数可以将图像进行二值化处理，将其转换为黑白图像，
     * 其中所有像素值高于某个阈值的部分被设置为白色，所有低于该阈值的部分则被设置为黑色。它的函数声明如下：
     * void threshold(InputArray src, OutputArray dst, double thresh, double maxval, int type)
     * src： 输入图像（单通道，8位或32位）
     * dst：输出图像，与输入图像大小类型相同
     * thresh：指定阈值
     * maxval：指定二值化后的最大值
     * type：二值化类型的标志，见下表
     * 二值化类型	常量值	备注
     * 二值化	THRESH_BINARY	大于阈值的为最大值，小于阈值的为0
     * 反二值化	THRESH_BINARY_INV	大于阈值的为0，小于阈值的为最大值
     * 截断	THRESH_TRUNC	大于阈值的为阈值，小于阈值的不变
     * 保留小于阈值的	THRESH_TOZERO	大于阈值的不变，小于阈值的为0
     * 反阈值的	THRESH_TOZERO_INV	大于阈值的为0，小于阈值的不变
     * 例如，下面的代码将图像进行二值化：
     *
     * Mat src = imread("image.jpg", 0);
     * Mat dst;
     * threshold(src, dst, 128, 255, THRESH_BINARY);
     * 这个函数将原始图像中的所有像素值高于128的部分设置为255，低于128的部分设置为0，然后将处理后的图像保存在 dst 中。
     */
    public static boolean isSignature(Mat image) {
        Mat grayImage = new Mat();
        Mat binaryImage = new Mat();
        //Mat resizedImage = new Mat();
        // Convert the image to grayscale
        cvtColor(image, grayImage, COLOR_BGR2GRAY);
        // Resize the image
        //resize(grayImage, resizedImage, new Size(150, 150));
        // Perform thresholding to create a binary image
        threshold(grayImage, binaryImage, 100, 255, THRESH_BINARY);
        // Count the number of white pixels in the binary image
        int numWhitePixels = countNonZero(binaryImage);
        if(log.isDebugEnabled())
            log.debug("numWhitePixels="+numWhitePixels);
        // If the number of white pixels is greater than a certain threshold, assume that it is a signature
        return numWhitePixels > 500;
    }



    private static String getOCR(String filePath,String tesseract_DataPath){
        try {
             //设置环境变量TESSDATA_PREFIX,等于[osd.traineddata,chi_sim.traineddata] 所在目录
            //String datapath = System.getenv("TESSDATA_PREFIX");
            Tesseract tesseract = new Tesseract();
            tesseract.setLanguage("chi_sim");
            tesseract.setDatapath(tesseract_DataPath);
            BufferedImage bi = ImageIO.read(new File(filePath));
            return tesseract.doOCR(bi);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
