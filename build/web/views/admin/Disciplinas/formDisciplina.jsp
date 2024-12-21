<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Disciplina"%>

<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Disciplina</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css"  rel="stylesheet">
    </head>

    <body>

        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-4 offset-3">
                    <%
                        Disciplina disciplina = (Disciplina) request.getAttribute("disciplina");
                        String acao = (String) request.getAttribute("acao");
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Disciplina</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Disciplina</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Disciplina</h1>");
                                break;
                        }

                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError%>
                    </div>
                    <% }%>
                    <form action="DisciplinaController" method="POST">
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" name="nome" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label for="requisito" class="form-label">Requisito</label>
                            <input type="text" name="requisito" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label for="ementa" class="form-label">Ementa</label>
                            <input type="text" name="ementa" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label for="carga_horaria" class="form-label">Carga Horária</label>
                            <input type="number" name="carga_horaria" class="form-control" required>
                        </div>
                        <div class="row">
                            <div class="col-sm-2">
                                <!-- Agora, cada botão envia um valor para btEnviar -->
                                <input type="submit" name="btEnviar" value="Incluir" class="btn btn-primary">
                            </div>
                            <div class="col-sm-2">
                                <input type="submit" name="btEnviar" value="Alterar" class="btn btn-warning">
                            </div>
                            <div class="col-sm-2">
                                <input type="submit" name="btEnviar" value="Excluir" class="btn btn-danger">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>

</html>
