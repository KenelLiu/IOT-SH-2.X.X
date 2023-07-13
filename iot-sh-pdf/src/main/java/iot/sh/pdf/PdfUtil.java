package iot.sh.pdf;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Kenel Liu
 */
public class PdfUtil {
    private PdfUtil(){}

    /**
     *
     * @param pdfFiles 文件
     * @param mergePdf 全路径文件名
     * @throws IOException
     */
    public static void mergePdfs(List<String> pdfFiles,String mergePdf) throws IOException {
        try {
            PDFMergerUtility merger = new PDFMergerUtility();
            // 遍历要合并的PDF文件列表，逐个添加到合并器中
            for (String pdfFile : pdfFiles) {
                merger.addSource(new File(pdfFile));
            }
            merger.setDestinationFileName(mergePdf);
            merger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
        }catch (Exception ex){
            throw  new IOException(ex.getMessage(),ex);
        }
    }
    /**
     *
     * @param pdfFiles 文件
     * @param mergePdf 全路径文件名
     * @throws IOException
     */
    public  static void mergePdfFiles(List<File> pdfFiles, String mergePdf) throws IOException {
        try {
            PDFMergerUtility merger = new PDFMergerUtility();
            // 遍历要合并的PDF文件列表，逐个添加到合并器中
            for (File pdfFile : pdfFiles) {
                merger.addSource(pdfFile);
            }
            merger.setDestinationFileName(mergePdf);
            merger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
        }catch (Exception ex){
            throw  new IOException(ex.getMessage(),ex);
        }
    }
}
