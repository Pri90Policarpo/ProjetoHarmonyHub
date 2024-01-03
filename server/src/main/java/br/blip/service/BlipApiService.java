package br.blip.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.blip.model.EventTracker;
import br.blip.repository.EventTrackerRepository;

/**
 * Serviço responsável por interagir com a API do Blip para recuperar eventos no intervalo de datas especificado.
 */
@Service
public class BlipApiService {

    private final RestTemplate restTemplate;
    private final String blipApiKey;
    private final String blipApiCommandUrl;
    private final EventTrackerRepository eventTrackRepository;
    private static final Logger logger = LoggerFactory.getLogger(BlipApiService.class);
    
    
	    
    /**
     * Construtor do serviço BlipApiService.
     *
     * @param restTemplate        Cliente REST utilizado para realizar chamadas à API do Blip.
     * @param blipApiKey          Chave de API necessária para autenticação nas chamadas à API do Blip.
     * @param blipApiCommandUrl   URL base da API do Blip para comandos.
     * @param eventTrackRepository Repositório de dados para armazenar os eventos recuperados.
     */
    public BlipApiService(RestTemplate restTemplate,
                          @Value("${blip.api.key}") String blipApiKey,
                          @Value("${blip.api.command.url}") String blipApiCommandUrl,
                          EventTrackerRepository eventTrackRepository) {
        this.restTemplate = restTemplate;
        this.blipApiKey = blipApiKey;
        this.blipApiCommandUrl = blipApiCommandUrl;
        this.eventTrackRepository = eventTrackRepository;
    }
    
    
    /**
     * Busca dados do Blip no intervalo de datas especificado.
     *
     * @param startDate Data de início do intervalo.
     * @param endDate   Data de término do intervalo.
     * @return Lista de eventos do Blip no intervalo especificado.
     */
    public List<EventTracker> fetchDataFromBlip(LocalDateTime startDate, LocalDateTime endDate) {
        List<EventTracker> result = new ArrayList<>();

        // Passo 1: Recuperar categorias da API do Blip
        
        String requestBody = "{\n" +
                "  \"id\": \"requestid\",\n" +
                "  \"to\": \"postmaster@analytics.msging.net\",\n" +
                "  \"method\": \"get\",\n" +
                "  \"uri\": \"/event-track?startDate=" + startDate + "&endDate=" + endDate + "&$take=10\"\n" +
                "}";
        String categoriesResponse = sendBlipCommand(blipApiCommandUrl, requestBody);

        List<String> categories = extractCategories(categoriesResponse);

        // Passo 2: Para cada categoria, recuperar ações associadas
        for (String category : categories) {
           
            
            String actionRequestBody = "{\n" +
                    "  \"id\": \"requestid\",\n" +
                    "  \"to\": \"postmaster@analytics.msging.net\",\n" +
                    "  \"method\": \"get\",\n" +
                    "  \"uri\": \"/event-track/" + URLEncoder.encode(category, Charset.defaultCharset()) +
                    "?startDate=" + startDate + "&endDate=" + endDate + "&$take=500\"\n" +
                    "}";
            
            String actionsResponse = sendBlipCommand(blipApiCommandUrl, actionRequestBody);

            List<String> actions = extractActions(actionsResponse);

            // Passo 3: Para cada ação, recuperar os dados finais
            for (String action : actions) {
                
                String actionDetailRequestBody = "{\n" +
                        "  \"id\": \"requestid\",\n" +
                        "  \"to\": \"postmaster@analytics.msging.net\",\n" +
                        "  \"method\": \"get\",\n" +
                        "  \"uri\": \"/event-track/" + URLEncoder.encode(category, Charset.defaultCharset()) + "/" + URLEncoder.encode(action, Charset.defaultCharset()) + 
                        "?startDate=" + startDate + "&endDate=" + endDate + "&$take=500\"\n" +
                        "}";
                
                String finalDataResponse = sendBlipCommand(blipApiCommandUrl, actionDetailRequestBody);

                // Adicionar dados ao resultado
                result.addAll(extractEventData(finalDataResponse));
                
            }
        }

        return result;
    }

    
    /**
     * Extrai os dados do evento a partir da resposta da API do Blip.
     *
     * @param finalDataResponse Resposta da API do Blip contendo os dados do evento.
     * @return Lista de eventos extraídos.
     */

