<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Administrador, entidade.Professor" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/aplicacaoMVC/home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <%
                    // Testar se está logado
                    HttpSession sessao = request.getSession(false);
                    if (sessao != null) {
                        Administrador administradorLogado = (Administrador) session.getAttribute("administrador");
                        Professor professorLogado = (Professor) session.getAttribute("professor");
                        
                        if (administradorLogado != null) { %>
                            <!-- Opções do menu para Administrador -->
                            <a class="nav-link" href="/aplicacaoMVC/admin/dashboard">Dashboard</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/CategoriaController?acao=Listar">Categorias</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar">Disciplinas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/TurmaController?acao=Listar">Turmas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/RegistrarAluno">Registrar aluno</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/ProfessorController">Professores</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/logOut">Logout</a>
                <%  } else if (professorLogado != null) { %>
                            <!-- Opções do menu para Professor -->
                            
                <%  } else { %>
                            <!-- Opções para usuário não autenticado -->
                            <a class="nav-link" href="/aplicacaoMVC/MostrarComentarios">Comentários</a>
                            <a class="nav-link" href="/aplicacaoMVC/AutenticaController?acao=Login">Login admin</a>
                            
                <%    }
                    } %>
            </div>
        </div>
    </div>
</nav>
