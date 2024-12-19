<%@page import="entidade.Aluno"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista de Alunos</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Lista de Alunos</h2>

                <a href="/aplicacaoMVC/admin/RegistrarAluno?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Email</th>
                                <th scope="col">Celular</th>
                                <th scope="col">Cidade</th>
                                <th scope="col">Bairro</th>
                                <th scope="col">CEP</th>
                                <th scope="col">CPF</th>
                                <th scope="col">Endereço</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                // Obtém a lista de alunos passada pelo controlador
                                ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");

                                for (Aluno aluno : listaAlunos) {
                                    out.println("<tr>");
                                    out.println("<th>" + aluno.getId() + "</th>");
                                    out.println("<td>" + aluno.getNome() + "</td>");
                                    out.println("<td>" + aluno.getEmail() + "</td>");
                                    out.println("<td>" + aluno.getCelular() + "</td>");
                                    out.println("<td>" + aluno.getCidade() + "</td>");
                                    out.println("<td>" + aluno.getBairro() + "</td>");
                                    out.println("<td>" + aluno.getCep() + "</td>");
                                    out.println("<td>" + aluno.getCpf() + "</td>");
                                    out.println("<td>" + aluno.getEndereco() + "</td>");
                            %>
                            <td>
                                <a href="/aplicacaoMVC/admin/RegistrarAluno?acao=Alterar&id=<%=aluno.getId()%>" class="btn btn-warning">Alterar</a>
                                <a href="/aplicacaoMVC/admin/RegistrarAluno?acao=Excluir&id=<%=aluno.getId()%>" class="btn btn-danger">Excluir</a>
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
