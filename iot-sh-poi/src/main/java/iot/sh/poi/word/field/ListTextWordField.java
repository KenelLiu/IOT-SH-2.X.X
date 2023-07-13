package iot.sh.poi.word.field;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * listText 里数据为TextWordField,BreakWordField 字段
 * 人工进行分行
 * 比如:有多个电话时
 * 显示： 电话1,电话2
 *       电话3,电话4
 * @author Kenel Liu
 */
@Data
public class ListTextWordField extends WordField{
    private List<WordField> listText=new ArrayList<>();
    public ListTextWordField add(WordField wordField){
        Objects.requireNonNull(wordField, "ListTextWordField");
        listText.add(wordField);
        return this;
    }
}
