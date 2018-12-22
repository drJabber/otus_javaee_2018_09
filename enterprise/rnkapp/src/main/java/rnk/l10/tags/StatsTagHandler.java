package rnk.l10.tags;

import rnk.l10.ejb.stats.StatsProcessor;

import javax.ejb.EJB;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class StatsTagHandler extends TagSupport {

    @EJB
    StatsProcessor processor;

    @Override
    public int doEndTag() throws JspException {
        try{
            Integer stats_id=processor.store_stats(pageContext);
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
