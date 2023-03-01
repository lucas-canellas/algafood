package com.algaworks.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

    /**
     * O @Value é um recurso do Spring que permite injetar valores de propriedades
     * do arquivo application.properties
     */
    /* @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos; */
    
    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            /**
             * O método copy() da classe FileCopyUtils copia o conteúdo de um InputStream
             * para um OutputStream.
             */
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }

    }

    /**
     * Método que retorna o caminho do arquivo a partir do nome do arquivo.
     * 
     * @param nomeArquivo
     * @return
     */
    private Path getArquivoPath(String nomeArquivo) {
        // O método resolve() da classe Path concatena o nome do arquivo com o diretório
        // de fotos.
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }

    @Override
    public FotoRecuperada recuperar(String nomerArquivo) {        
        try {
            Path arquivoPath = getArquivoPath(nomerArquivo);

            FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(arquivoPath))
                    .build();

            return fotoRecuperada;
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }



}
