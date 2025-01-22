package filtro;

import entidade.Aluno;
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

@WebFilter(filterName = "filtroAluno", urlPatterns = {"/aluno/*"})
public class filtroAluno implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            System.out.println("Session ID: " + session.getId());
            Aluno aluno = (Aluno) session.getAttribute("authUserAluno");
            if (aluno != null && !((String) aluno.getNome()).isEmpty()) {
                chain.doFilter(request, response);
                System.out.println("Aluno: " + aluno.getNome());
            } else {
                System.out.println("Aluno nao logado.");
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
