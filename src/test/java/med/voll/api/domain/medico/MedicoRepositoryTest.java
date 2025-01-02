package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DireccionDTO;
import med.voll.api.domain.pacientes.DatosRegistroPaciente;
import med.voll.api.domain.pacientes.Paciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaEsceario1() {
        var lunesSiguenteALas10= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("Medico1","medico@gmail.com","123456",Especialidad.CARDIOLOGIA);
        var paciente= registroPaciente("Paciente1","paciente@gmail.com","456123");
        registarConsulta(medico,paciente,lunesSiguenteALas10);

    var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguenteALas10);

    assertThat(medicoLibre).isNull();
    }
    @Test
    @DisplayName("Deberia devolver medico cuando el medico buscado  esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaEsceario2() {
        var lunesSiguenteALas10= LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("Medico1","medico@gmail.com","123456",Especialidad.CARDIOLOGIA);

        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, lunesSiguenteALas10);

        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void  registarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
    em.persist(new Consulta(null,medico,paciente,fecha));
    }

    private Medico  registrarMedico(String nombre,String email,String documento,Especialidad especialidad){
    var medico = new Medico(datosMedico(nombre,email,documento,especialidad));
    em.persist(medico);
    return medico;
    }
    private Paciente registroPaciente(String nombre,String email,String documento){
        var paciente= new Paciente(datosPaciente(nombre,email,documento));
        em.persist(paciente);
        return paciente;
    }


    private RegistroMedicoDTO datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new RegistroMedicoDTO(
          nombre,
          email,
          "3005003233",
          documento,
          especialidad,
                datosDireccion()
        );
    }


    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1234564",
                documento,
                datosDireccion()
        );
    }
    private DireccionDTO datosDireccion() {
            return new DireccionDTO(
                "calle x",
                "Distrito y",
                "ciudad <",
                "123",
                "1"
            );
    }

}