package tech.devinhouse.dipm3projeto1.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.dipm3projeto1.dto.ConfirmacaoRequestDto;
import tech.devinhouse.dipm3projeto1.dto.ConfirmacaoResponseDto;
import tech.devinhouse.dipm3projeto1.dto.PassageiroFullResponseDto;
import tech.devinhouse.dipm3projeto1.dto.PassageiroResponseDto;
import tech.devinhouse.dipm3projeto1.service.PassageiroService;

import java.util.List;

@RestController
@RequestMapping("/api/passageiros")
public class PassageirosController {
    private final PassageiroService service;

    public PassageirosController(PassageiroService service) {
        this.service = service;
    }

    @GetMapping("/{cpf}")
    public PassageiroResponseDto findByCpf(@PathVariable String cpf) {
        return new PassageiroResponseDto(service.findByCpf(cpf));
    }

    @PostMapping("/confirmacao")
    public ConfirmacaoResponseDto checkIn(@RequestBody @Valid ConfirmacaoRequestDto request) {
        return new ConfirmacaoResponseDto(service.checkIn(request));
    }

    @GetMapping
    public List<PassageiroFullResponseDto> findAll() {
        return service.findAll().stream().map(PassageiroFullResponseDto::new).toList();
    }
}
