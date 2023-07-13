package iot.sh.pdf.poi;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import iot.sh.exception.ConvertException;
/**
 * @author Kenel Liu
 */
public class WordUtil {

    private WordUtil(){}

    public static void convertPdf(String docx,String pdf) throws ConvertException {
        convert(docx,pdf,FileFormat.PDF);
    }
    public static void convertOfd(String docx,String ofd)throws ConvertException {
        convert(docx,ofd,FileFormat.OFD);
    }
    public static void convertOdt(String docx,String odt)throws ConvertException {
        convert(docx,odt,FileFormat.Odt);
    }
    public static void convertDoc(String docx,String doc)throws ConvertException {
        convert(docx,doc,FileFormat.Doc);
    }
    public static void convert(String srcFile,String targetFile,FileFormat format) throws ConvertException {
        Document document=null;
        try{
            //实例化Document类的对象
            document = new Document();
            //加载Word文档
            document.loadFromFile(srcFile);
            //保存为PDF/OFD等指定格式
            document.saveToFile(targetFile,format);
        }catch (Exception ex){
            throw new ConvertException(ex.getMessage(),ex);
        }finally {
            if(document!=null)document.close();
        }
    }
}
