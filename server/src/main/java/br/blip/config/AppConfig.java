package br.blip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuração da aplicação Blip.
 *
 * Esta classe contém configurações específicas da aplicação, como a definição de beans,
 * neste caso, um bean para o RestTemplate.
 */
@Configuration
public class AppConfig {

    /**
     * Método que define um bean do tipo RestTemplate.
     *
     * @return Uma instância de RestTemplate que pode ser injetada em outras partes da aplicação.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
