package com.iot.sh.word;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.iot.sh.word.field.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;
/**
 * @author Kenel Liu
 */
@Slf4j
public class SXSSFWord {
    private File templateFile; //word 模板
    private File outputFile; // 根据word模板生成文件

    private Map<String,? super WordField> wordFieldMap;//当valCDO.key=templateFile.字段,则templateFile.字段替换成valCDO.value

    public SXSSFWord(File templateFile,File outputFile,Map<String,? super WordField> wordFieldMap){
        this.templateFile=templateFile;
        this.outputFile=outputFile;
        this.wordFieldMap=wordFieldMap;
    }
    public void process() throws OpenXML4JException{
        XWPFDocument doc=null;
        FileOutputStream os=null;
        String text=null;
        try{
            doc = new XWPFDocument(OPCPackage.open(templateFile));
            //========处理段落=========//
            processParagraph(doc.getParagraphs(),this.wordFieldMap,false);
            //========处理表格数据======//
            processTable(doc);
            os=new FileOutputStream(outputFile);
            doc.write(os);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OpenXML4JException("key:"+text+";"+e.getMessage(),e);
        }finally{
            if(os!=null)try{os.close();}catch(Exception ex){}
            //不能关闭,否则模板中的变量被回写
            //if(doc!=null)try{doc.close();}catch(Exception ex){}
        }
    }

    private void processTable(XWPFDocument doc) throws XmlException{
        List<XWPFTable> tables=doc.getTables();
        if(tables==null || tables.size()==0) return;
        String copyKey=null;
        for(XWPFTable tab : tables){
            for(XWPFTableRow row : tab.getRows()){
                for(XWPFTableCell cell : row.getTableCells()){
                    copyKey=processParagraph(cell.getParagraphs(),this.wordFieldMap,true);
                    if(copyKey!=null)
                        break;
                }//XWPFTableCell
                if(copyKey!=null)
                    break;
            }//XWPFTableRow
            if(copyKey!=null){
                copyRow(tab, copyKey);
            }
            copyKey=null;
        }//XWPFTable
    }

    private void copyRow(XWPFTable table,String copyKey) throws XmlException{
        ListTableRowWordField listTableRowWordField=(ListTableRowWordField)(this.wordFieldMap.get(copyKey));
        if(listTableRowWordField==null || listTableRowWordField.getListTableRow().size()==0){
            log.warn("根据key["+copyKey+"],未找到对应填充数据");
            return;
        }
        int copyRowStart=listTableRowWordField.getCopyRowStart();
        List<TableRowWordField> listTableRow=listTableRowWordField.getListTableRow();
        //========默认情况 table共2行,第1行为标题，第二行是模板数据=======//
        XWPFTableRow srcRow=table.getRow(copyRowStart);//复制模板数据
        for (int i = copyRowStart; i <(copyRowStart+listTableRow.size()-1); i++) {
            //==========创建新行,默认将第二行数据进行复制=========//
            XWPFTableRow dstRow=table.createRow();
            SXSSFTableRow tabRow=new SXSSFTableRow(dstRow, srcRow);
            tabRow.copyTableRow(i);
        }
        //========填充数据===========//
        List<XWPFTableRow>  rows=table.getRows();
        for(int i=copyRowStart;i<rows.size();i++){
            TableRowWordField tableRowWordField=listTableRow.get(i-copyRowStart);
            Map<String,WordField> data=tableRowWordField.getRowMap();
            XWPFTableRow row=rows.get(i);
            for(XWPFTableCell cell : row.getTableCells()){
                processParagraph(cell.getParagraphs(),data,false);
            }//XWPFTableCell
        }
    }
    /**
     *
     * @param paragraphs
     * @return  true 表示是一个列表table,需要进行复制行处理,否则为普通数据替换
     * @throws XmlException
     */
    private String processParagraph(List<XWPFParagraph> paragraphs,Map<String,? super WordField> dataWordFieldMap,boolean bCopyRow) throws XmlException{
        if(paragraphs==null || paragraphs.size()==0) return null;
        for(XWPFParagraph p : paragraphs){
            List<XWPFRun> runs = p.getRuns();
            if(runs != null){
                for(XWPFRun r : runs){
                    String text = r.getText(0);
                    if(text==null)continue;
                    text=text.trim();
                    if(log.isDebugEnabled())
                        log.debug("读取text="+text);
                    if(!dataWordFieldMap.containsKey(text)) continue;
                    WordField wordField= (WordField) dataWordFieldMap.get(text);
                    String rowKey=handleWordField(p,r,wordField,bCopyRow,text);
                    if(bCopyRow && rowKey!=null){
                        //不是填充数据
                        log.info("查找到复制行标识["+text+"],当前表需要进行复制.");
                        return rowKey;//返回复制表F,其值为一个列表
                    }
                }
            }
        }
        return null;
    }
    private String handleWordField(XWPFParagraph p,XWPFRun r,WordField wordField,boolean bCopyRow,String rowKey) throws XmlException {
        return handleWordField(p,r,wordField,0,bCopyRow,rowKey);
    }

