package com.iot.sh.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelCellValueUtil {
    private ExcelCellValueUtil(){}


    public static void setCellStrValue(Cell cell, String valStr, CellStyle cellStyle){
        cell.setCellStyle(cellStyle);
        if(valStr!=null)
            cell.setCellValue(valStr);
    }

    public static void setCellIntValue(Cell cell, Integer valInt, CellStyle cellStyleInt){
        cell.setCellStyle(cellStyleInt);
        if(valInt!=null)
            cell.setCellValue(valInt);
    }

    public static void setCellLongValue(Cell cell, Long valLong, CellStyle cellStyleInt){
        cell.setCellStyle(cellStyleInt);
        if(valLong!=null)
            cell.setCellValue(valLong);
    }

    public static void setCellDateValue(Cell cell, Date valDate, CellStyle cellStyleDateTime){
        cell.setCellStyle(cellStyleDateTime);
        if(valDate!=null){
            cell.setCellValue(new java.sql.Date(valDate.getTime()));
        }
    }

    public static void setCellPercentValue(Cell cell, BigDecimal valBigDecimal, CellStyle cellStylePercent){
        cell.setCellStyle(cellStylePercent);
        if(valBigDecimal!=null)
            cell.setCellValue(valBigDecimal.doubleValue());
    }

    public static void setCellDoubleValue(Cell cell, BigDecimal valBigDecimal, CellStyle CellStyleDbl){
        cell.setCellStyle(CellStyleDbl);
        if(valBigDecimal!=null)
            cell.setCellValue(valBigDecimal.doubleValue());
    }

    public static void setCellValue(Cell cell, String valStr, CellStyle cellStyle,
                              CellStyle cellStyleInt, Pattern pattern){
        if(valStr!=null) {
            Matcher m = pattern.matcher(valStr);
            if (m.matches()) {
                cell.setCellStyle(cellStyleInt);
                cell.setCellValue(Long.parseLong(valStr));
            } else {
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new XSSFRichTextString(valStr));
            }
        }else{
            cell.setCellStyle(cellStyle);
        }
    }
}
