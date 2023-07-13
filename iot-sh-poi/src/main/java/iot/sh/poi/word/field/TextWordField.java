package iot.sh.poi.word.field;

import lombok.Data;

/**
 * @author Kenel Liu
 */
@Data
public class TextWordField extends WordField{
    private String text; //替换word模板里的内容
    private boolean multiline;//是否是多行 对应html textArea 存在换车换行处理
    private Integer fontSize;//默认与模板字体大小一致
}
