package controller.admin;

import entidade.Disciplina;
import model.DisciplinaDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DisciplinaController", urlPatterns = {"/admin/DisciplinaController"})
public class DisciplinaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        Disciplina disciplina = new Disciplina();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Disciplina> listaDisciplinas = disciplinaDAO.getAll();
                request.setAttribute("listaDisciplinas", listaDisciplinas);
                rd = request.getRequestDispatcher("/views/admin/Disciplinas/listaDisciplinas.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
                disciplina = disciplinaDAO.get(id);
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/Disciplinas/formDisciplina.jsp");
                rd.forward(request, response);
                break;

            case "Incluir":
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("acao", acao);
                rd = request.getRequestDispatcher("/views/admin/Disciplinas/formDisciplina.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String requisito = request.getParameter("requisito");
        String ementa = request.getParameter("ementa");
        int cargaHoraria = Integer.parseInt(request.getParameter("carga_horaria"));
        String acao = request.getParameter("acao");

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        RequestDispatcher rd;

        if (nome.isEmpty()) {
            request.setAttribute("msgError", "O nome da disciplina é
