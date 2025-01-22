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
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "filtroProfessor", urlPatterns = {"/professor/*"})
public class filtroProfessor implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            System.out.println("Session ID: " + session.getId());
            Professor professor = (Professor) session.getAttribute("authUserProfessor");
            if (professor != null && !((String) professor.getNome()).isEmpty()) {
                chain.doFilter(request, response);
                System.out.println("Professor: " + professor.getNome());
            } else {
                System.out.println("Professor nao logado.");
                ((HttpServletResponse) response).sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
            }
        } else {
            System.out.println("Sessao e null.");
            ((HttpServletResponse) response).sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
        }
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
