package iot.sh.poi.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Slf4j
public class SXSSFExcel {

    private SXSSFWorkbook wb;
    private Sheet sheet;
    private CellStyle cellStyle;
    private int rowIndex;
    private File tmpExcelFile;

    public  SXSSFExcel(List<ExcelCell> listExlCell){
        this(null, listExlCell);
    }
    public SXSSFExcel(String title,List<ExcelCell> listExlCell){
        this(null, title, listExlCell);
    }
    public SXSSFExcel(String sheetName,String title,List<ExcelCell> listExlCell){
        this(sheetName, title, listExlCell,500);
    }

    public SXSSFExcel(String sheetName,String title,List<ExcelCell> listExlCell,int maxMemRows){
        //keep 500 rows in memory, exceeding rows will be flushed to disk
        if(maxMemRows<=0)
            maxMemRows=500;
        wb=new SXSSFWorkbook(maxMemRows);
        if(sheetName==null)
            sheet=wb.createSheet();
        else
            sheet=wb.createSheet(sheetName);

        CellStyle titleStyle=null;
        if(title!=null){
            titleStyle=wb.createCellStyle();
            Font titleFont=wb.createFont();
            titleFont.setFontHeight((short)350);
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        cellStyle=wb.createCellStyle();
        setLineStyle(cellStyle);
        //创建title
        Cell cell;
        //int len=listExlBean.size();
        RichTextString value;
        if(title!=null){
            Row row=sheet.createRow(rowIndex);
            row.setHeight((short)500);
            rowIndex++;

            for(int i=0;i<listExlCell.size();i++){
                cell=row.createCell(i);
                if(i==0){
                    value=new XSSFRichTextString(title);
                    cell.setCellValue(value);
                }
                cell.setCellStyle(titleStyle);
                //设置宽度
                sheet.setColumnWidth(i,listExlCell.get(i).getCellWidth());
            }
            //显示标题
            if((listExlCell.size()-1)>0){
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, listExlCell.size()-1));
            }
        }
        //创建cols
        Row colRow=sheet.createRow(rowIndex);
        for(int i=0;i<listExlCell.size();i++){
            cell=colRow.createCell(i);
            cell.setCellStyle(cellStyle);
            if(title==null){//若没有标题,则通过输出值设置
                sheet.setColumnWidth(i,listExlCell.get(i).getCellWidth());
            }
            //设置colname标题
            value=new XSSFRichTextString(listExlCell.get(i).getCellName());
            cell.setCellValue(value);
        }
    }
    public  void setLineStyle(CellStyle style){
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
    public void setCompressTempFiles(boolean compress){
        wb.setCompressTempFiles(compress);
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public CellStyle createCellStyleInt(){
        return createCellStyleInt(null);
    }

    public CellStyle createCellStyleInt(String format){
        if(format==null || format.trim().length()==0)
            format="##############";
            //format="#,##0";
        return createCellStyle(format);
    }

    public CellStyle createCellStyleDbl(){
        return createCellStyleDbl(null);
    }
    public CellStyle createCellStyleDbl(String format) {
        if(format==null || format.trim().length()==0)
            format="#############0.00";
        return createCellStyle(format);
    }

    public CellStyle createCellStylePercent(){
        return createCellStylePercent(null);
    }

    public CellStyle createCellStylePercent(String format){
        if(format==null || format.trim().length()==0)
            format="0.00%";
        return createCellStyle(format);
    }

    public CellStyle createCellStyleDate(){
        return createCellStyleDate(null);
    }
    public CellStyle createCellStyleDate(String format){
        if(format==null || format.trim().length()==0)
            format="yyyy-MM-dd";
        format=DateFormatConverter.convert(Locale.CHINESE, format);
        return createCellStyle(format);
    }

    public CellStyle createCellStyle(String format){
        DataFormat df = wb.createDataFormat();
        CellStyle colStyle=wb.createCellStyle();
        setLineStyle(colStyle);
        colStyle.setDataFormat(df.getFormat(format));
        return colStyle;
    }

    public void outputTempFile() throws IOException{
        outputTempFile(System.getProperty("java.io.tmpdir"));
    }

    public void outputTempFile(String path) throws IOException{
        //重新读取临时文件，通过流形式下载到客户端
        outputTempFile(path,true);
    }

    public void outputTempFile(String path,boolean isDir) throws IOException{
        String tmpFile=null;
        if(isDir){
            File file=new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            tmpFile=path+"/"+System.currentTimeMillis()+".xlsx";
        }else{
            tmpFile=path;//path全路径文件
        }

        //=======输出文件=========//
        FileOutputStream out = new FileOutputStream(tmpFile);
        wb.write(out);
        // dispose of temporary files backing this workbook on disk
        out.close();
        wb.dispose();
        tmpExcelFile=new File(tmpFile);
    }

    public void outputTempFile(String path,String excelName) throws IOException{
        //重新读取临时文件，通过流形式下载到客户端
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        String tmpFile=path+"/"+excelName;
        outputTempFile(tmpFile,false);
    }

    public void close() throws IOException {
        wb.close();
    }
    public SXSSFWorkbook getWb() {
        return wb;
    }
    public Sheet getSheet() {
        return sheet;
    }
    public int getRowIndex() {
        return rowIndex;
    }

    public void resetRowIndex(int rowIndex){
        this.rowIndex=rowIndex;
    }
    public File getTempFile() {
        return tmpExcelFile;
    }
}
