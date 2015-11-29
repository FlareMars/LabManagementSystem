package com.libmanagement.common.tag;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class BasePathTag extends TagSupport {

	private static final long serialVersionUID = -1911916303622057369L;

	private String basePath;

	@Override  
    public int doStartTag() throws JspException {  
        JspWriter out=pageContext.getOut();  
        try {
        	ServletRequest rq = pageContext.getRequest();
        	basePath = rq.getScheme()+"://"+rq.getServerName()+":"+rq.getServerPort();
            out.print(basePath);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return SKIP_BODY;  
    }

}