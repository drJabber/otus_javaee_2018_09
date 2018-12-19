package rnk.l10.filters;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

//@WebFilter(filterName = "SetCharacterEncodingFilter", urlPatterns = "/*", asyncSupported = true)
public class SetCharacterEncodingFilter extends HttpFilter {
    private static final Logger logger =
            Logger.getLogger(SetCharacterEncodingFilter.class.getName());

    private String encoding = null;
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        try {
            Charset testCS = Charset.forName(this.encoding);
            this.active = true;
        } catch (Exception e) {
            this.active = false;
            logger.warn(encoding + " character set not supported ("+e.getMessage()+"). SetCharacterEncodingFilter de-activated.");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (active) resp.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);    }
}