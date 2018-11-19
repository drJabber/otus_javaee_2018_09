package rnk.l10.tags;

import rnk.l10.utils.StatsProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class StatsXTagHandler extends TagSupport {
    @Override
    public int doEndTag() throws JspException {
        try{
            Integer stats_id=(new StatsProcessor(pageContext)).store_statsx();
            HttpServletRequest rq=(HttpServletRequest) pageContext.getRequest();
            HttpServletResponse rsp=(HttpServletResponse)rq.getAttribute("resp");
            if (rsp==null){
                rsp=(HttpServletResponse)pageContext.getResponse();
            }
            rsp.addCookie(new Cookie("rnk-stats-tracker",stats_id.toString()));
            return SKIP_BODY;
        }catch(Exception ex){
            throw new JspException(ex);
        }
    }
}
