package iot.sh.pdf.html.template;

import iot.sh.exception.ConvertException;
import iot.sh.pdf.html.HtmlPdfManager;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author Kenel Liu
 */
@Slf4j
public abstract class AbstractTemplate {
    private TemplateEngine engine;
    private String templateName;
    HtmlPdfManager htmlPdfManager= HtmlPdfManager.getInstance();
    protected Context ctx = new Context();
    private AbstractTemplate(){}

    /**
     *
     * @param engine
     * @param templateName 对应resources/html2pdfTemplate目录下文件名
     *                     即PdfThymeleafEngine.templateResolverPrefix
     */
    public AbstractTemplate(TemplateEngine engine,String templateName) {
        this.engine = engine;
        this.templateName=templateName;
    }

    protected String templateName(){
        return this.templateName;
    }

    protected abstract void addVariable();
    /**
     * 解析模版，生成html
     * @return
     */
    protected String process() {
        addVariable();
        return engine.process(templateName(), ctx);
    }
    /**
     * @param fileRootPath 文件路径  /app/file
     * @param uri 为保存到数据库里 uri=/pdf/pdf文件名.pdf;
     * @throws ConvertException
     */
    public void  parse2Pdf(String fileRootPath,String uri) throws ConvertException {
        String html=process();
        htmlPdfManager.html2pdf(html,fileRootPath,uri);
    }
}