    private String handleWordField(XWPFParagraph p,XWPFRun r,WordField wordField,int pos,boolean bCopyRow,String rowKey) throws XmlException {
        String fieldName=wordField.getClass().getSimpleName();
        switch(fieldName){
            case "TextWordField":
                TextWordField textWordField=(TextWordField)wordField;
                handleTextWordField(r,textWordField,pos);
                break;
            case "ListTextWordField":
                ListTextWordField listTextWordField=(ListTextWordField)wordField;
                handleListTextWordField(p,r,listTextWordField,bCopyRow,rowKey);
                break;
            case "BreakWordField":
                r.addBreak();
                break;
            case "ListCheckBoxWordField":
                ListCheckBoxWordField listCheckBoxWordField=(ListCheckBoxWordField)wordField;
                handleListCheckboxWordField(p,r,listCheckBoxWordField);
                break;
            case "ListTableRowWordField":
                if(bCopyRow)
                    return rowKey;
                break;
            case "ImageWordField":
                ImageWordField imageWordField=(ImageWordField)wordField;
                handleImageWordField(r,imageWordField);
                break;
        }
        return null;
    }
    //=========处理普通文本=============//
    private void handleTextWordField(XWPFRun r,TextWordField textWordField,int pos){
        if(textWordField.getFontSize()!=null)
            r.setFontSize(textWordField.getFontSize());
        //==========单行========//
        if(!textWordField.isMultiline()){
            r.setText(textWordField.getText(),0);
            return;
        }
        //===========多行,对应textArea,存在回车换行数据================//
        String[] val=textWordField.getText().split("\\n");
        for(int i=0;i<val.length;i++){
            if(val[i]==null)continue;
            if(pos==0){
                r.setText(val[i],0);
            }else{
                r.addBreak();
                r.setText(val[i]);
            }
        }
    }
    private void handleListTextWordField(XWPFParagraph p,XWPFRun r, ListTextWordField listTextWordField,boolean bCopyRow,String rowKey) throws XmlException {
        int pos=0;
        for (WordField wordField:listTextWordField.getListText()) {
            handleWordField(p,r,wordField,pos,bCopyRow,rowKey);
            pos++;
        }
    }
    //=========处理checkbox=============//
    private void handleCheckboxWordField(XWPFParagraph p,XWPFRun r, CheckboxWordField checkboxWordField,int pos) throws XmlException {
        if(pos==0)
            r.setText("",0);

        int boxVal=checkboxWordField.getBoxVal();
        r.setText("   ");//增加一个空格
        if(pos>0){//不是第一个checkbox，需创建box
            r=getOrAddParagraph(p,true,false);
        }
        String hpsSize=null;
        if(boxVal==42){//未选中
            hpsSize=checkboxWordField.getUnSelectedBoxSize();
        }else{//选中
            hpsSize=checkboxWordField.getSelectedBoxSize();
        }
        //=============画checkBox=============//
        CTRPr  pRpr = getRunCTRPr(p, r);
        //CTHpsMeasure  sz = pRpr.isSetSz() ? pRpr : pRpr.addNewSz();
        CTHpsMeasure  sz = (pRpr.getSzList()==null||pRpr.getSzList().size()==0) ? pRpr.addNewSz():pRpr.getSzArray(0);
        sz.setVal(new BigInteger(hpsSize));
        //CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
        CTHpsMeasure szCs = (pRpr.getSzCsList()==null||pRpr.getSzCsList().size()==0)?  pRpr.addNewSzCs():pRpr.getSzCsArray(0);
        szCs.setVal(new BigInteger(hpsSize));
        List<CTSym> symList = r.getCTR().getSymList();
        symList.add(getCTSym("Wingdings 2", "F0" + Integer.toHexString(boxVal)));
        r=getOrAddParagraph(p,true,false);

        int fontSize=checkboxWordField.getFontSize()==null?9: checkboxWordField.getFontSize();
        r.setFontSize(fontSize);

        String checkVal=checkboxWordField.getText();
        boolean isWordBreak=checkboxWordField.isWordBreak();
        boolean isUnderline=checkboxWordField.isUnderline();
        String extra=checkboxWordField.getExtraContent();//扩展内容 在下划线里或直接在后面

        if(isUnderline){
            r.setText(checkVal);
            if(extra!=null){
                XWPFRun r1=getOrAddParagraph(p,true,false);
                r1.setFontSize(fontSize);
                r1.setText(extra);
                r1.setUnderline(UnderlinePatterns.SINGLE);
            }
        }else{
            if(extra!=null)
                checkVal=checkVal+extra;
            r.setText(checkVal);
        }
        if(isWordBreak)
            r.addBreak();
    }

