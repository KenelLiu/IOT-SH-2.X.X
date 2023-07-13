package iot.sh.pdf.html;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author Kenel Liu
 */
@Slf4j
public class PdfThymeleafEngine {
    private String templateResolverPrefix;//设置模版前缀，相当于需要在资源文件夹中创建一个[templateResolverPrefix=值]文件夹
    private TemplateEngine templateEngine;
    private   static PdfThymeleafEngine pdfThymeleafEngine=new PdfThymeleafEngine();

    private PdfThymeleafEngine(){}

    public TemplateEngine getTemplateEngine(){
        return getTemplateEngine(null);
    }

    public TemplateEngine getTemplateEngine(String templateResolverPrefix){
        if(templateEngine!=null) return templateEngine;
        synchronized(pdfThymeleafEngine){
            if(templateResolverPrefix==null)
                templateResolverPrefix="/html2pdfTemplate/";
            if(!templateResolverPrefix.startsWith("/"))
                templateResolverPrefix="/"+templateResolverPrefix;
            if(!templateResolverPrefix.endsWith("/"))
                templateResolverPrefix=templateResolverPrefix+"/";
            this.templateResolverPrefix=templateResolverPrefix;

            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            // 设置模版前缀，相当于需要在资源文件夹中创建一个html2pdfTemplate文件夹，所有的模版都放在这个文件夹中
            resolver.setPrefix(templateResolverPrefix);
            // 设置模版后缀
            resolver.setSuffix(".html");
            resolver.setCharacterEncoding("UTF-8");
            resolver.setCacheable(true);
            // 设置模版模型为HTML
            resolver.setTemplateMode(TemplateMode.HTML);
            SpringTemplateEngine engine = new SpringTemplateEngine();
            engine.addDialect(new Java8TimeDialect());
            engine.setTemplateResolver(resolver);
            engine.setEnableSpringELCompiler(true);
            return engine;
        }
    }

    public static PdfThymeleafEngine getInstance(){
        return pdfThymeleafEngine;
    }

}
