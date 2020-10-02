package io.github.lmikoto.ipersistence.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyang
 * 2020/9/27 7:41 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericTokeParser {

    private String openToken;

    private String closeToken;

    private TokenHandler tokenHandler;

    public String parse(String text){
        if(text == null || text.isEmpty()){
            return "";
        }
        int start = text.indexOf(openToken);
        if(start == -1){
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        StringBuffer builder = new StringBuffer();
        StringBuffer expression = null;
        while (start > -1){
            // 转译字符不处理
            if(start > 0 && src[start -1] == '\\'){
                offset = start + openToken.length();
            }else {
                if(expression == null){
                    expression = new StringBuffer();
                }else{
                    expression.setLength(0);
                }
                builder.append(src,offset,start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken,offset);
                while (end > -1){
                    if(end > offset && src[end - 1] == '\\'){
                        expression.append(src,offset,end - offset);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken,offset);
                    }else{
                        expression.append(src,offset,end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if(end == -1){
                    builder.append(src,start, src.length - start);
                    offset = src.length;
                }else {
                    builder.append(tokenHandler.handlerToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken,offset);
        }
        if(offset < src.length){
            builder.append(src,offset,src.length - offset);
        }
        return builder.toString();
    }
}
