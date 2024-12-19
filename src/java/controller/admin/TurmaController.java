package controller.admin;

import entidade.Turma;
import model.TurmaDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "TurmaController", urlPatterns = {"/admin/turma"})
public class TurmaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        TurmaDAO turmaDAO = new TurmaDAO();
        try {
            if ("list".equals(action)) {
                List<Turma> turmas = turmaDAO.listarTodos();
                request.setAttribute("turmas", turmas);
                request.getRequestDispatcher("/views/admin/listaTurma.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                turmaDAO.excluir(id);
                response.sendRedirect("turma?action=list");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao processar ação", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Turma turma = new Turma();
            turma.setProfessorId(Integer.parseInt(request.getParameter("professorId")));
            turma.setAlunoId(Integer.parseInt(request.getParameter("alunoId")));
            turma.setCodigoTurma(request.getParameter("codigoTurma"));
            turma.setNota(Double.parseDouble(request.getParameter("nota")));

            TurmaDAO turmaDAO = new TurmaDAO();
            turmaDAO.inserir(turma);

            response.sendRedirect("turma?action=list");
        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar turma", e);
        }
    }
}
