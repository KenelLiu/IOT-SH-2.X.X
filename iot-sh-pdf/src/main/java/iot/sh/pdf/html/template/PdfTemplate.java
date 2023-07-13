package iot.sh.pdf.html.template;

import iot.sh.pdf.html.PdfThymeleafEngine;
import iot.sh.pdf.html.model.PdfModel;
import lombok.Data;
import org.thymeleaf.TemplateEngine;
/**
 * 调用项目调用该模块
 * 生成pdf文件写法
    //=========生成Pdf文件=========//
     PdfTemplate pdfTemplate=new PdfTemplate(PdfThymeleafEngine.getInstance().getTemplateEngine(), "AssessICF");
     pdfTemplate.setPdfModel(pdfModel);
     pdfTemplate.parse2Pdf(fileRootPath,uri);
 * @author Kenel Liu
 */
@Data
public class PdfTemplate extends AbstractTemplate{
    // 构造函数
    public PdfTemplate(TemplateEngine engine, String templateName) {
        super(engine, templateName);
    }

    protected PdfModel pdfModel;

    @Override
    protected void addVariable() {
        ctx.setVariable("pdfModel",pdfModel);
    }


}
