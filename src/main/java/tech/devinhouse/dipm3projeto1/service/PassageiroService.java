package tech.devinhouse.dipm3projeto1.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.devinhouse.dipm3projeto1.dto.ConfirmacaoRequestDto;
import tech.devinhouse.dipm3projeto1.model.Confirmacao;
import tech.devinhouse.dipm3projeto1.model.Passageiro;
import tech.devinhouse.dipm3projeto1.repository.ConfirmacaoRepository;
import tech.devinhouse.dipm3projeto1.repository.PassageiroRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PassageiroService {
    private final PassageiroRepository passageiroRepository;
    private final AssentosService assentosService;
    private final ConfirmacaoRepository confirmacaoRepository;

    public PassageiroService(PassageiroRepository passageiroRepository, AssentosService assentosService, ConfirmacaoRepository confirmacaoRepository) {
        this.passageiroRepository = passageiroRepository;
        this.assentosService = assentosService;
        this.confirmacaoRepository = confirmacaoRepository;
    }

    public Passageiro findByCpf(String cpf) {
        return passageiroRepository.findById(cpf).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF não cadastrado."));
    }

    public List<Passageiro> findAll() {
        return passageiroRepository.findAll();
    }

    public Confirmacao checkIn(ConfirmacaoRequestDto request) {
        Passageiro passageiro = findByCpf(request.getCpf());

        validateCheckIn(passageiro, request);

        String assento = request.getAssento();

        Confirmacao confirmacao = new Confirmacao(assento);
        confirmacaoRepository.save(confirmacao);

        passageiro.setMilhas(passageiro.getMilhas() + passageiro.getClassificacao().milhas);
        passageiro.setConfirmacao(confirmacao);
        passageiroRepository.save(passageiro);

        System.out.println(
                "Confirmação feita pelo passageiro de CPF "
                        .concat(passageiro.getCpf())
                        .concat(" com e-ticket ")
                        .concat(confirmacao.getEticket()));

        return confirmacao;
    }

    private void validateCheckIn(Passageiro passageiro, ConfirmacaoRequestDto request) {
        String assento = request.getAssento();

        if (passageiro.getConfirmacao() != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Passageiro já realizou check-in.");

        if (!assentosService.findAll().contains(assento))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assento não existente.");

        if (confirmacaoRepository.existsByAssento(assento))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Assento ocupado.");

        boolean fileiraDeEmergencia = (assento.charAt(0) == '5') || (assento.charAt(0) == '6');

        LocalDate dataDeMaioridade = passageiro.getDataNascimento().plusYears(18);
        boolean ehMenorDeIdade = dataDeMaioridade.isAfter(LocalDate.now());

        if (fileiraDeEmergencia && ehMenorDeIdade)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Fileiras de emergência só podem ser ocupadas por passageiros maiores de idade.");

        if (fileiraDeEmergencia && !request.getMalasDespachadas())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Obrigatório despachar malas nas fileiras de emergência.");
    }
}
