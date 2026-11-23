package ma.enset.tp1.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CompteGraphQLTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Test
    void creationEtLectureGraphQL() throws Exception {
        String mutation = mapper.writeValueAsString(Map.of("query",
            "mutation { createCompte(input:{solde:450.0,typeCompte:EPARGNE,devise:\"MAD\"}) { id solde typeCompte } }"));
        mvc.perform(post("/graphql").contentType(MediaType.APPLICATION_JSON).content(mutation))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.createCompte.typeCompte").value("EPARGNE"));
        String query = mapper.writeValueAsString(Map.of("query",
            "query { allComptes { id solde typeCompte } }"));
        mvc.perform(post("/graphql").contentType(MediaType.APPLICATION_JSON).content(query))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.allComptes").isArray());
    }
}
