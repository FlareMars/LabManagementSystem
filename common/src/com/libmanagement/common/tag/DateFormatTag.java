package com.libmanagement.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by FlareMars on 2015/10/19
 */
public class DateFormatTag extends TagSupport {

    private Long time;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public DateFormatTag() {

    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            Date date = new Date(time);
            out.print(DATE_FORMAT.format(date));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
