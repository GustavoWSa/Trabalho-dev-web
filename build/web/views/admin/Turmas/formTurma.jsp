<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Turma</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
         <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-4 offset-3">
                    <%
                        Turma turma = (Turma) request.getAttribute("turma");
                        String acao = (String) request.getAttribute("acao");
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Turma</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Turma</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Turma</h1>");
                                break;
                        }

                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError%>
                    </div>
                    <% }%>
        <form action="TurmaController?action=salvar" method="POST">
            <div class="mb-3">
                <label for="professor_id" class="form-label">Codigo professor</label>
                <input type="text" name="professor_id" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="disciplina_id" class="form-label">Codigo disciplina</label>
                <input type="text" name="disciplina_id" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="aluno_id" class="form-label">Codigo aluno</label>
                <input type="text" name="aluno_id" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="codigo_turma" class="form-label">Codigo turma</label>
                <input type="text" name="codigo_turma" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="nota" class="form-label">Nota</label>
                <input type="text" name="nota" class="form-control" required>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    <input type="submit" name="btEnviar" value="<%=acao%>" class="btn btn-primary">
                    <a href="/aplicacaoMVC/admin/TurmaController?acao=Listar" class="btn btn-danger">Retornar</a>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
