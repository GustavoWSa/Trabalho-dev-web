package controller.admin;
import java.io.IOException;
import model.AlunoDAO;
import model.Conexao;
import entidade.Aluno;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarAluno", urlPatterns = {"/admin/RegistrarAluno"})
public class RegistrarAluno extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Aluno aluno = new Aluno();
        AlunoDAO alunoDAO = new AlunoDAO();
        RequestDispatcher rd;

        switch (acao) {
            case "Listar":
                ArrayList<Aluno> listaAlunos = alunoDAO.ListaDeAlunos();
                request.setAttribute("listaAlunos", listaAlunos);

                rd = request.getRequestDispatcher("/views/admin/Alunos/listaAlunos.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                int id = Integer.parseInt(request.getParameter("id"));
            {
                try {
                    aluno = alunoDAO.getAluno(id);
                } catch (Exception ex) {
                    Logger.getLogger(RegistrarAluno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

                request.setAttribute("aluno", aluno);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
                rd.forward(request, response);
                break;


            case "Incluir":
                request.setAttribute("aluno", aluno);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
                rd.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = request.getParameter("id").isEmpty() ? 0 : Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String celular = request.getParameter("celular");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String endereco = request.getParameter("endereco");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep");
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        if (nome.isEmpty() || email.isEmpty() || celular.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            Aluno aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);

            request.setAttribute("aluno", aluno);
            request.setAttribute("acao", btEnviar);
            request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");

            rd = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
            rd.forward(request, response);
        } else {
            Aluno aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
            AlunoDAO alunoDAO = new AlunoDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        alunoDAO.Inserir(aluno);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                        break;
                    case "Alterar":
                        alunoDAO.Alterar(aluno);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                        break;
                    case "Excluir":
                        alunoDAO.Excluir(aluno);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/RegistrarAluno?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Erro ao executar operação no banco de dados.");
            }
        }
    }
}