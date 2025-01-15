package controller;

import entidade.Professor;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ProfessorDAO;

/**
 *
 * @author Leonardo
 */
@WebServlet(name = "AutenticaProfessorController", urlPatterns = {"/AutenticaProfessorController"})
public class AutenticaProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/views/autenticacao/formLoginProfessor.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        // pegando os parâmetros do request
        String cpf_user = request.getParameter("cpf");
        String senha_user = request.getParameter("senha");
        if (cpf_user.isEmpty() || senha_user.isEmpty()) {
            // dados não foram preenchidos retorna ao formulário
            request.setAttribute("msgError", "Usuário e/ou senha incorreto");
            rd = request.getRequestDispatcher("/views/autenticacao/formLoginProfessor.jsp");
            rd.forward(request, response);

        } else {
            Professor professorObtido;
            Professor professor = new Professor(cpf_user, senha_user);
            ProfessorDAO ProfessorDAO = new ProfessorDAO();
            try {
                professorObtido = ProfessorDAO.Logar(professor);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha na query para Logar");
            }

            if (professorObtido.getId() != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("professor", professorObtido);

                rd = request.getRequestDispatcher("/admin/dashboard");
                rd.forward(request, response);

            } else {
                request.setAttribute("msgError", "Usuário e/ou senha incorreto");
                rd = request.getRequestDispatcher("/views/autenticacao/formLoginProfessor.jsp");
                rd.forward(request, response);

            }
        }
    }

}
