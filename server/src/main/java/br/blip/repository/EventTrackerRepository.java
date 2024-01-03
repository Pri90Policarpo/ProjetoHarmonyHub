package br.blip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.blip.model.EventTracker;
import br.blip.model.dto.InsightDTO;
import br.blip.model.dto.InsightTemporalDTO;

/**
 * Interface do repositório Spring Data JPA para a entidade EventTracker.
 */
public interface EventTrackerRepository extends JpaRepository<EventTracker, String> {
    
    /**
     * Conta o número de eventos por categoria.
     *
     * @return Lista de InsightDTO contendo a categoria e a contagem de eventos.
     */
    @Query("SELECT new br.blip.model.dto.InsightDTO(category, COUNT(*)) FROM EventTracker GROUP BY category")
    List<InsightDTO> countEventsByCategory();

    /**
     * Conta o número de eventos por ação.
     *
     * @return Lista de InsightDTO contendo a ação e a contagem de eventos.
     */
    @Query("SELECT new br.blip.model.dto.InsightDTO(action, COUNT(*)) FROM EventTracker GROUP BY action")
    List<InsightDTO> countEventsByAction();
            
    /**
     * Distribuição temporal do número de eventos por hora.
     *
     * @return Lista de InsightTemporalDTO contendo a data truncada por hora e a contagem de eventos.
     */
    @Query("SELECT new br.blip.model.dto.InsightTemporalDTO(date_trunc('hour', to_timestamp(storagedate, 'YYYY-MM-DD\"T\"HH24')), COUNT(*)) FROM EventTracker GROUP by date_trunc('hour', to_timestamp(storagedate, 'YYYY-MM-DD\"T\"HH24')) ORDER BY date_trunc('hour', to_timestamp(storagedate, 'YYYY-MM-DD\"T\"HH24'))")
    List<InsightTemporalDTO> temporalDistribution();

    /**
     * Conta o número de eventos por nome do estado.
     *
     * @return Lista de InsightDTO contendo o nome do estado e a contagem de eventos.
     */
    @Query("SELECT new br.blip.model.dto.InsightDTO(stateName, COUNT(*)) FROM EventTracker GROUP BY stateName")
    List<InsightDTO> countEventsByStateName();

    /**
     * Conta o número de eventos por ID do estado.
     *
     * @return Lista de InsightDTO contendo o ID do estado e a contagem de eventos.
     */
    @Query("SELECT new br.blip.model.dto.InsightDTO(stateId, COUNT(*)) FROM EventTracker GROUP BY stateId")
    List<InsightDTO> countEventsByStateId();

    /**
     * Conta o número de eventos por nome do estado anterior.
     *
     * @return Lista de InsightDTO contendo o nome do estado anterior e a contagem de eventos.
     */
    @Query("SELECT new br.blip.model.dto.InsightDTO(previousStateName, COUNT(*)) FROM EventTracker GROUP BY previousStateName")
    List<InsightDTO> countEventsByPreviousStateName();
}
