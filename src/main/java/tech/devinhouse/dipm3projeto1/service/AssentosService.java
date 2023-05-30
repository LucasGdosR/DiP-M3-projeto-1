package tech.devinhouse.dipm3projeto1.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssentosService {
    private final int NUMERO_DE_FILEIRAS = 10;
    private final int ASSENTOS_POR_FILEIRA = 6;
    private final List<String> assentosRepository;

    public AssentosService() {
        assentosRepository = new ArrayList<>();

        for (int i = 1; i <= NUMERO_DE_FILEIRAS; i++)
            for (int j = 0; j < ASSENTOS_POR_FILEIRA; j++) {
                String assento = String.valueOf(i) + (char) ('A' + j);
                assentosRepository.add(assento);
            }
    }

    public List<String> findAll() {
        return assentosRepository;
    }
}
