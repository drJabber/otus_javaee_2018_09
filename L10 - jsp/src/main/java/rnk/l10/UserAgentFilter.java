package rnk.l10;

import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserAgentFilter extends HttpFilter {
    private ServletContext context;

    @Override
    public void init(FilterConfig config) throws ServletException {
        Map<String,Integer> limits=new HashMap<String,Integer>();
        List<String> params= Collections.list(config.getInitParameterNames());

        Collections.list(config.getInitParameterNames())
                .stream()
                .filter(n->n.startsWith("user-agent-"))
                .forEach(n->limits.put(n.substring(11,n.length()).toUpperCase(), Integer.parseInt(config.getInitParameter(n))));

        context=config.getServletContext();

        context.setAttribute("user-agent-limits", limits);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        boolean browser_invalid=false;
        List<Cookie> found_cookies=(req.getCookies()==null)?
                null:
                Arrays.stream(req.getCookies()).filter(c->c.getName()=="valid-browser").collect(Collectors.toList()) ;
        if ((found_cookies==null) || (found_cookies.size()==0) || (found_cookies.get(0).getValue()!="Y")){
            String agent=req.getHeader("User-Agent");
            Map<String, Integer> limits=(Map<String, Integer>)context.getAttribute("user-agent-limits") ;
            Parser parser=new Parser();
            Client parsed=parser.parse(agent);

            List<String> found= limits.keySet().stream().filter(k->parsed.userAgent.family.toUpperCase().contains(k.toUpperCase())).collect(Collectors.toList());
            if (found.size()>0){
                Integer limit=limits.get(found.get(0));
                if (Integer.parseInt(parsed.userAgent.major)<limit){
                    res.sendRedirect("browser-invalid");
                }else{
                    res.addCookie(new Cookie("valid-browser","true"));
                }

            }
        }


        super.doFilter(req, res, chain);
    }
}
