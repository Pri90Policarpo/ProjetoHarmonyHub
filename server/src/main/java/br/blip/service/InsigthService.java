package br.blip.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.blip.model.EventTracker;
import br.blip.model.dto.InsightDTO;
import br.blip.model.dto.InsightTemporalDTO;
import br.blip.repository.EventTrackerRepository;

@Service
public class InsigthService {

	

	
    /**
     * Converte o formato da contagem de eventos para uma lista de objetos Map.
     *
     * @param eventCountDTOList Lista de objetos InsightDTO contendo a contagem de eventos.
     * @return Lista de objetos Map representando o formato desejado.
     */
    public List<Object> changeFormatEventCount(List<InsightDTO> eventCountDTOList) {
        return eventCountDTOList.stream()
                .map(dto -> Map.of(dto.getKey(), dto.getValue()))
                .collect(Collectors.toList());
    }
    
    /**
     * Converte o formato da contagem temporal de eventos para uma lista de objetos Map.
     *
     * @param eventCountDTOList Lista de objetos InsightTemporalDTO contendo a contagem temporal de eventos.
     * @return Lista de objetos Map representando o formato desejado.
     */
    public List<Object> changeFormatTemporalEventCount(List<InsightTemporalDTO> eventCountDTOList) {
        return eventCountDTOList.stream()
                .map(dto -> Map.of(dto.getKey(), dto.getValue()))
                .collect(Collectors.toList());
    }
   
    

    
}