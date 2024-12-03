package med.voll.api.dto;

import jakarta.validation.constraints.NotNull;

public record ActualizarMedicosDTO(
       @NotNull Long id,
        String nombre,
        String documento,
        DireccionDTO direccion

        ) {
}
