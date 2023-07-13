package iot.sh.poi.word.field;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kenel Liu
 */
@Data
public class TableRowWordField extends WordField{
    private Map<String,WordField> rowMap=new LinkedHashMap<>();
    public TableRowWordField add(String cellKey, WordField value){
        rowMap.put(cellKey,value);
        return this;
    }
}
