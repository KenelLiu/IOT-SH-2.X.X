package iot.sh.pdf.html;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.font.FontProvider;
import iot.sh.exception.ConvertException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
public class HtmlPdfManager {
    private String[] fonts={"STKAITI.TTF","simfang.ttf","simsun.ttc"};
    private static HtmlPdfManager htmlPdfManager=new HtmlPdfManager();

    private HtmlPdfManager(){}

    public static HtmlPdfManager getInstance(){
        return htmlPdfManager;
    }

    /**
     * 将resources/fonts目录下 指定字体复制到fileRootPath+"/fonts"
     * @param fileRootPath
     */
    private void copyFont(String fileRootPath){
        File destFontPath=new File(fileRootPath+"/fonts");
        if(!destFontPath.exists())
            destFontPath.mkdirs();
        for(int i=0;i<fonts.length;i++){
            File fontFile=new File(fileRootPath+"/fonts/"+fonts[i]);
            if(!fontFile.exists()){
                try(InputStream inputStream = Objects.requireNonNull(HtmlPdfManager.class.getResourceAsStream("/fonts/" + fonts[i]));
                    OutputStream outputStream=Files.newOutputStream(new File(destFontPath, fonts[i]).toPath())
                    ){
                    IOUtils.copy(inputStream, outputStream);
                }catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * 加载目录【fileRootPath+"/fonts"】下指定字体
     * 并设置baseuri为rootFilePath
     * @param fileRootPath
     * @return
     */
    public ConverterProperties  getConverterProperties(String fileRootPath){
        copyFont(fileRootPath);
        ConverterProperties converterProperties= new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
        for(int i=0;i<fonts.length;i++){
            File fontFile=new File(fileRootPath+"/fonts/"+fonts[i]);
            if(fontFile.exists()){
                if(fonts[i].endsWith(".ttc")){
                    fontProvider.addFont(fileRootPath+"/fonts/"+fonts[i]+",1");
                }
                if(fonts[i].endsWith(".ttf")) {
                     fontProvider.addFont(fileRootPath+"/fonts/"+fonts[i]);
                }
            }
        }
        converterProperties.setFontProvider(fontProvider);
        converterProperties.setBaseUri(fileRootPath);
        return converterProperties;
    }

    /**
     *
     * @param html  将模板转换成html内容
     * @param fileRootPath 文件路径  /app/file
     * @param uri 为保存到数据库里 uri=/pdf/pdf文件名.pdf;
     * @throws ConvertException
     */
    public   void html2pdf(String html, String fileRootPath,String uri) throws ConvertException {
        try {
            ConverterProperties properties = getConverterProperties(fileRootPath);
            File pdfFile = new File(fileRootPath + uri);
            if (!pdfFile.getParentFile().exists())
                pdfFile.getParentFile().mkdirs();
            if (!pdfFile.exists())
                pdfFile.createNewFile();
            try (final OutputStream os = Files.newOutputStream(pdfFile.toPath());
                 final PdfWriter pdfWriter = new PdfWriter(os);
                 final PdfDocument pdfDocument = new PdfDocument(pdfWriter)) {
                try (final Document doc = HtmlConverter.convertToDocument(html, pdfDocument, properties)) {
                    doc.add(new AreaBreak());
                }
            }
            if(log.isDebugEnabled())
                log.debug("html转换pdf文件:" + pdfFile.getAbsolutePath());
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            throw new ConvertException("html转换pdf文件失败");
        }
    }
}
