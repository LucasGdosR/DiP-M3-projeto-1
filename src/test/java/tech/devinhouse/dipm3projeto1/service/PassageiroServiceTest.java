package tech.devinhouse.dipm3projeto1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.dipm3projeto1.dto.ConfirmacaoRequestDto;
import tech.devinhouse.dipm3projeto1.model.Classificacao;
import tech.devinhouse.dipm3projeto1.model.Confirmacao;
import tech.devinhouse.dipm3projeto1.model.Passageiro;
import tech.devinhouse.dipm3projeto1.repository.ConfirmacaoRepository;
import tech.devinhouse.dipm3projeto1.repository.PassageiroRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassageiroServiceTest {

    @Mock
    private PassageiroRepository passageiroRepository;
    @Mock
    private AssentosService assentosService;
    @Mock
    private ConfirmacaoRepository confirmacaoRepository;
    @InjectMocks
    private PassageiroService service;

    private Passageiro passageiroMockUp;
    private ConfirmacaoRequestDto requestMockUp;

    @BeforeEach
    void setUp() {
        passageiroMockUp = new Passageiro();
        passageiroMockUp.setCpf("000.000.000-00");
        passageiroMockUp.setNome("Rachel Green");
        passageiroMockUp.setDataNascimento(LocalDate.of(1969, 1, 11));
        passageiroMockUp.setClassificacao(Classificacao.VIP);
        passageiroMockUp.setMilhas(100);
        passageiroMockUp.setConfirmacao(null);

        requestMockUp = new ConfirmacaoRequestDto();
        requestMockUp.setCpf("000.000.000-00");
        requestMockUp.setAssento("1A");
        requestMockUp.setMalasDespachadas(true);
    }

    /**
     * GET -> findAll()
     */
    @Test
    void shouldFindAllPassengersInRepository() {
        List<Passageiro> expectedPassengers = List.of(passageiroMockUp);
        when(passageiroRepository.findAll()).thenReturn(expectedPassengers);

        List<Passageiro> receivedPassengers = service.findAll();

        assertNotNull(receivedPassengers);
        assertEquals(expectedPassengers.size(), receivedPassengers.size());
        assertEquals(passageiroMockUp, receivedPassengers.get(0));
    }

    @Test
    void shouldFindEmptyListWhenRepositoryIsEmpty() {
        List<Passageiro> passengers = service.findAll();

        assertNotNull(passengers);
        assertInstanceOf(List.class, passengers);
        assertTrue(passengers.isEmpty());
        assertDoesNotThrow(() -> service.findAll());
    }

    /**
     * GET -> findByCpf(cpf)
     */
    @Test
    void shouldFindByCpf() {
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));

        Passageiro receivedPassenger = service.findByCpf(passageiroMockUp.getCpf());

        assertEquals(passageiroMockUp, receivedPassenger);
    }

    @Test
    void shouldThrowWhenCpfNonexistent() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.findByCpf("cpf inexistente"));
        assertEquals(exception.getMessage(), "404 NOT_FOUND \"CPF não cadastrado.\"");
    }

    /**
     * POST -> checkIn(request)
     */
    @Test
    void shouldConfirmCheckInAndIncrementMiles() {
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));
        when(assentosService.findAll()).thenReturn(List.of(requestMockUp.getAssento()));
        int oldMiles = passageiroMockUp.getMilhas();

        Confirmacao confirmacao = service.checkIn(requestMockUp);
        assertEquals(requestMockUp.getAssento(), confirmacao.getAssento());
        assertEquals(oldMiles + passageiroMockUp.getClassificacao().milhas, passageiroMockUp.getMilhas());
    }

    @Test
    void shouldThrowWhenPassengerHasAlreadyCheckedIn() {
        Confirmacao confirmacao = new Confirmacao("1A");
        passageiroMockUp.setConfirmacao(confirmacao);
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.checkIn(requestMockUp));
        assertEquals(exception.getMessage(), "409 CONFLICT \"Passageiro já realizou check-in.\"");
    }

    @Test
    void shouldThrowWhenSeatNonexistent() {
        requestMockUp.setAssento("assento inexistente");
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.checkIn(requestMockUp));
        assertEquals(exception.getMessage(), "404 NOT_FOUND \"Assento não existente.\"");
    }

    @Test
    void shouldThrowWhenSeatOccupied() {
        when(confirmacaoRepository.existsByAssento(requestMockUp.getAssento())).thenReturn(true);
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));
        when(assentosService.findAll()).thenReturn(List.of(requestMockUp.getAssento()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.checkIn(requestMockUp));
        assertEquals(exception.getMessage(), "409 CONFLICT \"Assento ocupado.\"");
    }

    @Test
    void shouldThrowWhenUnderageInEmergencyRow() {
        passageiroMockUp.setDataNascimento(LocalDate.now().minusYears(18).plusDays(1));
        requestMockUp.setAssento("5A");
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));
        when(assentosService.findAll()).thenReturn(List.of(requestMockUp.getAssento()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.checkIn(requestMockUp));
        assertEquals(exception.getMessage(),
                "400 BAD_REQUEST \"Fileiras de emergência só podem ser ocupadas por passageiros maiores de idade.\"");
    }

    @Test
    void shouldThrowWhenInEmergencyRowWithCarryOnBaggage() {
        requestMockUp.setAssento("5A");
        requestMockUp.setMalasDespachadas(false);
        when(passageiroRepository.findById(passageiroMockUp.getCpf())).thenReturn(Optional.of(passageiroMockUp));
        when(assentosService.findAll()).thenReturn(List.of(requestMockUp.getAssento()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.checkIn(requestMockUp));
        assertEquals(exception.getMessage(),
                "400 BAD_REQUEST \"Obrigatório despachar malas nas fileiras de emergência.\"");
    }
}
