package iot.sh.poi.excel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ExcelCell {
	private String field;//数据库字段或实体属性名
	private String cellName;//导出excel时,在header里写入中文字段名称
	private int  cellWidth;//导出excel时,在header里设置单元格宽度
	public ExcelCell(String cellName) {
		this(cellName,null);
	}
	public ExcelCell(String cellName,String field){
		this(cellName,field,20*256);
	}
	public ExcelCell(String cellName,String field,int cellWidth){
		this.cellName=cellName;
		this.field=field;
		this.cellWidth=cellWidth;
	}
	
}
