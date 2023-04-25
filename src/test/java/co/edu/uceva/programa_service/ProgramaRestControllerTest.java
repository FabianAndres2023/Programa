package co.edu.uceva.programa_service;

import co.edu.uceva.programa_service.model.entities.Programa;
import co.edu.uceva.programa_service.model.service.IProgramaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Pruebas unitarias (unit tests) para la  API RESTful que se encarga de realizar operaciones CRUD sobre una entidad
 * llamada "Programa".
 * Se importan las clases necesarias para realizar las pruebas (MockMvc, ObjectMapper, etc.), se inyecta el servicio
 * que se encarga de realizar las operaciones sobre la entidad "Programa" (IProgramaService), y se definen varios métodos de
 * prueba para la API RESTful, que comprueban el correcto funcionamiento de los métodos:
 * GET, POST, PUT y DELETE de la API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramaRestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private IProgramaService programaService;

    /**
     * Inicializa los objetos necesarios para la prueba. En el ejemplo de código dado, este método se utiliza para \
     * inicializar el objeto MockMvc, que se utiliza para simular el envío de solicitudes HTTP en la prueba de la  \
     * clase ProgramaRestController.
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Prueba del método GET "/programa-service/hola/{nombre}", que comprueba que se recibe el nombre correcto
     * en la respuesta.
     * @throws Exception
     */
    @Test
    public void testHolaMundo() throws Exception {
        String nombre = "Juan";
        this.mockMvc.perform(get("/programa-service/hola/{nombre}", nombre))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola " + nombre));
    }

    /**
     * Prueba del método GET "/programa-service/programaes", que comprueba que se recibe una lista de países en la respuesta.
     * @throws Exception
     */
    @Test
    public void testListar() throws Exception {
        Programa programa1 = new Programa();
        programa1.setNombre("croacia");
        Programa programa2 = new Programa();
        programa2.setNombre("España");
        programaService.save(programa1);
        programaService.save(programa2);
        List<Programa> listaProgramaes = new ArrayList<>();
        listaProgramaes.add(programa1);
        listaProgramaes.add(programa2);

        this.mockMvc.perform(get("/programa-service/programaes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is(programa1.getNombre())))
                .andExpect(jsonPath("$[1].nombre", is(programa2.getNombre())));

        programaService.delete(programa1);
        programaService.delete(programa2);
    }

    /**
     * Prueba del método GET "/programa-service/programaes/{id}", que comprueba que se recibe el país correcto en la respuesta.
     * @throws Exception
     */
    @Test
    public void testBuscarPrograma() throws Exception {
        Programa programa = new Programa();
        programa.setNombre("España");
        programaService.save(programa);

        this.mockMvc.perform(get("/programa-service/programaes/{id}", programa.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(programa.getNombre())));

        programaService.delete(programa);
    }

    /**
     * Prueba del método POST "/programa-service/programaes", que comprueba que se crea un nuevo país correctamente.
     * @throws Exception
     */
    @Test
    public void testCrearPrograma() throws Exception {
        Programa programa = new Programa();
        programa.setNombre("España");

        this.mockMvc.perform(post("/programa-service/programaes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(programa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(programa.getNombre())));

        programaService.delete(programa);
    }

    /**
     * Prueba del método PUT "/programa-service/programaes", que comprueba que se actualiza un país correctamente.
     * @throws Exception
     */
    @Test
    public void testActualizarPrograma() throws Exception {
        Programa programa = new Programa();
        programa.setNombre("España");
        programaService.save(programa);
        programa.setNombre("Portugal");

        this.mockMvc.perform(put("/programa-service/programaes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(programa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(programa.getNombre())));
//                .andExpect(jsonPath("$.capital", is(programa.getNombre())));

        programaService.delete(programa);
    }

    /**
     * Prueba del método DELETE "/programa-service/programaes/{id}", que comprueba que se elimina un país correctamente.
     * @throws Exception
     */
    @Test
    public void testBorrarPrograma() throws Exception {
        Programa programa = new Programa();
        programa.setNombre("Canada");
        programaService.save(programa);

        this.mockMvc.perform(delete("/programa-service/programaes/{id}", programa.getId()))
                .andExpect(status().isOk());

        assertNull(programaService.findById(programa.getId()));
    }

    /**
     * Método para convertir un objeto a una cadena JSON
     *
     * @param obj Objeto a convertir
     * @return Cadena JSON
     */
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

