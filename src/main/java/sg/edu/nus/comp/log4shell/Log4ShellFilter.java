package sg.edu.nus.comp.log4shell;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class Log4ShellFilter implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        /**
         * Log4Shell Mitigation
         * Disallow jndi parameters in all endpoints
         */
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Enumeration<String> paramNames = request.getParameterNames();

        String enabled = filterConfig.getInitParameter("enabled");
        String filterRegEx = filterConfig.getInitParameter("filterRegEx"); // \$\{.*jndi:?.*\}
        if(enabled != null && enabled.equalsIgnoreCase("yes") &&
                filterRegEx != null && !filterRegEx.isEmpty()) { // Do not run if regex empty
            String name = "";
            while(paramNames.hasMoreElements()) {
                name = paramNames.nextElement();
                String input = request.getParameter(name);
                if (input != null && input.matches(filterRegEx)) { // Any param value has jndi
                    httpResponse.setContentType("text/html");
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "JNDI Forbidden!");
                    httpResponse.getWriter().print("");
                    break;
                }
            }
        }
        chain.doFilter(request, response);
    }

}