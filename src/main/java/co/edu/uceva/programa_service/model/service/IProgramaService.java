package co.edu.uceva.programa_service.model.service;

import co.edu.uceva.programa_service.model.entities.Programa;
import java.util.List;

public interface IProgramaService {
    Programa save(Programa programa);
    void delete(Programa programa);
    Programa update(Programa programa);
    List<Programa> findAll();
    Programa findById(Long id);

}
