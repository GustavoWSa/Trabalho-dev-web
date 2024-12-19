<table>
    <thead>
        <tr>
            <th>Nome</th>
            <th>Requisito</th>
            <th>Emetenta</th>
            <th>Carga Hor�ria</th>
            <th>A��es</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="disciplina" items="${disciplinas}">
            <tr>
                <td>${disciplina.nome}</td>
                <td>${disciplina.requisito}</td>
                <td>${disciplina.emetenta}</td>
                <td>${disciplina.cargaHoraria}</td>
                <td>
                    <a href="disciplina?action=edit&id=${disciplina.id}">Editar</a>
                    <a href="disciplina?action=delete&id=${disciplina.id}">Excluir</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