    private void handleListCheckboxWordField(XWPFParagraph p,XWPFRun r,ListCheckBoxWordField listCheckBoxWordField) throws XmlException {
        int pos=0;
        for(CheckboxWordField checkboxWordField:listCheckBoxWordField.getListCheckBox()) {
            handleCheckboxWordField(p,r,checkboxWordField,pos);
            pos++;
        }
    }

    XWPFRun getOrAddParagraph(XWPFParagraph p, boolean isInsert,boolean isNewLine) {
        XWPFRun pRun = null;
        if (isInsert) {
            pRun = p.createRun();
        } else {
            if (p.getRuns() != null && p.getRuns().size() > 0) {
                pRun = p.getRuns().get(0);
            } else {
                pRun = p.createRun();
            }
        }
        if (isNewLine) {
            pRun.addBreak();
        }
        return pRun;
    }

    CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun r) {
        CTRPr pRpr = null;
        if (r.getCTR() != null) {
            pRpr = r.getCTR().getRPr();
            if (pRpr == null) {
                pRpr = r.getCTR().addNewRPr();
            }
        } else {
            pRpr = p.getCTP().addNewR().addNewRPr();
        }
        return pRpr;
    }

    CTSym getCTSym(String wingType, String charStr) throws XmlException {
        CTSym sym = CTSym.Factory
                .parse("<xml-fragment w:font=\""
                               + wingType
                               + "\" w:char=\""
                               + charStr
                               + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");
        return sym;
    }
    //===================处理图片================//
    private void handleImageWordField(XWPFRun r,ImageWordField imageWordField){
        //====去掉替换的文字===、、
        r.setText("",0);
        String fileName=imageWordField.getPictureName()==null?imageWordField.getPicture().getName()
                :imageWordField.getPictureName();
        try(FileInputStream imageData=new FileInputStream(imageWordField.getPicture())){
            r.addPicture(imageData, imageWordField.getPictureType(), fileName,
                         Units.toEMU(imageWordField.getWidth()),
                         Units.toEMU(imageWordField.getHeight()));
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }
}
