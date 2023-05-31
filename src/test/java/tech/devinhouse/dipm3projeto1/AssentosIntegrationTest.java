package tech.devinhouse.dipm3projeto1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AssentosIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar uma lista de 60 assentos, sendo o primeiro 1A, e o último 10F.")
    void assentosIntegrationTest() throws Exception {
        mockMvc.perform(get("/api/assentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(60))
                .andExpect(jsonPath("$[0]").value("1A"))
                .andExpect(jsonPath("$[59]").value("10F"));
    }
}