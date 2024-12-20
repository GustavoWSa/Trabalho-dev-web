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
                int idAlterar = Integer.parseInt(request.getParameter("id"));
            try {
            Aluno alunoParaAlterar = alunoDAO.get(idAlterar);
            request.setAttribute("aluno", alunoParaAlterar);
            request.setAttribute("acao", "Alterar");
            request.setAttribute("msgError", "");
            } catch (Exception e) {
            System.out.println("Erro ao buscar aluno para alteração: " + e.getMessage());
            request.setAttribute("msgError", "Falha ao carregar dados do aluno.");
            }

            RequestDispatcher rdAlterar = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
            rdAlterar.forward(request, response);
            break;
            case "Excluir":
                // get parametro ação indicando sobre qual categoria será a ação
                // Captura o ID do aluno a ser excluído
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                alunoDAO.delete(id); // Exclui o aluno do banco
                request.setAttribute("msgOperacaoRealizada", "Aluno excluído com sucesso!");
            } catch (Exception e) {
                request.setAttribute("msgError", "Erro ao excluir o aluno: " + e.getMessage());
            }
            // Redireciona para a listagem após exclusão
            response.sendRedirect("/aplicacaoMVC/admin/RegistrarAluno?acao=Listar");
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

    // Captura o ID, caso exista (somente para Alterar ou Excluir)
    String idParam = request.getParameter("id");
    int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

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

    // Verifique se o nome está vazio
    if (nome == null || nome.isEmpty()) {
        Aluno aluno = new Aluno();
        request.setAttribute("aluno", aluno);
        request.setAttribute("acao", btEnviar);
        request.setAttribute("msgError", "É necessário preencher todos os campos");
        rd = request.getRequestDispatcher("/views/admin/Alunos/formRegistrarAluno.jsp");
        rd.forward(request, response);
    } else {
        Aluno aluno;
        if (id > 0) {
            // Criação do aluno com ID (para Alterar ou Excluir)
            aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
        } else {
            // Criação do aluno sem ID (para Incluir)
            aluno = new Aluno(nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
        }

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
                    alunoDAO.delete(aluno.getId());
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/RegistrarAluno?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            throw new RuntimeException("Falha em uma query para cadastro de Aluno");
        }
    }
}}