<%@page import="entidade.Turma"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Aluno, entidade.Professor" %>

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
                <jsp:include page="../comum/menu.jsp" />
                <div class="mt-5">
                    
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Id</th>
                                    <th scope="col">Id professor</th>
                                    <th scope="col">Id disciplina</th>
                                    
                                    <th scope="col">Codigo turma</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                // Obtém a lista de turmas passada pelo controlador
                                    ArrayList<Turma> listaTurma = (ArrayList<Turma>) request.getAttribute("listaTurma");

                                    for (Turma turma : listaTurma) {
                                        out.println("<tr>");
                                        out.println("<th>" + turma.getId() + "</th>");
                                        out.println("<th>" + turma.getProfessor_id() + "</th>");
                                        out.println("<td>" + turma.getDisciplina_id() + "</td>");
                                        
                                        out.println("<td>" + turma.getCodigoTurma() + "</td>");
                                           
                                %>
                                <td>
                                    <form action="/aplicacaoMVC/aluno/TurmaAluno" method="POST" style="display: inline;">
                                        <input type="hidden" name="acao" value="Inscrever">
                                        <input type="hidden" name="id" value="<%= turma.getId() %>">
                                        <input type="hidden" name="professor_id" value="<%= turma.getProfessor_id() %>">
                                        <input type="hidden" name="disciplina_id" value="<%= turma.getDisciplina_id() %>">
                                        <input type="hidden" name="codigo_turma" value="<%= turma.getCodigoTurma() %>">
                                        <button type="submit" class="btn btn-primary">Inscrever-se</button>
                                    </form>
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
        
                    </table>
                </div>
            </div>
        </div>

        
            }
    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>   
    </body>
    
    
</html>
