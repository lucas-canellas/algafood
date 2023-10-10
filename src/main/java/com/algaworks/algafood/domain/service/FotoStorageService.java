package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomerArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        if (nomeArquivoExistente != null) {
            this.remover(nomeArquivoExistente);
        }        
    }

    /**
     * Default é uma interface que permite a implementação de métodos em uma interface, sem que seja necessário implementá-los nas classes que a implementam.
     * @param nomeOriginal
     * @return
     */
    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Getter
    @Builder
    class NovaFoto {
        /**
         * Inputstream é uma classe abstrata que permite a leitura dos dados de fontes externas, como arquivos, fotos, etc.
         * @return InputStream
         */
        private InputStream inputStream;
        private String nomeArquivo;
        private String contentType;
        private Long size;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }
    }
}
