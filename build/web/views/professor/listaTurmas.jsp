<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.ArrayList, entidade.Turma" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Turmas Mentoradas</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Turmas Mentoradas</h1>
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
                            <th>ID</th>
                            <th>Código da Turma</th>
                            <th>Disciplina</th>
                            <th>Nota</th>
                            <th>Ações</th>
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
                            <td><%= turma.getNota() %></td>
                            <td>
                                <!-- Button to trigger modal to update nota -->
                                <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#updateNotaModal<%= turma.getId() %>">
                                    Atualizar Nota
                                </button>
                            </td>
                        </tr>

                        <!-- Modal for updating nota -->
                        <div class="modal fade" id="updateNotaModal<%= turma.getId() %>" tabindex="-1" aria-labelledby="updateNotaModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="updateNotaModalLabel">Atualizar Nota</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="/professor/TurmaController" method="POST">
                                            <div class="mb-3">
                                                <label for="nota" class="form-label">Nova Nota</label>
                                                <input type="number" class="form-control" id="nota" name="nota" value="<%= turma.getNota() %>" required>
                                            </div>
                                            <input type="hidden" name="id" value="<%= turma.getId() %>">
                                            <input type="hidden" name="acao" value="Alterar"> <!-- Existing action to handle the update -->
                                            <button type="submit" class="btn btn-primary">Salvar</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
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
