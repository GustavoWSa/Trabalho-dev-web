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

        String acao = (String) request.getParameter("acao");
        Aluno aluno = new Aluno();
        AlunoDAO alunoDAO = new AlunoDAO();
        RequestDispatcher rd;
        if (acao == null) {
        // Defina um valor padrão ou redirecione para um erro
        acao = "Listar"; // ou algum valor adequado
        }
        switch (acao) {
            case "Listar":
                ArrayList<Aluno> listaAlunos = alunoDAO.getAll();
                request.setAttribute("listaAlunos", listaAlunos);

                rd = request.getRequestDispatcher("/views/admin/Alunos/listaAlunos.jsp");
                rd.forward(request, response);
                break;

            case "Alterar":
            case "Excluir":
                // get parametro ação indicando sobre qual categoria será a ação
                int id = Integer.parseInt(request.getParameter("id"));
                try {
                        aluno = alunoDAO.get(id); // Usa a instância do AlunoDAO para chamar o método get
                    } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha ao buscar o aluno pelo ID");
    }

                request.setAttribute("aluno", aluno);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/categoria/formCategoria.jsp");
                rd.forward(request, response);
                break;


            case "Incluir":
                request.setAttribute("aluno", aluno);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
                rd.forward(request, response);
                
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        //request.getParameter("id").isEmpty() ? 0 : Integer.parseInt(request.getParameter("id"));
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

    if (nome.isEmpty()) {
            Aluno aluno = new Aluno();
            switch (btEnviar) {
                case "Alterar":
                case "Excluir":
                    try {
                    AlunoDAO alunoDAO = new AlunoDAO();
                    aluno = alunoDAO.get(id);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException("Falha em uma query para cadastro de Aluno");
                }
                break;
            }

            request.setAttribute("nome", aluno);
            request.setAttribute("acao", btEnviar);

            request.setAttribute("msgError", "É necessário preencher todos os campos");

            rd = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
            rd.forward(request, response);

        } else {
            
             Aluno aluno = new Aluno(id,nome,cidade,cpf,email,celular,senha,endereco,bairro,cep);
             AlunoDAO alunoDAO = new AlunoDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        alunoDAO.insert(aluno);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;
                    case "Alterar":
                        alunoDAO.update(aluno);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;
                    case "Excluir":
                        alunoDAO.delete(id);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/RegistrarAluno?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (IOException | ServletException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha em uma query para cadastro de Aluno");
            }
        }
    }


}