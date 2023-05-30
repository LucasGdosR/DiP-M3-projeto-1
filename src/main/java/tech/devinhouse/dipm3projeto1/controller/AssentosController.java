package tech.devinhouse.dipm3projeto1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.dipm3projeto1.service.AssentosService;

import java.util.List;

@RestController
@RequestMapping("/api/assentos")
public class AssentosController {
    private final AssentosService service;

    public AssentosController(AssentosService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> findAll() {
        return service.findAll();
    }
}