    private List<EventTracker> extractEventData(String finalDataResponse) {
        List<EventTracker> eventDataList = new ArrayList<>();

        try {
        	 ObjectMapper objectMapper = new ObjectMapper();
             objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

             JsonNode responseNode = objectMapper.readTree(new String(finalDataResponse.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));        
            
            // Recupera a quantidade de registros, provavelmente necessário pra criar alguma paginação
            JsonNode totalNode = responseNode.get("resource").get("total");            
            
            if (totalNode != null && totalNode.isNumber()) {
                long totalValue = totalNode.asLong();
            } else {
            	long totalValue = 0;
            }
            

            if (responseNode.has("resource") && responseNode.get("resource").has("items")) {
                ArrayNode itemsNode = (ArrayNode) responseNode.get("resource").get("items");
                for (JsonNode item : itemsNode) {
                    // Extrair os dados necessários para visualização
                    String ownerIdentity = item.get("ownerIdentity").asText();
                    String storageDate = item.get("storageDate").asText();
                    String category = item.get("category").asText();
                    String action = item.get("action").asText();
                    String stateName = item.get("extras").get("#stateName").asText();
                    String stateId = item.get("extras").get("#stateId").asText();
                    String messageId = item.get("extras").get("#messageId").asText();
                    String previousStateId = item.get("extras").get("#previousStateId").asText();
                    String previousStateName = item.get("extras").get("#previousStateName").asText();
                   
                    // Criar uma instância do EventTrack
                    EventTracker eventTrack = new EventTracker(ownerIdentity, storageDate, category, action, stateName,
                            stateId, messageId, previousStateId, previousStateName);

                    // Adicionar à lista
                    eventDataList.add(eventTrack);
                    
                    // Armazenar no banco de dados
                    eventTrackRepository.save(eventTrack);                    
                    
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tratar exceção conforme necessário
        }

        return eventDataList;
    }
    
    /**
     * Envia um comando para a API do Blip.
     *
     * @param url  URL da API do Blip.
     * @param body Corpo da requisição.
     * @return Resposta da API do Blip.
     */
    private String sendBlipCommand(String url, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "Key " + blipApiKey);

         HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, requestEntity, String.class);
    }
    
    /**
     * Extrai as categorias da resposta da API do Blip.
     *
     * @param categoriesResponse Resposta da API do Blip contendo as categorias.
     * @return Lista de categorias extraídas.
     */
    private List<String> extractCategories(String categoriesResponse) {
        List<String> categories = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(categoriesResponse);

            if (responseNode.has("resource") && responseNode.get("resource").has("items")) {
                ArrayNode itemsNode = (ArrayNode) responseNode.get("resource").get("items");
                for (JsonNode item : itemsNode) {
                    if (item.has("category")) {
                        categories.add(item.get("category").asText());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Trate a exceção de acordo com a necessidade
        }

        return categories;
    }

    /**
     * Extrai as ações da resposta da API do Blip.
     *
     * @param actionsResponse Resposta da API do Blip contendo as ações.
     * @return Lista de ações extraídas.
     */
    private List<String> extractActions(String actionsResponse) {
        List<String> actions = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(actionsResponse);

            if (responseNode.has("resource") && responseNode.get("resource").has("items")) {
                ArrayNode itemsNode = (ArrayNode) responseNode.get("resource").get("items");
                for (JsonNode item : itemsNode) {
                    if (item.has("action")) {
                        actions.add(item.get("action").asText());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Trate a exceção de acordo com a necessidade
        }

        return actions;
    }

    /**
     * Método executado automaticamente ao iniciar o Spring.
     * Realiza uma busca inicial de dados do Blip no intervalo específico.
     */
    @PostConstruct
    public void init() {
        // Executar imediatamente quando o Spring iniciar
    	    	
    	  LocalDateTime startDate = LocalDateTime.of(2023, 12, 28, 0, 0);
    	  LocalDateTime endDate = LocalDateTime.of(2023, 12, 30, 23, 59, 59);
    	  fetchDataFromBlip(startDate, endDate);    	
        
        logger.info("O método init() foi executado com sucesso!");
    }
    
    /**
     * Método agendado para executar periodicamente.
     * Busca dados do Blip em um intervalo ajustado para a próxima hora.
     */
    @Scheduled(cron = "0 * * * * *") // Executa a cada minuto
    public void fetchDataPeriodically() {
        // Ajustar as horas, minutos e segundos para estar sempre dentro do range da próxima hora
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime startDate = now.withHour(0).withMinute(0).withSecond(1);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59);
        
        logger.info("Executando fetchDataPeriodically. Intervalo de {} a {}", startDate, endDate);

        
        fetchDataFromBlip(startDate, endDate);
    }
    /**
     * Obtém todos os dados da tabela EventTracker.
     *
     * @return Lista de todos os eventos do Blip.
     */
    public List<EventTracker> getAllData() {
        return eventTrackRepository.findAll();
    }
       
    
}
