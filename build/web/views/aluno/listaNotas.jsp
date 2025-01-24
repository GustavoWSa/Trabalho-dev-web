<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.ArrayList, entidade.Turma" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Turmas Matriculadas</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Notas</h1>
        <hr>

        <%
            // Retrieve the list of turmas from the request
            ArrayList<entidade.Turma> listaTurma = (ArrayList<entidade.Turma>) request.getAttribute("listaTurma");
        %>

        <div class="table-responsive">
            <%
                if (listaTurma != null && !listaTurma.isEmpty()) {
            %>
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            
                            <th scope="col">Id</th>
                            <th scope="col">Código da Turma</th>
                            <th scope="col">Disciplina</th>
                            <th scope="col">Id aluno</<th>
                            <th scope="col">Nota</th>
                            
                            
                            
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (entidade.Turma turma : listaTurma) {
                        %>
                        <tr>
                            <td><%= turma.getId() %></td>
                            <td><%= turma.getCodigoTurma() %></td>
                            <td><%= turma.getDisciplina_id() %></td>
                            <<td><%= turma.getAluno_id() %></td>
                            <td><%= turma.getNota() %></td>
                            
                        </tr>

                       
                        <% 
                            }
                        %>
                    </tbody>
                </table>
            <%
                } else {
            %>
                <div class="alert alert-info text-center">
                    Nenhuma turma encontrada.
                </div>
            <%
                }
            %>
        </div>
    </div>

    <!-- Include Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
