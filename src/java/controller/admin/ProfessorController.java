package controller.admin;
import java.io.IOException;
import model.ProfessorDAO;
import model.Conexao;
import entidade.Professor;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ProfessorController", urlPatterns = {"/admin/ProfessorController"})
public class ProfessorController extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = (String) request.getParameter("acao");
        Professor professor = new Professor();
        ProfessorDAO professorDAO = new ProfessorDAO();
        RequestDispatcher rd;
        if (acao == null) {
        // Defina um valor padrão ou redirecione para um erro
        acao = "Listar"; // ou algum valor adequado
        }
        switch (acao) {
            case "Listar":
                ArrayList<Professor> listaProfessor = professorDAO.getAll();
                request.setAttribute("listaProfessor", listaProfessor);

                rd = request.getRequestDispatcher("/views/admin/Professor/listaProfessor.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
                int idAlterar = Integer.parseInt(request.getParameter("id"));
            try {
            Professor professorParaAlterar = professorDAO.get(idAlterar);
            request.setAttribute("professor", professorParaAlterar);
            request.setAttribute("acao", "Alterar");
            request.setAttribute("msgError", "");
            } catch (Exception e) {
            System.out.println("Erro ao buscar professor para alteração: " + e.getMessage());
            request.setAttribute("msgError", "Falha ao carregar dados do professor.");
            }

            RequestDispatcher rdAlterar = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
            rdAlterar.forward(request, response);
            break;
            case "Excluir":
                // get parametro ação indicando sobre qual categoria será a ação
                // Captura o ID do professor a ser excluído
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                professorDAO.delete(id); // Exclui o aluno do banco
                request.setAttribute("msgOperacaoRealizada", "Professor excluído com sucesso!");
            } catch (Exception e) {
                request.setAttribute("msgError", "Erro ao excluir o professor: " + e.getMessage());
            }
            // Redireciona para a listagem após exclusão
            response.sendRedirect("/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            break;


            case "Incluir":
                request.setAttribute("professor", professor);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
                rd.forward(request, response);
                
        }
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

    // Captura o ID, caso exista (somente para Alterar ou Excluir)
    String idParam = request.getParameter("id");
    int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

    String nome = request.getParameter("nome");
    String email = request.getParameter("email");
    String cpf = request.getParameter("cpf");
    String senha = request.getParameter("senha");
    String btEnviar = request.getParameter("btEnviar");

    RequestDispatcher rd;

    // Verifique se o nome está vazio
    if (nome == null || nome.isEmpty()) {
        Professor professor = new Professor();
        request.setAttribute("professor", professor);
        request.setAttribute("acao", btEnviar);
        request.setAttribute("msgError", "É necessário preencher todos os campos");
        rd = request.getRequestDispatcher("/views/admin/Professor/formProfessor.jsp");
        rd.forward(request, response);
    } else {
        Professor professor;
        if (id > 0) {
            // Criação do aluno com ID (para Alterar ou Excluir)
            professor = new Professor(id, nome, email, cpf, senha);
        } else {
            // Criação do aluno sem ID (para Incluir)
            professor = new Professor(nome, email, cpf, senha);
        }

        ProfessorDAO professorDAO = new ProfessorDAO();

        try {
            switch (btEnviar) {
                case "Incluir":
                    professorDAO.insert(professor);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                    break;
                case "Alterar":
                    professorDAO.update(professor);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                    break;
                case "Excluir":
                    professorDAO.delete(professor.getId());
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | ServletException ex) {
            System.out.println("Erro: " + ex.getMessage());
            throw new RuntimeException("Falha em uma query para cadastro de Professor");
        }
    }
}}