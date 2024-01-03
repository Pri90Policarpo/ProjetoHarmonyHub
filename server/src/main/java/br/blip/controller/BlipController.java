package br.blip.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.blip.model.EventTracker;
import br.blip.service.BlipApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controlador para lidar com solicitações relacionadas ao Blip.
 */
@RestController
@RequestMapping("/blip")
public class BlipController {

    private final BlipApiService blipApiService;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    /**
     * Construtor para o controlador BlipController.
     *
     * @param blipApiService Serviço para interagir com a API do Blip.
     */
    public BlipController(BlipApiService blipApiService) {
        this.blipApiService = blipApiService;
        this.startDate = LocalDateTime.now().withHour(00).withMinute(00).withSecond(01).truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
        this.endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).truncatedTo(java.time.temporal.ChronoUnit.SECONDS);
    }

    /**
     * Endpoint para buscar e mostrar dados do Blip dentro do intervalo de datas especificado.
     *
     * @param startDateString Data de início no formato 'yyyy-MM-ddTHH:mm:ss'.
     * @param endDateString   Data de término no formato 'yyyy-MM-ddTHH:mm:ss'.
     * @return Lista de eventos do Blip dentro do intervalo de datas especificado.
     */
    @GetMapping("/fetch-and-show")
    @ApiOperation("Buscar e mostrar dados do Blip dentro do intervalo de datas especificado.")
    public List<EventTracker> fetchAndShowData(
            @ApiParam(value = "Data de início no formato 'yyyy-MM-ddTHH:mm:ss'. Se não fornecida, o valor padrão (Data de hoje) será usado.", defaultValue = "2023-12-29T00:00:01")
            @RequestParam(name = "startDate", required = false)
                    String startDateString,

            @ApiParam(value = "Data de término no formato 'yyyy-MM-ddTHH:mm:ss'. Se não fornecida, o valor padrão (Data de hoje) será usado.", defaultValue = "2023-12-29T23:59:59")
            @RequestParam(name = "endDate", required = false)
                    String endDateString
    ) {

        // Converter strings para LocalDateTime
        LocalDateTime startDate = parseToLocalDateTime(startDateString, "yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime endDate = parseToLocalDateTime(endDateString, "yyyy-MM-dd'T'HH:mm:ss");

        // Se os parâmetros não foram fornecidos, use os valores padrão
        if (startDate == null) {
            startDate = this.startDate;
        }

        if (endDate == null) {
            endDate = this.endDate;
        }

        // Obter dados para visualização a partir do serviço
        return blipApiService.fetchDataFromBlip(startDate, endDate);
    }

    /**
     * Método para converter uma string no formato específico para LocalDateTime.
     *
     * @param dateString String que representa a data.
     * @param pattern    Padrão para análise da data.
     * @return LocalDateTime convertido a partir da string, ou null se a string for nula ou vazia.
     */
    private LocalDateTime parseToLocalDateTime(String dateString, String pattern) {
        if (dateString != null && !dateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateString, formatter);
        }
        return null;
    }
    
    
    /**
     * Endpoint para buscar e mostrar todos os dados da tabela EventTracker.
     *
     * @return Lista de todos os eventos do Blip.
     */
    @GetMapping("/all-data")
    @ApiOperation("Buscar e mostrar todos os dados da tabela EventTracker.")
    @ResponseBody
    public List<EventTracker> getAllData() {
        // Obtém todos os dados da tabela
        return blipApiService.getAllData();
    }
    
}
