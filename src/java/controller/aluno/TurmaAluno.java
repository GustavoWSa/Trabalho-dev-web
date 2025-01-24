package controller.aluno;
import entidade.Turma;
import entidade.Aluno;
import entidade.Professor;
import model.TurmaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
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

            case "ListarTurmasAluno": //Turmas mentoradas por professor
                HttpSession session = request.getSession(false); 
                if (session != null) {
                    Professor professorLogado = (Professor) session.getAttribute("authUserProfessor");
                    Aluno alunoLogado = (Aluno) session.getAttribute("authUserAluno");
                    if (alunoLogado != null) {
                        // Retorna turmas mentoradas pelo professor
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

            case "ListarAbertas":
                // Obtém as turmas que ainda têm menos de dois alunos
                listaTurma = turmaDAO.getTurmasAbertas();

                // Garante que a lista não seja nula
                if (listaTurma == null) {
                    listaTurma = new ArrayList<>();
                }

                // Adiciona a lista de turmas abertas como atributo na requisição
                request.setAttribute("listaTurma", listaTurma);

                // Encaminha para a página JSP de listagem de turmas
                rd = request.getRequestDispatcher("/views/aluno/InscricaoTurma.jsp");
                rd.forward(request, response);
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
    String acao = request.getParameter("acao");
    TurmaDAO turmaDAO = new TurmaDAO();

    try {
        switch (acao) {
            case "Inscrever":
                HttpSession session = request.getSession(false);
                if (session != null) {
                    // Verifica se há um aluno logado
                    Aluno alunoLogado = (Aluno) session.getAttribute("authUserAluno");
                    if (alunoLogado != null) {
                        // Obtém os dados enviados pelo formulário
                        int turmaId = Integer.parseInt(request.getParameter("id"));
                        int professorId = Integer.parseInt(request.getParameter("professor_id"));
                        int disciplinaId = Integer.parseInt(request.getParameter("disciplina_id"));
                        String codigoTurma = request.getParameter("codigo_turma");

                        // Cria a relação turma-aluno
                        Turma turmaInscricao = new Turma();
                        turmaInscricao.setId(turmaId);
                        turmaInscricao.setProfessor_id(professorId);
                        turmaInscricao.setDisciplina_id(disciplinaId);
                        turmaInscricao.setCodigoTurma(codigoTurma);
                        turmaInscricao.setAluno_id(alunoLogado.getId());

                        // Chama o método para inserir a relação no banco de dados
                        turmaDAO.incluir(turmaInscricao);

                        // Redireciona para a página de turmas abertas
                        response.sendRedirect("/aplicacaoMVC/aluno/TurmaAluno?acao=ListarAbertas");
                    } else {
                        // Caso o aluno não esteja autenticado
                        response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
                    }
                } else {
                    // Caso a sessão seja inválida
                    response.sendRedirect("/aplicacaoMVC/AutenticaController?acao=Login");
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ação inválida!");
                break;
        }
    } catch (Exception e) {
        System.out.println("Erro ao processar ação no método POST: " + e.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar a requisição.");
    }
}



    }

