package com.iot.example.spiredoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iot.sh.jackson.ObjectMappers;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
@RestController
@RequestMapping("/opensagres")
@CrossOrigin(origins = "*")
@Slf4j
public class Word2PdfOpensagresPoiController {
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
				word2Pdf(word,pdf);
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

	private void word2Pdf(String word,String pdf) throws IOException {
		try(FileInputStream fis = new FileInputStream(word);FileOutputStream fos = new FileOutputStream(pdf);) {
			// Load the Word document
			XWPFDocument document = new XWPFDocument(fis);
			// Prepare the output PDF file
			PdfOptions options = PdfOptions.create();
			addFont(options);
			// Convert the Word document to PDF
			PdfConverter.getInstance().convert(document, fos, options);
			// Close streams
			fis.close();
			fos.close();
			System.out.println("Conversion successful!");
		} catch (Exception e) {
			throw new IOException(e.getMessage(),e);
		}
	}

   private void addFont(PdfOptions options){
	   options.fontProvider((familyName, encoding, size, style, color) -> {
		   try {
			   log.info("familyName="+familyName);
			   if(familyName==null)familyName="Times New Roman";
			   String loadFont="simfang.ttf";
			   switch (familyName){
				   case "微软雅黑":
					   loadFont="msyh.ttc";
					   break;
				   case "仿宋":
					   loadFont="simfang.ttf";
					   break;
				   case "楷体":
					   loadFont="simkai.ttf";
					   break;
				   case "宋体":
					   loadFont="simfang.ttf";
				       break;
				   case "Webdings":
					   loadFont="webdings.ttf";
					   break;
				   case "Wingdings":
					   loadFont="wingding.ttf";
					   break;
				   case "Wingdings 2":
					   loadFont="WINGDNG2.TTF";
					   break;
				   case "Wingdings 3":
					   loadFont="WINGDNG3.TTF";
					   break;
				   default:
					   loadFont="times.ttf";
			   }
			   String prefixFont = null;
			   String os = System.getProperties().getProperty("os.name");
			   if (os.startsWith("win") || os.startsWith("Win")) {
				   //windows字体
				   prefixFont = "C:/Users/KenelLiu/Desktop/win/"+loadFont;
			   } else {
				   //linux/macos字体
				   prefixFont = "/usr/share/fonts/win/"+loadFont;
			   }
			   BaseFont stChinese = BaseFont.createFont(prefixFont, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			   Font stFontChinese = new Font(stChinese, size, style, color);
			   stFontChinese.setFamily(familyName);
			   return stFontChinese;
		   } catch (Exception e) {
			 	log.info(e.getMessage(),e);
			   return null;
		   }
	   });
   }

}
