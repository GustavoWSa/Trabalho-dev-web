<%@page import="entidade.Turma"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista de turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Lista de Turmas</h2>

                <a href="/aplicacaoMVC/admin/TurmaController?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Id professor</th>
                                <th scope="col">Id disciplina</th>
                                <th scope="col">Id professor</th>
                                <th scope="col">Codigo turma</th>
                                <th scope="col">Nota</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                // Obtém a lista de turmas passada pelo controlador
                                ArrayList<Turma> listaTurma = (ArrayList<Turma>) request.getAttribute("listaTurma");

                                for (Turma turma : listaTurma) {
                                    out.println("<tr>");
                                    out.println("<th>" + turma.getId() + "</th>");
                                    out.println("<td>" + turma.getDisciplina_id() + "</td>");
                                    out.println("<td>" + turma.getAluno_id() + "</td>");
                                    out.println("<td>" + turma.getCodigoTurma() + "</td>");
                                    out.println("<td>" + turma.getNota() + "</td>");   
                            %>
                            <td>
                                <a href="/aplicacaoMVC/admin/TurmaController?acao=Alterar&id=<%=turma.getId()%>" class="btn btn-warning">Alterar</a>
                                <a href="/aplicacaoMVC/admin/TurmaController?acao=Excluir&id=<%=turma.getId()%>" class="btn btn-danger">Excluir</a>
                            </td>
                            <%
                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
