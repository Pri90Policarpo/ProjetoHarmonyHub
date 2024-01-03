package br.blip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configuração de Segurança da Aplicação Blip.
 *
 * Esta classe estende WebSecurityConfigurerAdapter para fornecer configurações de segurança personalizadas.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configurações de segurança HTTP.
     *
     * @param http Configuração do objeto HttpSecurity.
     * @throws Exception Exceção em caso de erro na configuração.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**") // Permite acesso irrestrito a todas as URLs
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().disable() // Desativa autenticação básica
            .csrf().disable(); // Desativa CSRF para simplificar o exemplo
    }
}
