package br.blip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe principal que inicia a aplicação Blip.
 *
 * Esta classe contém o método principal (`main`) para iniciar a aplicação Spring Boot.
 * A anotação `@SpringBootApplication` combina várias anotações essenciais para configuração
 * e inicialização de uma aplicação Spring Boot.
 * A anotação `@EnableScheduling` permite o suporte a agendamento de tarefas.
 */
@SpringBootApplication
@EnableScheduling
public class BlipApplication {

    /**
     * Método principal para iniciar a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando passados durante a inicialização da aplicação.
     */
    public static void main(String[] args) {
        SpringApplication.run(BlipApplication.class, args);
    }
}
