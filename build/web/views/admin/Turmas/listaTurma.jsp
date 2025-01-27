<%@page import="entidade.Turma, entidade.Administrador, entidade.Professor, java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="#">
    <title>Lista de Turmas</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    HttpSession sessao = request.getSession(false);
    if (sessao != null) {
        Administrador administradorLogado = (Administrador) sessao.getAttribute("authUserAdmin");
        Professor professorLogado = (Professor) sessao.getAttribute("authUserProfessor");
        if (administradorLogado != null) {
%>
<div class="container">
    <jsp:include page="../../comum/menu.jsp" />
    <div class="mt-5">
        <h1>Área Restrita</h1>
        <h2>Lista de Turmas</h2>

        <a href="/aplicacaoMVC/admin/TurmaC?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Id professor</th>
                        <th scope="col">Id disciplina</th>
                        <th scope="col">Id aluno</th>
                        <th scope="col">Codigo turma</th>
                        <th scope="col">Nota</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Safely get the list of turmas
                        ArrayList<Turma> listaTurma = (ArrayList<Turma>) request.getAttribute("listaTurma");
                        if (listaTurma != null && !listaTurma.isEmpty()) {
                            for (Turma turma : listaTurma) {
                    %>
                    <tr>
                        <th><%= turma.getId() %></th>
                        <th><%= turma.getProfessor_id() %></th>
                        <td><%= turma.getDisciplina_id() %></td>
                        <td><%= turma.getAluno_id() %></td>
                        <td><%= turma.getCodigoTurma() %></td>
                        <td><%= turma.getNota() %></td>
                        <td>
                            <a href="/aplicacaoMVC/admin/TurmaC?acao=Alterar&id=<%= turma.getId() %>" class="btn btn-warning">Alterar</a>
                            <a href="/aplicacaoMVC/admin/TurmaC?acao=Excluir&id=<%= turma.getId() %>" class="btn btn-danger">Excluir</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">Nenhuma turma encontrada.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%
        } else {
%>
<div class="container">
    <h1>Acesso Negado</h1>
    <p>Você não tem permissão para acessar esta página.</p>
</div>
<%
        }
    } else {
%>
<div class="container">
    <h1>Usuário não autenticado</h1>
    <p>Por favor, faça login para acessar esta página.</p>
</div>
<%
    }
%>
<script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
