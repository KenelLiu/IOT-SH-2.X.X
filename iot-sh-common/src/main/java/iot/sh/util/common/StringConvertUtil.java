package iot.sh.util.common;

/**
 * @author Kenel Liu
 */
public class StringConvertUtil {
    private StringConvertUtil(){}
    public static String FormatTextForXML(String strText)
    {
        if(strText==null)
        {
            return null;
        }
        StringBuilder strbText=new StringBuilder(strText.length());
        int nLength=strText.length();
        for(int i=0;i<nLength;i++)
        {
            char ch=strText.charAt(i);
            switch(ch)
            {
                case '&':
                    strbText.append("&amp;");
                    break;
                case '>':
                    strbText.append("&gt;");
                    break;
                case '<':
                    strbText.append("&lt;");
                    break;
                case '\'':
                    strbText.append("&apos;");
                    break;
                case '\"':
                    strbText.append("&quot;");
                    break;
                case '\r':
                    strbText.append("");
                    break;
                case '\n':
                    strbText.append("&#xa;");
                    break;
                default:
                    strbText.append(ch);
                    break;
            }
        }

        return strbText.toString();
    }


    /**
     * 参见
     * http://www.json.org/
     * @param strText
     * @return
     */
    public static String FormatTextForJson(String strText)
    {
        if(strText==null)return null;
        int len= strText.length();
        StringBuilder sb= new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = strText.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
    public static String FormatTextForHTML(String strText){
        return FormatTextForHTML(strText,null);
    }
    /**
     *
     * @param strText
     * @param excludeChar 字符不做处理
     * @return
     */
    public static String FormatTextForHTML(String strText,char[] excludeChar){
        if(strText==null)return null;
        int len= strText.length();
        StringBuilder sb= new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = strText.charAt(i);
            //=========找到排除的字符=======//
            if(excludeChar!=null && excludeChar.length>0){
                boolean find=false;
                for(int k=0;k<excludeChar.length;k++){
                    if(excludeChar[k]==c){
                        sb.append(c);
                        find=true;
                        break;
                    }
                }
                if(find){
                    continue;
                }
            }
            switch (c){
                case '\'':
                    sb.append("&#039;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\\':
                    //sb.append("\\\\");
                    sb.append("&#092;");
                    break;
                case '/':
                    sb.append("&#47;");
                    break;
                case '©':
                    sb.append("&copy;");
                    break;
                case '®':
                    sb.append("&reg;");
                    break;
                case '¥':
                    sb.append("&yen;");
                    break;
                case '€':
                    sb.append("&euro;");
                    break;
                case '™':
                    sb.append("&#153;");
                    break;
                case ' ':
                    sb.append("&nbsp;");
                    break;
                case '\r':
                    break;
                case '\n':
                    sb.append("<br/>");
                    break;
                default:
                    sb.append(c);
            }
        }
        return new String(sb.toString());
    }

    public static String FormatTextForMixHTMLJSON(String strText){
        if(strText==null)return null;
        int len= strText.length();
        StringBuilder sb= new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = strText.charAt(i);
            switch (c) {
                case '\'':
                    sb.append("&#039;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("\\&quot;");
                    break;
                case '\\':
                    //sb.append("\\\\");
                    sb.append("\\&#092;");
                    break;
                case '/':
                    sb.append("&#47;");
                    break;
                case '©':
                    sb.append("&copy;");
                    break;
                case '®':
                    sb.append("&reg;");
                    break;
                case '¥':
                    sb.append("&yen;");
                    break;
                case '€':
                    sb.append("&euro;");
                    break;
                case '™':
                    sb.append("&#153;");
                    break;
                case ' ':
                    sb.append("&nbsp;");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\r':
                    break;
                case '\n':
                    sb.append("<br/>");
                    break;
                default:
                    sb.append(c);
            }
        }
        return new String(sb.toString());
    }
}
