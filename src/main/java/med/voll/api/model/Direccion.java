package med.voll.api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DireccionDTO;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String distrito;
    private String ciudad;
    private String numero;
    private String complemento;

    public Direccion(DireccionDTO direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito =direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
    }


    public Direccion actualizarDatos(DireccionDTO direccion) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito =direccion.distrito();
        this.complemento = direccion.complemento();
        this.ciudad = direccion.ciudad();
        return this;
    }
}
