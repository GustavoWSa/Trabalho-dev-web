
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="entidade.Turma" %>
<%@ page import="entidade.Disciplina" %>
<%@ page import="entidade.Aluno" %>
<%@ page import="entidade.Professor" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Turma</title>
</head>
<body>
    <h1>${acao == 'Incluir' ? 'Cadastrar' : 'Alterar'} Turma</h1>

    <form action="TurmaController" method="post">
        <input type="hidden" name="acao" value="${acao}">
        <input type="hidden" name="id" value="${turma.id}">

        <div>
            <label for="professor_id">Professor:</label>
            <select name="professor_id" id="professor_id" required>
                <c:forEach var="professor" items="${listaProfessores}">
                    <option value="${professor.id}" <c:if test="${professor.id == turma.professorId}">selected</c:if>>${professor.nome}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="disciplina_id">Disciplina:</label>
            <select name="disciplina_id" id="disciplina_id" required>
                <c:forEach var="disciplina" items="${listaDisciplinas}">
                    <option value="${disciplina.id}" <c:if test="${disciplina.id == turma.disciplinaId}">selected</c:if>>${disciplina.nome}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="aluno_id">Aluno:</label>
            <select name="aluno_id" id="aluno_id" required>
                <c:forEach var="aluno" items="${listaAlunos}">
                    <option value="${aluno.id}" <c:if test="${aluno.id == turma.alunoId}">selected</c:if>>${aluno.nome}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="codigo_turma">Código da Turma:</label>
            <input type="text" name="codigo_turma" id="codigo_turma" value="${turma.codigoTurma}" required>
        </div>

        <div>
            <label for="nota">Nota:</label>
            <input type="number" step="0.01" name="nota" id="nota" value="${turma.nota}" required>
        </div>

        <div>
            <button type="submit">${acao == 'Incluir' ? 'Cadastrar' : 'Alterar'} Turma</button>
        </div>
    </form>

    <a href="/aplicacaoMVC/admin/TurmaController?acao=Listar">Voltar para a lista de turmas</a>
</body>
</html>
