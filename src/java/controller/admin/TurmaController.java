package controller.admin;
import java.io.IOException;
import model.TurmaDAO;
import model.Conexao;
import entidade.Turma;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "TurmaController", urlPatterns = {"/admin/TurmaController"})
public class TurmaController extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = (String) request.getParameter("acao");
        Turma turma = new Turma();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;
        if (acao == null) {
        // Defina um valor padrão ou redirecione para um erro
        acao = "Listar"; // ou algum valor adequado
        }
        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurma = turmaDAO.getAll();
                request.setAttribute("listaTurma", listaTurma);

                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurma.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
                int idAlterar = Integer.parseInt(request.getParameter("id"));
            try {
            Turma turmaParaAlterar = turmaDAO.get(idAlterar);
            request.setAttribute("turma", turmaParaAlterar);
            request.setAttribute("acao", "Alterar");
            request.setAttribute("msgError", "");
            } catch (Exception e) {
            System.out.println("Erro ao buscar turma para alteração: " + e.getMessage());
            request.setAttribute("msgError", "Falha ao carregar dados do turma.");
            }

            RequestDispatcher rdAlterar = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rdAlterar.forward(request, response);
            break;
            case "Excluir":
                // get parametro ação indicando sobre qual categoria será a ação
                // Captura o ID do turma a ser excluído
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                turmaDAO.delete(id); // Exclui o aluno do banco
                request.setAttribute("msgOperacaoRealizada", "Turma excluído com sucesso!");
            } catch (Exception e) {
                request.setAttribute("msgError", "Erro ao excluir o turma: " + e.getMessage());
            }
            // Redireciona para a listagem após exclusão
            response.sendRedirect("/aplicacaoMVC/admin/TurmaController?acao=Listar");
            break;


            case "Incluir":
                request.setAttribute("turma", turma);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                
        }
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

    // Captura o ID, caso exista (somente para Alterar ou Excluir)
    String idParam = request.getParameter("id");
    int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

    int professor_id = Integer.parseInt(request.getParameter("professor_id"));
    int disciplina_id = Integer.parseInt(request.getParameter("disciplina_id"));
    int aluno_id = Integer.parseInt(request.getParameter("aluno_id"));
    String codigo_turma = request.getParameter("codigo_turma");
    double nota = Double.parseDouble(request.getParameter("nota"));
    String btEnviar = request.getParameter("btEnviar");

    RequestDispatcher rd;

    // Verifique se o professor_id está vazio
    if (professor_id == null || professor_id.isEmpty()) {
        Turma turma = new Turma();
        request.setAttribute("turma", turma);
        request.setAttribute("acao", btEnviar);
        request.setAttribute("msgError", "É necessário preencher todos os campos");
        rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
        rd.forward(request, response);
    } else {
        Turma turma;
        if (id > 0) {
            // Criação do aluno com ID (para Alterar ou Excluir)
            turma = new Turma(id, professor_id, disciplina_id, aluno_id, codigo_turma, nota);
        } else {
            // Criação do aluno sem ID (para Incluir)
            turma = new Turma(professor_id, disciplina_id, aluno_id, codigo_turma, nota);
        }

        TurmaDAO turmaDAO = new TurmaDAO();

        try {
            switch (btEnviar) {
                case "Incluir":
                    turmaDAO.insert(turma);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                    break;
                case "Alterar":
                    turmaDAO.update(turma);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                    break;
                case "Excluir":
                    turmaDAO.delete(turma.getId());
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | ServletException ex) {
            System.out.println("Erro: " + ex.getMessage());
            throw new RuntimeException("Falha em uma query para cadastro de Turma");
        }
    }
}}