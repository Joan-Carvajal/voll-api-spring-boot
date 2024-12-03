package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.ActualizarMedicosDTO;
import med.voll.api.dto.ListadoMedicoDTO;
import med.voll.api.dto.RegistroMedicoDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;


    @PostMapping
    public void registrarMedico(@RequestBody @Valid RegistroMedicoDTO registroMedicoDTO){
        medicoRepository.save(new Medico(registroMedicoDTO));
        System.out.println(registroMedicoDTO);
    }

    @GetMapping("/listar")
    public Page<ListadoMedicoDTO> listadoMedico(@PageableDefault(size = 2) Pageable paginacion){
        return medicoRepository.findByActivoTrue(paginacion).map(ListadoMedicoDTO::new);
//        return medicoRepository.findAll(paginacion).map(ListadoMedicoDTO::new);
    }
    @PutMapping
    @Transactional
    public void  actualizarMedico(@RequestBody @Valid ActualizarMedicosDTO actualizarMedicosDto){
        Medico medico =medicoRepository.getReferenceById(actualizarMedicosDto.id());
        medico.actualizarDatos(actualizarMedicosDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  void  eliminarMedico(@PathVariable Long id){
        Medico medico =medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
    }
//    public  void  eliminarMedico(@PathVariable Long id){
//        Medico medico =medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}
