package controller.admin;

import model.ProfessorDAO;
import entidade.Professor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProfessorController", urlPatterns = {"/ProfessorController"})
public class ProfessorController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "editar":
                editarProfessor(request, response);
                break;
            case "excluir":
                excluirProfessor(request, response);
                break;
            default:
                listarProfessores(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "salvar":
                salvarProfessor(request, response);
                break;
            default:
                listarProfessores(request, response);
                break;
        }
    }

    private void salvarProfessor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");

        Professor professor = new Professor(nome, email, cpf, senha);
        ProfessorDAO dao = new ProfessorDAO();

        try {
            dao.inserir(professor);
            response.sendRedirect("ProfessorController");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editarProfessor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProfessorDAO dao = new ProfessorDAO();
        try {
            Professor professor = dao.getProfessor(id);
            request.setAttribute("professor", professor);
            request.getRequestDispatcher("/views/admin/formProfessor.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void excluirProfessor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProfessorDAO dao = new ProfessorDAO();
        try {
            dao.excluir(id);
            response.sendRedirect("ProfessorController");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listarProfessores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProfessorDAO dao = new ProfessorDAO();
        try {
            request.setAttribute("professores", dao.listarProfessores());
            request.getRequestDispatcher("/views/admin/listaProfessor.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
