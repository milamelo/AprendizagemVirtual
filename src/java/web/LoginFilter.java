/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import negocio.entidade.Usuario;

/**
 *
 * @author Camila
 */
@WebFilter(urlPatterns = "/pages/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Usuario usuario = null;
        HttpSession httpSession = ((HttpServletRequest) request).getSession(false);
    
        if (httpSession != null) {
            usuario = (Usuario) httpSession.getAttribute("usuarioLogado");
        }

        if (usuario == null) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletRequest) request).getSession().invalidate();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/index.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
