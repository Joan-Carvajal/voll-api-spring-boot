package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {
    boolean existsByPacienteIdAndFechaBetween( Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimaHorario);

//    boolean existsByMedicoIdAndFecha(Long idMedico, @NotNull @Future LocalDateTime fecha);
    boolean existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(Long idMedico, LocalDateTime fecha);
}
