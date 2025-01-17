package controller;

import entidade.Administrador;
import entidade.Professor;
import entidade.Aluno;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradorDAO;
import model.ProfessorDAO;
import model.AlunoDAO;

/**
 *
 * @author Leonardo
 */
@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
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
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);

        } else {
            
            
            try {
                AdministradorDAO AdministradorDAO = new AdministradorDAO();
                Administrador administradorObtido;
                administradorObtido = AdministradorDAO.Logar(cpf_user, senha_user);
                ProfessorDAO ProfessorDAO = new ProfessorDAO();
                Professor professorObtido;
                professorObtido = ProfessorDAO.Logar(cpf_user, senha_user);
                AlunoDAO AlunoDAO = new AlunoDAO();
                Aluno alunoObtido;
                
                System.out.println(administradorObtido);
                System.out.println(administradorObtido.getAprovado());
                System.out.println(administradorObtido.getNome());
                System.out.println(administradorObtido.getRole());
                alunoObtido = AlunoDAO.Logar(cpf_user, senha_user);
                if (administradorObtido.getNome() != null && !administradorObtido.getNome().trim().isEmpty()){
                    if (administradorObtido.getAprovado().contentEquals("s")){
                        request.getSession().setAttribute("authUserAdmin", administradorObtido);
                        rd = request.getRequestDispatcher("/views/public/home.jsp");
                        rd.forward(request, response);
                        //sessionStorage.setItem("Role", "admin");
                        
                    }
                    else {
                        rd = request.getRequestDispatcher("/views/erro/NaoAutorizado.jsp");
                        rd.forward(request, response);
                        
                    }
                        
                    
                }
                else if(professorObtido != null){
                    
                    request.getSession().setAttribute("authUserProfessor", professorObtido);
                    rd = request.getRequestDispatcher("/views/professor/lancarNota.jsp");
                    rd.forward(request, response);
                }
                else if(alunoObtido != null){
                    
                    request.getSession().setAttribute("authUserAluno", alunoObtido);
                    rd = request.getRequestDispatcher("/views/professor/lancarNota.jsp");
                    rd.forward(request, response);
                }
                
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }

            
             
        }
    }

}
