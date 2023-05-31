package tech.devinhouse.dipm3projeto1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.dipm3projeto1.dto.ConfirmacaoRequestDto;
import tech.devinhouse.dipm3projeto1.dto.ConfirmacaoResponseDto;
import tech.devinhouse.dipm3projeto1.dto.PassageiroFullResponseDto;
import tech.devinhouse.dipm3projeto1.dto.PassageiroResponseDto;
import tech.devinhouse.dipm3projeto1.model.Classificacao;
import tech.devinhouse.dipm3projeto1.model.Confirmacao;
import tech.devinhouse.dipm3projeto1.model.Passageiro;
import tech.devinhouse.dipm3projeto1.service.AssentosService;
import tech.devinhouse.dipm3projeto1.service.PassageiroService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class PassageirosControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private PassageiroService passageiroService;
    @MockBean
    private AssentosService assentosService;
    private Passageiro passageiroMockUp;
    private ConfirmacaoRequestDto requestDtoMockUp;

    /**
     * GET -> findAll()
     */
    @BeforeEach
    void setUp() {
        passageiroMockUp = new Passageiro();
        passageiroMockUp.setCpf("000.000.000-00");
        passageiroMockUp.setNome("Rachel Green");
        passageiroMockUp.setDataNascimento(LocalDate.of(1969, 1, 11));
        passageiroMockUp.setClassificacao(Classificacao.VIP);
        passageiroMockUp.setMilhas(100);
        passageiroMockUp.setConfirmacao(null);

        requestDtoMockUp = new ConfirmacaoRequestDto();
        requestDtoMockUp.setCpf(passageiroMockUp.getCpf());
        requestDtoMockUp.setAssento("1A");
        requestDtoMockUp.setMalasDespachadas(true);
    }

    @Test
    void shouldFindAllPassengers() throws Exception {
        List<Passageiro> expectedList = List.of(passageiroMockUp);
        when(passageiroService.findAll()).thenReturn(expectedList);

        List<PassageiroFullResponseDto> expectedResponse = List.of(new PassageiroFullResponseDto(passageiroMockUp));
        String expectedJson = mapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/api/passageiros"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    /**
     * GET -> findByCpf(cpf)
     */
    @Test
    void shouldFindByCpf() throws Exception {
        when(passageiroService.findByCpf(passageiroMockUp.getCpf())).thenReturn(passageiroMockUp);

        String expectedJson = mapper.writeValueAsString(new PassageiroResponseDto(passageiroMockUp));

        mockMvc.perform(get("/api/passageiros/"+passageiroMockUp.getCpf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldReturn404WhenCpfNonexistent() throws Exception {
        when(passageiroService.findByCpf("cpfinexistente")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/passageiros/cpfinexistente"))
                .andExpect(jsonPath("$").doesNotExist())
                .andExpect(status().isNotFound());
    }

    /**
     * POST -> checkIn(request)
     */
    @Test
    void shouldReturnResponseDtoWhenEverythingOk() throws Exception {
        Confirmacao confirmacao = new Confirmacao(requestDtoMockUp.getAssento());
        when(passageiroService.checkIn(any())).thenReturn(confirmacao);
        String expectedJson = mapper.writeValueAsString(new ConfirmacaoResponseDto(confirmacao));

        String requestJson = mapper.writeValueAsString(requestDtoMockUp);

        mockMvc.perform(post("/api/passageiros/confirmacao")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
