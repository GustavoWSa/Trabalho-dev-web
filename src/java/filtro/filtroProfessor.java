package filtro;

import entidade.Professor;
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

@WebFilter(filterName = "filtroRestrito", urlPatterns = {"/Professor/*"})
public class filtroProfessor implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        Professor professor = (Professor)((HttpServletRequest) request).getSession().getAttribute("professor");

        if ((professor != null) && (!((String) professor.getNome()).isEmpty())) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("http://localhost:8080/aplicacaoMVC/home");
        }
    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
