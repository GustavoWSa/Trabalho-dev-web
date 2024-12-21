package controller.admin;
import java.io.IOException;
import model.DisciplinaDAO;
import model.Conexao;
import entidade.Disciplina;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        String acao = (String) request.getParameter("acao");
        Disciplina disciplina = new Disciplina();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        RequestDispatcher rd;
        
        if (acao == null) {
            acao = "Listar"; 
        }
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
                try{
                         disciplina = disciplinaDAO.get(id);
                } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha ao buscar a discip´lina pelo ID");
    }
                 request.setAttribute("disciplina", disciplina);
                request.setAttribute("msgError", "");
                request.setAttribute("acao", acao);

                rd = request.getRequestDispatcher("/views/admin/categoria/formCategoria.jsp");
                rd.forward(request, response);
                break;
                
            case "Incluir":
                request.setAttribute("disciplina", disciplina);
                request.setAttribute("acao", acao);
                request.setAttribute("msgError", "");

                rd = request.getRequestDispatcher("/views/admin/Disciplinas/formDisciplina.jsp");
                rd.forward(request, response);
                
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String requisito = request.getParameter("requisito");
        String ementa = request.getParameter("ementa");
        int cargaHoraria = Integer.parseInt(request.getParameter("carga_horaria"));
        String btEnviar = request.getParameter("btEnviar");
        RequestDispatcher rd;

        // Validação de nome (obrigatório)
        if (nome == null || nome.isEmpty()) {
            Disciplina disciplina = new Disciplina();
            request.setAttribute("disciplina", disciplina);
            request.setAttribute("msgError", "É necessário preencher todos os campos");
            rd = request.getRequestDispatcher("/views/admin/Disciplinas/formDisciplina.jsp");
            rd.forward(request, response);
        } else {
        // Criação do disciplina sem id
        Disciplina Disciplina = new Disciplina(nome, requisito, ementa,cargaHoraria);
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
       
        try {
            switch (btEnviar) {
                case "Incluir":
                    disciplinaDAO.insert(Disciplina); // O id será gerado automaticamente pelo banco
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                    break;
                case "Alterar":
                    disciplinaDAO.update(Disciplina); // O aluno já tem o id (o id foi capturado na seleção)
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                    break;
                case "Excluir":
                    disciplinaDAO.delete(Disciplina.getId()); // O id será passado para exclusão
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/DisciplinaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | ServletException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Falha em uma query para cadastro de Aluno");
        }
    }
 }       
}
