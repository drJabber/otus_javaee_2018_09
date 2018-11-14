package rnk.l10.tags;

import rnk.l10.utils.StatsProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class StatsXTagHandler extends TagSupport {


    @Override
    public int doEndTag() throws JspException {
        try{

            Integer stats_id=(new StatsProcessor(pageContext)).store_statsx();
            HttpServletResponse rsp=(HttpServletResponse)pageContext.getResponse();
            rsp.addCookie(new Cookie("rnk-stats-tracker",stats_id.toString()));
            return SKIP_BODY;

        }catch(Exception ex){
            throw new JspException(ex);
        }
    }
}
