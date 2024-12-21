package controller.admin;
import java.io.IOException;
import model.Conexao;
import entidade.Administrador;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministradorDAO;

@WebServlet(name = "AdminController", urlPatterns = {"/admin/AdministradorController"})
public class AdministradorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = (String) request.getParameter("acao");
        Administrador administrador = new Administrador();
        AdministradorDAO administradorDAO = new AdministradorDAO();
        RequestDispatcher rd;

        if (acao == null) {
            // Defina um valor padrão ou redirecione para um erro
            acao = "Listar"; // ou algum valor adequado
        }

        switch (acao) {
            case "Listar":
                // Listar todos os administradores
                ArrayList<Administrador> listaAdministradores = administradorDAO.getAll();
                request.setAttribute("listaAdministradores", listaAdministradores);

                rd = request.getRequestDispatcher("/views/admin/Administrador/listaAdministradores.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
                // Captura o ID do administrador a ser alterado
                int idAlterar = Integer.parseInt(request.getParameter("id"));

                try {
                    // Chama o método para buscar o administrador pelo ID
                    Administrador administradorParaAlterar = administradorDAO.get(idAlterar);
                    if (administradorParaAlterar != null) {
                        // Passa o administrador para o JSP
                        request.setAttribute("administrador", administradorParaAlterar);
                        request.setAttribute("acao", "Alterar");
                        request.setAttribute("msgError", "");
                    } else {
                        // Caso não encontre o administrador
                        request.setAttribute("msgError", "Administrador não encontrado.");
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao buscar administrador para alteração: " + e.getMessage());
                    request.setAttribute("msgError", "Falha ao carregar dados do administrador.");
                }

                rd = request.getRequestDispatcher("/views/admin/Administrador/formAdministrador.jsp");
                rd.forward(request, response);
                break;

            case "Excluir":
                // Captura o ID do administrador a ser excluído
                int idExcluir = Integer.parseInt(request.getParameter("id"));
                try {
                    administradorDAO.delete(idExcluir); // Exclui o administrador do banco
                    request.setAttribute("msgOperacaoRealizada", "Administrador excluído com sucesso!");
                } catch (Exception e) {
                    request.setAttribute("msgError", "Erro ao excluir o administrador: " + e.getMessage());
                }
                // Redireciona para a listagem após exclusão
                response.sendRedirect("/aplicacaoMVC/admin/AdministradorController?acao=Listar");
                break;

            case "Incluir":
                // Passa o administrador vazio para o formulário
                request.setAttribute("administrador", administrador);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Administrador/formAdministrador.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        // Captura o ID, caso exista (somente para Alterar ou Excluir)
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String aprovado = request.getParameter("aprovado");
        String endereco = request.getParameter("endereco");
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        // Verifique se o nome está vazio
        if (nome == null || nome.isEmpty()) {
            Administrador administrador = new Administrador();
            request.setAttribute("administrador", administrador);
            request.setAttribute("acao", btEnviar);
            request.setAttribute("msgError", "É necessário preencher todos os campos");
            rd = request.getRequestDispatcher("/views/admin/Administrador/formAdministrador.jsp");
            rd.forward(request, response);
        } else {
            Administrador administrador;
            if (id > 0) {
                // Criação do Administrador com ID (para Alterar ou Excluir)
                administrador = new Administrador(nome, cpf, senha, aprovado, endereco);
            } else {
                // Criação do administrador sem ID (para Incluir)
                administrador = new Administrador(nome, cpf, senha, aprovado, endereco);
            }

            AdministradorDAO administradorDAO = new AdministradorDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        administradorDAO.insert(administrador);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;
                    case "Alterar":
                        administradorDAO.update(administrador);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;
                    case "Excluir":
                        administradorDAO.delete(administrador.getId());
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/AdministradorController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
                throw new RuntimeException("Falha em uma query para cadastro de Administrador");
            }
        }
    }
}
