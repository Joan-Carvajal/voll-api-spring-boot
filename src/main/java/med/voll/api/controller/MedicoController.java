package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DireccionDTO;
import med.voll.api.domain.medico.ActualizarMedicosDTO;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.ListadoMedicoDTO;
import med.voll.api.domain.medico.RegistroMedicoDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;


    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid RegistroMedicoDTO registroMedicoDTO , UriComponentsBuilder uriComponentsBuilder){
         Medico medico=medicoRepository.save(new Medico(registroMedicoDTO));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DireccionDTO(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoMedicoDTO>> listadoMedico(@PageableDefault(size = 3) Pageable paginacion){

        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(ListadoMedicoDTO::new));

//        return medicoRepository.findAll(paginacion).map(ListadoMedicoDTO::new);
    }
    @PutMapping
    @Transactional
    public ResponseEntity  actualizarMedico(@RequestBody @Valid ActualizarMedicosDTO actualizarMedicosDto){
        Medico medico =medicoRepository.getReferenceById(actualizarMedicosDto.id());
        medico.actualizarDatos(actualizarMedicosDto);

        // usar patron DTO que es lo recomendable
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                 new DireccionDTO(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                         medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico =medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico =medicoRepository.getReferenceById(id);
    var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
            medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString() ,
            new DireccionDTO(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                    medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        return  ResponseEntity.ok(datosMedico);
    }
//    public  void  eliminarMedico(@PathVariable Long id){
//        Medico medico =medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}
