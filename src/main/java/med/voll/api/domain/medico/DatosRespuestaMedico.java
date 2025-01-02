package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DireccionDTO;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String especialida,
        DireccionDTO direccion

) {
}
