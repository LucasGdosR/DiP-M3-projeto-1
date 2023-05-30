package tech.devinhouse.dipm3projeto1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.dipm3projeto1.model.Passageiro;
import tech.devinhouse.dipm3projeto1.service.PassageiroService;

@RestController
@RequestMapping("/api/passageiros")
public class PassageirosController {
    private final PassageiroService service;

    public PassageirosController(PassageiroService service) {
        this.service = service;
    }

    @GetMapping("/{cpf}")
    public Passageiro findByCpf(@PathVariable String cpf) {
        return service.findByCpf(cpf);
    }
}
