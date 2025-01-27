package filtro;

import entidade.Administrador;
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

@WebFilter(filterName = "filtroRestrito", urlPatterns = {"/admin/*"})
public class filtroRestrito implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        // Obtém a sessão, mas retorna null se não existir
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Administrador administrador = (Administrador) httpRequest.getSession(false).getAttribute("authUserAdmin");

        if (httpRequest.getSession(false) != null) {
            // Tenta obter o atributo da sessão
            administrador = (Administrador) httpRequest.getSession(false).getAttribute("authUserAdmin");
        }

        if (administrador != null && administrador.getNome() != null && !administrador.getNome().isEmpty()) {
            // Se o administrador está autenticado, segue com o filtro
            System.out.println("Admin: " + administrador.getNome());
            System.out.println("Id admin: " + administrador.getId());
            chain.doFilter(request, response);
        } else {
            // Redireciona para a página inicial se o administrador não estiver autenticado
            System.err.println("Administrador não autenticado ou sessão inválida.");
            httpResponse.sendRedirect("http://localhost:8080/aplicacaoMVC/home");
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

