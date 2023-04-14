package co.edu.uceva.programa_service.model.service;

import co.edu.uceva.programa_service.model.dao.IProgramaDao;
import co.edu.uceva.programa_service.model.entities.Programa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramaServiceImpl implements IProgramaService {
    @Autowired
    IProgramaDao programaDao;

    @Override
    public Programa save(Programa programa) {
        return programaDao.save(programa);
    }
    @Override
    public void delete(Programa programa) {
        programaDao.delete(programa);

    }

    @Override
    public Programa update(Programa programa) {
        return programaDao.save(programa);

    }


    /**
     * Este metodo lista los paises guardados
     * @return una lista de paises
     */

    @Override
    public List<Programa> findAll() {
        return(List<Programa>) programaDao.findAll();
    }

    @Override
    public Programa findById(Long id) {
        return (Programa) programaDao.findById(id).get();

    }

}
