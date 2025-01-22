package controller.aluno;
import entidade.Turma;
import entidade.Aluno;
import entidade.Professor;
import model.TurmaDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "TurmaController", urlPatterns = {"/aluno/TurmaAluno"})
public class TurmaAluno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        if (acao == null) {
            acao = "ListarTurmasAluno";
        }

        switch (acao) {
            case "Listar":
                ArrayList<Turma> listaTurma = turmaDAO.getAll();
                if (listaTurma == null) {
                    listaTurma = new ArrayList<>();
                }
                request.setAttribute("listaTurma", listaTurma);
                rd = request.getRequestDispatcher("/views/professor/listaTurmas.jsp");
                rd.forward(request, response);
                break;

            case "ListarTurmasAluno":
                HttpSession session = request.getSession(false); // Retrieve existing session only once
                if (session != null) {
                    Professor professorLogado = (Professor) session.getAttribute("authUserProfessor");
                    Aluno alunoLogado = (Aluno) session.getAttribute("authUserAluno");
                    if (alunoLogado != null) {
                        // Retrieve turmas mentored by the professor
                        System.out.println("Aluno ID: " + alunoLogado.getId());
                        listaTurma = turmaDAO.getMatriculadas(alunoLogado.getId());
                        request.setAttribute("listaTurma", listaTurma);
                        rd = request.getRequestDispatcher("/views/aluno/listaNotas.jsp");
                        System.out.println("Requested URL: " + request.getRequestURL());
                        System.out.println("Query string: " + request.getQueryString());

                        rd.forward(request, response);
                    } else {
                        response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
                    }
                } else {
                    response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
                }
                break;



            case "Alterar":
                try {
                    int idAlterar = Integer.parseInt(request.getParameter("id"));
                    Turma turmaParaAlterar = turmaDAO.get(idAlterar);
                    request.setAttribute("turma", turmaParaAlterar);
                    request.setAttribute("acao", "Alterar");
                    rd = request.getRequestDispatcher("/views/admin/Turmas/formTurma.jsp");
                    rd.forward(request, response);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar turma para alteração: " + e.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao alterar turma.");
                }
                break;

            case "Excluir":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    turmaDAO.delete(id);
                    response.sendRedirect("/professor/TurmaController?acao=Listar");
                } catch (Exception e) {
                    System.out.println("Erro ao excluir turma: " + e.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao excluir turma.");
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação inválida!");
                break;
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String btEnviar = request.getParameter("btEnviar");
        TurmaDAO turmaDAO = new TurmaDAO();

        try {
            // Get the parameters for the turma update
            int id = Integer.parseInt(request.getParameter("id"));
            int professorId = Integer.parseInt(request.getParameter("professor_id"));
            int disciplinaId = Integer.parseInt(request.getParameter("disciplina_id"));
            int alunoId = Integer.parseInt(request.getParameter("aluno_id"));
            String codigoTurma = request.getParameter("codigo_turma");
            double nota = Double.parseDouble(request.getParameter("nota"));

            Turma turma = new Turma(id, professorId, disciplinaId, alunoId, codigoTurma, nota);

            switch (btEnviar) {
                case "Incluir":
                    turmaDAO.insert(turma);
                    break;
                case "Alterar":
                    turmaDAO.update(turma); // Updates the nota here
                    break;
                case "Excluir":
                    turmaDAO.delete(turma.getId());
                    break;
                default:
                    throw new RuntimeException("Operação desconhecida.");
            }

            response.sendRedirect("/professor/TurmaController?acao=Listar");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar a operação.");
        }
    }
    }

