<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="entidade.Turma" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Turmas</title>
</head>
<body>
    <h1>Lista de Turmas</h1>
    <a href="/aplicacaoMVC/admin/TurmaController?acao=Incluir">Cadastrar nova turma</a>

    <table border="1">
        <thead>
            <tr>
                <th>Código da Turma</th>
                <th>Disciplina</th>
                <th>Professor</th>
                <th>Aluno</th>
                <th>Nota</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="turma" items="${listaTurmas}">
                <tr>
                    <td>${turma.codigoTurma}</td>
                    <td>${turma.disciplina.nome}</td>
                    <td>${turma.professor.nome}</td>
                    <td>${turma.aluno.nome}</td>
                    <td>${turma.nota}</td>
                    <td>
                        <a href="/aplicacaoMVC/admin/TurmaController?acao=Alterar&id=${turma.id}">Alterar</a> |
                        <a href="/aplicacaoMVC/admin/TurmaController?acao=Excluir&id=${turma.id}" onclick="return confirm('Tem certeza que deseja excluir?')">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a href="/aplicacaoMVC/admin/dashboard">Voltar ao Dashboard</a>
</body>
</html>
