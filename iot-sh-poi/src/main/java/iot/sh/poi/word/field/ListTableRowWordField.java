package iot.sh.poi.word.field;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kenel Liu
 */
@Data
public class ListTableRowWordField extends WordField{
    //==========copyRowStart 待复制的行模板数据 所在行数=======//
    //========copyRowStart 默认请情况:table共2行,第1行为标题，第二行是模板数据=======//
    private int copyRowStart=1;
    private List<TableRowWordField> listTableRow=new ArrayList<>();
    public ListTableRowWordField add(TableRowWordField tableRowWordField){
        Objects.requireNonNull(tableRowWordField, "tableRowWordField");
        listTableRow.add(tableRowWordField);
        return this;
    }
}
