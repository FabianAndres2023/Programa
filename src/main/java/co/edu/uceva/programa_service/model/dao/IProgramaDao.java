package co.edu.uceva.programa_service.model.dao;

import co.edu.uceva.programa_service.model.entities.Programa;
import org.springframework.data.repository.CrudRepository;

public interface IProgramaDao extends CrudRepository<Programa, Long> {

}
