package med.voll.api.dto;

import med.voll.api.model.Medico;

public record ListadoMedicoDTO(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email
) {
    public ListadoMedicoDTO(Medico medico){
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString() ,medico.getDocumento(), medico.getEmail());
    }
}
