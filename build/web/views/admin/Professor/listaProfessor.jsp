<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Professores</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Lista de Professores</h2>
        <a href="ProfessorController" class="btn btn-primary mb-3">Cadastrar Novo Professor</a>
        
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CPF</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Professor> professores = (ArrayList<Professor>) request.getAttribute("professores");
                    if (professores != null) {
                        for (Professor professor : professores) {
                %>
                            <tr>
                                <td><%= professor.getNome() %></td>
                                <td><%= professor.getEmail() %></td>
                                <td><%= professor.getCpf() %></td>
                                <td>
                                    <a href="ProfessorController?action=editar&id=<%= professor.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                                    <a href="ProfessorController?action=excluir&id=<%= professor.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                                </td>
                            </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
