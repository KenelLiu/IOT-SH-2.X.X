package iot.sh.poi.excel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ExcelFactory {

    public  static SXSSFExcel createExcel(List<ExcelCell> listExcelCell) throws IOException{
        return createExcel(null,listExcelCell);
    }

    public  static SXSSFExcel createExcel(String title,List<ExcelCell> listExcelCell) throws IOException {
        return new SXSSFExcel(title,listExcelCell);
    }


}
