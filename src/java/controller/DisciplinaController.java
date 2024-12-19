package controller;

import entidade.Disciplina;
import model.DisciplinaDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DisciplinaController", urlPatterns = {"/disciplina"})
public class DisciplinaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try (Connection connection = getConnection()) {
            DisciplinaDAO dao = new DisciplinaDAO(connection);

            if (action.equals("list")) {
                List<Disciplina> disciplinas = dao.listAll();
                request.setAttribute("disciplinas", disciplinas);
                RequestDispatcher rd = request.getRequestDispatcher("/views/admin/listaDisciplinas.jsp");
                rd.forward(request, response);

            } else if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Disciplina disciplina = dao.findById(id);
                request.setAttribute("disciplina", disciplina);
                RequestDispatcher rd = request.getRequestDispatcher("/views/admin/formDisciplina.jsp");
                rd.forward(request, response);

            } else if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("disciplina?action=list");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection()) {
            DisciplinaDAO dao = new DisciplinaDAO(connection);

            int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
            String nome = request.getParameter("nome");
            String requisito = request.getParameter("requisito");
            String emetenta = request.getParameter("emetenta");
            int cargaHoraria = Integer.parseInt(request.getParameter("cargaHoraria"));

            Disciplina disciplina = new Disciplina(id, nome, requisito, emetenta, cargaHoraria);

            if (id == 0) {
                dao.create(disciplina);
            } else {
                dao.update(disciplina);
            }

            response.sendRedirect("disciplina?action=list");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return (Connection) getServletContext().getAttribute("connection");
    }
}
