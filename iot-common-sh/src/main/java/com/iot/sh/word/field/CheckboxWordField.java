package com.iot.sh.word.field;

import lombok.Data;

/**
 * checkbox在word中使用特殊符号处理
 * @author Kenel Liu
 * {unSelectedboxSize:30,selectedBoxSize:22,fontSize:9}
 * {unSelectedboxSize:40,selectedBoxSize:28,fontSize:14}
 * {unSelectedboxSize:35,selectedBoxSize:24,fontSize:12}
 */
@Data
public class CheckboxWordField extends WordField{
    //在word Wingdings2中 使用此值[boxVal]42:表示box未选中 82:表示box已选中
    private int boxVal=42;
    private Integer fontSize=9;// box后的文字大小
    private String unSelectedBoxSize="30";//box大小,未选中时大小
    private String selectedBoxSize="22";//box大小,选中时大小
    private String text;//box框后面的文字
    //=========附加属性======//
    private boolean underline; //该选项是否有下划线
    private boolean wordBreak; //该选项处理后进行换行
    private String  extraContent;//除了选中内容,还有额外信息 如: 账号: []新增, []已有(账号:XXXXXXXXXXXXX)  中的 (账号:XXXXXXXXXXXXX) 信息 或者是 下划线_____里的内容

}
