package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DireccionDTO;

public record ActualizarMedicosDTO(
       @NotNull Long id,
        String nombre,
        String documento,
        DireccionDTO direccion

        ) {
}
