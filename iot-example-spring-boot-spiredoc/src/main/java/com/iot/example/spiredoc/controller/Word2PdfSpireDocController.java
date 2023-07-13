package com.iot.example.spiredoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iot.sh.jackson.ObjectMappers;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.StringWriter;

@RestController
@RequestMapping("/spire")
@CrossOrigin(origins = "*")
@Slf4j
public class Word2PdfSpireDocController {
	@Autowired
	protected HttpServletRequest request;
	@RequestMapping(path= "/word2Pdf",method = {RequestMethod.GET,RequestMethod.POST}, produces = {"application/json;charset=utf-8"})
	public String word2Pdf() {
		ObjectMapper objectMapper= ObjectMappers.JSON_MAPPER;
		ObjectNode objectNode=objectMapper.createObjectNode();
		try {
			String word = request.getParameter("doc");
			String pdf = request.getParameter("pdf");
			if (word == null || pdf == null) {
				objectNode.put("message", "doc或pdf为null");
			} else {
				//实例化Document类的对象
				Document document = new Document();
				//加载Word文档
				document.loadFromFile(word);
				//保存为OFD格式
				document.saveToFile(pdf, FileFormat.PDF);
				document.close();
				objectNode.put("message","转换完成");
			}
		}catch (Exception ex){
			objectNode.put("message", ex.getMessage());
		}
		StringWriter stringEmp = new StringWriter();
		try {
			objectMapper.writeValue(stringEmp, objectNode);
		}catch (Exception ex){
			log.error(ex.getMessage(),ex);
			stringEmp = new StringWriter();
			stringEmp.write("{\"message\":"+ex.getMessage()+"}");
		}
		return stringEmp.toString();
	}
	@RequestMapping(path= "/mergePdf",method = {RequestMethod.GET,RequestMethod.POST}, produces = {"application/json;charset=utf-8"})
	public String mergePdf() {
		ObjectMapper objectMapper=ObjectMappers.JSON_MAPPER;
		ObjectNode objectNode=objectMapper.createObjectNode();
		try {
			String path = request.getParameter("path");
			String pdfs = request.getParameter("pdfs");
			String mergePdfName = request.getParameter("mergePdfName");
			if (path == null || pdfs == null || mergePdfName==null) {
				objectNode.put("message", "path或pdfs或mergePdfName为null");
			} else {
				String mergedFile=path+"/merge-"+mergePdfName+".pdf";
				PDFMergerUtility merger = new PDFMergerUtility();
				// 遍历要合并的PDF文件列表，逐个添加到合并器中
				for (String pdfFile : pdfs.split(",")) {
					String pdf=path+pdfFile;
					log.info("pdf="+pdf);
					merger.addSource(new File(path+pdfFile));
				}
				// 设置合并后的输出文件
				merger.setDestinationFileName(mergedFile);
				// 执行合并操作
				merger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
				log.info("PDF合并完成！");
				objectNode.put("message","PDF合并完成");
			}
		}catch (Exception ex){
			objectNode.put("message", ex.getMessage());
		}
		StringWriter stringEmp = new StringWriter();
		try {
			objectMapper.writeValue(stringEmp, objectNode);
		}catch (Exception ex){
			log.error(ex.getMessage(),ex);
			stringEmp = new StringWriter();
			stringEmp.write("{\"message\":"+ex.getMessage()+"}");
		}
		return stringEmp.toString();
	}
}
