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
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Confirmacao checkIn(ConfirmacaoRequestDto request) {
        Passageiro passageiro = passageiroRepository.findById(request.getCpf()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String assento = request.getAssento();

        if (!assentosService.findAll().contains(assento))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (confirmacaoRepository.existsByAssento(assento))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        boolean fileiraDeEmergencia = (assento.charAt(0) == '5') || (assento.charAt(0) == '6');

        LocalDate dataDeMaioridade = passageiro.getDataNascimento().minusYears(18);
        boolean ehMenorDeIdade = dataDeMaioridade.isAfter(LocalDate.now());

        if (fileiraDeEmergencia && ehMenorDeIdade)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (fileiraDeEmergencia && !request.getMalasDespachadas())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

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
}
