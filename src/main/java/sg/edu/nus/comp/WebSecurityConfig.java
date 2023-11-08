package sg.edu.nus.comp;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'none'; " +
                                "img-src 'self'; " +
                                "script-src https://maxcdn.bootstrapcdn.com https://ajax.googleapis.com; " +
                                "style-src 'self' https://maxcdn.bootstrapcdn.com; " +
                                "font-src 'self' https://maxcdn.bootstrapcdn.com; " +
                                "frame-ancestors 'none';")
                        .and()
                        .addHeaderWriter(new HeaderWriter() {
                            @Override
                            public void writeHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
                                // Cookie Security Configurations
                                addCookieSecurityAttributes(httpServletRequest, httpServletResponse);
                            }
                        })
                )
        );
    }

    /**
     * REF:https://stackoverflow.com/questions/42998367/same-site-flag-for-session-cookie-in-spring-security
     * @param response
     */
    private void addCookieSecurityAttributes(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        // Prepare cookie flags
        Collection<String> arrFlags = new ArrayList<>();
        arrFlags.add("HttpOnly");
        arrFlags.add("SameSite=Strict");
        arrFlags.add("Path=" + httpServletRequest.getContextPath());
        arrFlags.add("Domain=" + httpServletRequest.getServerName());
        String flags = String.join(";", arrFlags);

        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        // there can be multiple Set-Cookie attributes
        for (String header : headers) {
            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE,
                        String.format("%s; %s", header, flags));
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE,
                    String.format("%s; %s", header, flags));
        }
    }
}