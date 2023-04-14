package co.edu.uceva.programa_service.controller;


import co.edu.uceva.programa_service.model.entities.Programa;
import co.edu.uceva.programa_service.model.service.IProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class ProgramaRestController {

    @Autowired    // Crea un contenedor sin necesidad de hacer un construcctor
    IProgramaService programaService;

    /**
     * Endpoint para recibir un saludo
     * @param nombre Es el nombre que envian desde la url
     * @return Un saludo
     */

    @GetMapping("/hola/{nombre}")
    public String holaMundo(@PathVariable("nombre") String nombre){
        return "Hola "+ nombre;
    }

    @GetMapping("/programas")
    public List<Programa> Listar(){
        programaService.findAll();
        return  programaService.findAll();
    }

    @GetMapping("/programas/{id}")
    public Programa buscarPrograma(@PathVariable("id") long id  ){
        return  programaService.findById(id);

    }

    @PostMapping("/programas")
    public Programa crearPrograma(@RequestBody Programa programa){
        return programaService.save(programa);

    }

    @DeleteMapping("/programas/{id}")
    public void borrarPrograma(@PathVariable("id") long id ) {
        Programa programa;
        programa = programaService.findById(id); // Busca Programa
        programaService.delete(programa);

    }

    @PutMapping("/programas")
    public Programa actualizarPrograma(@RequestBody Programa programa) {
        return programaService.update(programa);
    }

}
