<%@ include file="/views/comun/header.jsp" %>
<div class="container mt-4">
    <h1 class="text-center">${disciplina != null ? "Editar Disciplina" : "Cadastrar Disciplina"}</h1>
    <form action="/disciplina" method="post" class="mt-4">
        <!-- ID oculto para edição -->
        <input type="hidden" name="id" value="${disciplina != null ? disciplina.id : ''}">
        
        <!-- Campo Nome -->
        <div class="mb-3">
            <label for="nome" class="form-label">Nome</label>
            <input type="text" class="form-control" id="nome" name="nome" 
                   value="${disciplina != null ? disciplina.nome : ''}" required>
        </div>

        <!-- Campo Requisito -->
        <div class="mb-3">
            <label for="requisito" class="form-label">Requisito</label>
            <input type="text" class="form-control" id="requisito" name="requisito" 
                   value="${disciplina != null ? disciplina.requisito : ''}">
        </div>

        <!-- Campo Ementa -->
        <div class="mb-3">
            <label for="ementa" class="form-label">Ementa</label>
            <input type="text" class="form-control" id="ementa" name="ementa" 
                   value="${disciplina != null ? disciplina.ementa : ''}">
        </div>

        <!-- Campo Carga Horária -->
        <div class="mb-3">
            <label for="cargaHoraria" class="form-label">Carga Horária</label>
            <input type="number" class="form-control" id="cargaHoraria" name="cargaHoraria" 
                   value="${disciplina != null ? disciplina.cargaHoraria : ''}" required>
        </div>

        <!-- Botão de Submissão -->
        <div class="text-end">
            <button type="submit" class="btn btn-success">
                ${disciplina != null ? "Salvar Alterações" : "Cadastrar"}
            </button>
        </div>
    </form>
</div>
<%@ include file="/views/comun/footer.jsp" %>
