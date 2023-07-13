package iot.sh.poi.word.field;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 若是checkbox
 * word模板里的一个field对应ListCheckBoxWordField,包含所有 所有的checkbox【box大小,是否选中,是否有下划线】等信息
 * @author Kenel Liu
 */
@Data
public class ListCheckBoxWordField extends WordField{
    private List<CheckboxWordField> listCheckBox=new ArrayList<>();

    public ListCheckBoxWordField add(CheckboxWordField checkboxWordField){
        Objects.requireNonNull(checkboxWordField,"checkboxWordField");
        listCheckBox.add(checkboxWordField);
        return this;
    }
}
