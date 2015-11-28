package com.libmanagement.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by FlareMars on 2015/10/20
 */
public class CutPostfixTag extends TagSupport {

    private String value;

    public CutPostfixTag() {

    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print(value.substring(0,value.lastIndexOf('-')));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
