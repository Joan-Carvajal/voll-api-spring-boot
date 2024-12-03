package med.voll.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.ActualizarMedicosDTO;
import med.voll.api.dto.Especialidad;
import med.voll.api.dto.RegistroMedicoDTO;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(RegistroMedicoDTO registroMedicoDTO) {
        this.nombre = registroMedicoDTO.nombre();
        this.email = registroMedicoDTO.email();
        this.telefono = registroMedicoDTO.telefono();
        this.documento =registroMedicoDTO.documento();
        this.activo= true;
        this.especialidad= registroMedicoDTO.especialidad();
        this.direccion = new Direccion(registroMedicoDTO.direccion());
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void actualizarDatos(ActualizarMedicosDTO actualizarMedicosDto) {
        if (actualizarMedicosDto.nombre()!= null){
        this.nombre = actualizarMedicosDto.nombre();
        }
        if (actualizarMedicosDto.documento()!=null){
            this.documento =actualizarMedicosDto.documento();
        }
        if (actualizarMedicosDto.direccion() != null){
            this.direccion = direccion.actualizarDatos(actualizarMedicosDto.direccion());
        }


    }

    public void desactivarMedico() {
        this.activo= false;
    }
}
