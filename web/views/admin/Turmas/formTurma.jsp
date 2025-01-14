<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Turma"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Turma</title>
    <link href="../../../../bootstrap/bootstrap.min.css" rel="stylesheet">
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
        <form action="TurmaController" method="POST">
            <div class="mb-3">
                <label for="professor_id" class="form-label">Id professor</label>
                <input type="number" name="professor_id" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="disciplina_id" class="form-label">Id disciplina</label>
                <input type="number" name="disciplina_id" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="aluno_id" class="form-label">aluno_id</label>
                <input type="number" name="aluno_id" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="codigoTurma" class="form-label">codigoTurma</label>
                <input type="text" name="codigoTurma" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="nota" class="form-label">nota</label>
                <input type="double" name="nota" class="form-control" required>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    <input type="submit" value="Registrar" class="btn btn-primary">
                </div>
            </div>
        </form>
    </div>
</body>
</html>
