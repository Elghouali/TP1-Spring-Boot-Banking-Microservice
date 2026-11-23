package ma.enset.tp1.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CompteRestControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Test
    void creationEtConsultationREST() throws Exception {
        mvc.perform(post("/api/comptes").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("solde", 1000, "typeCompte", "COURANT", "devise", "MAD"))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.solde", is(1000)))
            .andExpect(jsonPath("$.typeCompte", is("COURANT")));
        mvc.perform(get("/api/comptes")).andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(java.util.List.class)));
    }

    @Test
    void validationDuMontant() throws Exception {
        mvc.perform(post("/api/comptes").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Map.of("solde", -1, "typeCompte", "EPARGNE"))))
            .andExpect(status().isBadRequest());
    }
}
