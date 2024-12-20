package controller.admin;

import entidade.Turma;
import model.TurmaDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
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
        String acao = request.getParameter("acao");
        Turma turma = new Turma();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurmas = turmaDAO.getAll();
                request.setAttribute("listaTurmas", listaTurmas);
                rd = request.getRequestDispatcher("/views/admin/Turmas/listaTurma.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                turma = turmaDAO.get(id);
                request.setAttribute("turma", turma);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                request.setAttribute("turma", turma);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int professorId = Integer.parseInt(request.getParameter("professor_id"));
        int disciplinaId = Integer.parseInt(request.getParameter("disciplina_id"));
        int alunoId = Integer.parseInt(request.getParameter("aluno_id"));
        String codigoTurma = request.getParameter("codigo_turma");
        double nota = Double.parseDouble(request.getParameter("nota"));
        String acao = request.getParameter("acao");

        // Validar campos obrigatórios (exemplo: codigoTurma)
        if (codigoTurma.isEmpty()) {
            request.setAttribute("msgError", "O campo Código da Turma é obrigatório.");
            request.setAttribute("acao", acao);
            request.setAttribute("turma", new Turma(id, professorId, disciplinaId, alunoId, codigoTurma, nota));

            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
            rd.forward(request, response);
        } else {
            Turma turma = new Turma(id, professorId, disciplinaId, alunoId, codigoTurma, nota);
            TurmaDAO turmaDAO = new TurmaDAO();
            RequestDispatcher rd;

            try {
                switch (acao) {
                    case "Incluir":
                        if (turmaDAO.insert(turma)) {
                            request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        } else {
                            request.setAttribute("msgError", "Erro ao incluir turma.");
                        }
                        break;

                    case "Alterar":
                        if (turmaDAO.update(turma)) {
                            request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        } else {
                            request.setAttribute("msgError", "Erro ao alterar turma.");
                        }
                        break;

                    case "Excluir":
                        if (turmaDAO.delete(id)) {
                            request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        } else {
                            request.setAttribute("msgError", "Erro ao excluir turma.");
                        }
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha em uma query para cadastro de Turma");
            }
        }
    }
}
