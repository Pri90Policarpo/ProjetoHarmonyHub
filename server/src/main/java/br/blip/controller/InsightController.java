package br.blip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.blip.model.dto.InsightDTO;
import br.blip.model.dto.InsightTemporalDTO;
import br.blip.repository.EventTrackerRepository;
import br.blip.service.InsigthService;

/**
 * Controlador para lidar com solicitações relacionadas a análises de dados.
 */
@RestController
@RequestMapping("/analises")
public class InsightController {

    @Autowired
    private EventTrackerRepository eventTrackerRepository;

    private final InsigthService insightService;

    /**
     * Construtor para o controlador InsightController.
     *
     * @param eventTrackerRepository Repositório para interagir com dados de eventos.
     * @param insightService         Serviço para realizar análises de dados.
     */
    public InsightController(EventTrackerRepository eventTrackerRepository, InsigthService insightService) {
        this.eventTrackerRepository = eventTrackerRepository;
        this.insightService = insightService;
    }

    /**
     * Endpoint para obter a contagem de eventos por categoria.
     *
     * @return Lista de objetos representando a contagem de eventos por categoria.
     */
    @GetMapping("/eventos-por-categoria")
    @ResponseBody
    public List<Object> getEventosPorCategoria() {
        List<InsightDTO> countEventsByCategory = eventTrackerRepository.countEventsByCategory();
        return insightService.changeFormatEventCount(countEventsByCategory);
    }

    /**
     * Endpoint para obter a contagem de eventos por ação.
     *
     * @return Lista de objetos representando a contagem de eventos por ação.
     */
    @GetMapping("/eventos-por-acao")
    @ResponseBody
    public List<Object> getEventosPorAcao() {
        List<InsightDTO> countEventsByAction = eventTrackerRepository.countEventsByAction();
        return insightService.changeFormatEventCount(countEventsByAction);
    }

    /**
     * Endpoint para obter a distribuição temporal dos eventos.
     *
     * @return Lista de objetos representando a distribuição temporal dos eventos.
     */
    @GetMapping("/distribuicao-temporal")
    public List<Object> getDistribuicaoTemporal() {
        List<InsightTemporalDTO> temporalDistribution = eventTrackerRepository.temporalDistribution();
        return insightService.changeFormatTemporalEventCount(temporalDistribution);
    }

    /**
     * Endpoint para obter a contagem de eventos por nome do estado.
     *
     * @return Lista de objetos representando a contagem de eventos por nome do estado.
     */
    @GetMapping("/eventos-por-nome-do-estado")
    @ResponseBody
    public List<Object> getEventosPorNomeDoEstado() {
        List<InsightDTO> countEventsByStateName = eventTrackerRepository.countEventsByStateName();
        return insightService.changeFormatEventCount(countEventsByStateName);
    }

    /**
     * Endpoint para obter a contagem de eventos por ID do estado.
     *
     * @return Lista de objetos representando a contagem de eventos por ID do estado.
     */
    @GetMapping("/eventos-por-id-do-estado")
    @ResponseBody
    public List<Object> getEventosPorIdDoEstado() {
        List<InsightDTO> countEventsByStateId = eventTrackerRepository.countEventsByStateId();
        return insightService.changeFormatEventCount(countEventsByStateId);
    }

    /**
     * Endpoint para obter a contagem de eventos por nome do estado anterior.
     *
     * @return Lista de objetos representando a contagem de eventos por nome do estado anterior.
     */
    @GetMapping("/eventos-por-nome-do-estado-anterior")
    @ResponseBody
    public List<Object> getEventosPorNomeDoEstadoAnterior() {
        List<InsightDTO> countEventsByPreviousStateName = eventTrackerRepository.countEventsByPreviousStateName();
        return insightService.changeFormatEventCount(countEventsByPreviousStateName);
    }
    
    
    @RequestMapping(value ="/chart", method=RequestMethod.GET)
    public ModelAndView getWelcomePageAsModel() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("insight-chart.html");
        return modelAndView;
    }
    

}
