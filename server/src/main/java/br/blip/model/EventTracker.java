package br.blip.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Entidade que representa o modelo de dados do EventTracker.
 */
@ApiModel
@Entity
@Table(name = "eventtracker", schema = "blip_db")
public class EventTracker {

    @ApiModelProperty(notes = "Identidade do proprietário")
    private String ownerIdentity;

    @ApiModelProperty(notes = "Data de armazenamento do evento")
    private String storageDate;

    @ApiModelProperty(notes = "Categoria do evento")
    private String category;

    @ApiModelProperty(notes = "Ação associada ao evento")
    private String action;

    @ApiModelProperty(notes = "Nome do estado do evento")
    private String stateName;

    @ApiModelProperty(notes = "Identificador do estado do evento")
    private String stateId;

    @Id
    @ApiModelProperty(notes = "Identificador único da mensagem associada ao evento")
    private String messageId;

    @ApiModelProperty(notes = "Identificador do estado anterior ao evento")
    private String previousStateId;

    @ApiModelProperty(notes = "Nome do estado anterior ao evento")
    private String previousStateName;

    // O Hibernate requer um construtor padrão para instanciar a classe ao recuperar dados do banco de dados.
    public EventTracker() {

    }

    /**
     * Construtor para a classe EventTracker.
     *
     * @param ownerIdentity      Identidade do proprietário.
     * @param storageDate        Data de armazenamento do evento.
     * @param category           Categoria do evento.
     * @param action             Ação associada ao evento.
     * @param stateName          Nome do estado do evento.
     * @param stateId            Identificador do estado do evento.
     * @param messageId          Identificador único da mensagem associada ao evento.
     * @param previousStateId    Identificador do estado anterior ao evento.
     * @param previousStateName  Nome do estado anterior ao evento.
     */
    public EventTracker(String ownerIdentity, String storageDate, String category, String action, String stateName, String stateId,
            String messageId, String previousStateId, String previousStateName) {
        super();

        this.ownerIdentity = ownerIdentity;
        this.storageDate = storageDate;
        this.category = category;
        this.action = action;
        this.stateName = stateName;
        this.stateId = stateId;
        this.messageId = messageId;
        this.previousStateId = previousStateId;
        this.previousStateName = previousStateName;
    }

    @Basic
    @Column(name = "storagedate", nullable = false)
    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    @Basic
    @Column(name = "category", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "action", nullable = false)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "statename", nullable = false)
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Basic
    @Column(name = "stateid", nullable = false)
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "messageid", nullable = false, unique = true)
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "previousstateid", nullable = false)
    public String getPreviousStateId() {
        return previousStateId;
    }

    public void setPreviousStateId(String previousStateId) {
        this.previousStateId = previousStateId;
    }

    @Basic
    @Column(name = "previousstatename", nullable = false)
    public String getPreviousStateName() {
        return previousStateName;
    }

    public void setPreviousStateName(String previousStateName) {
        this.previousStateName = previousStateName;
    }

    @Basic
    @Column(name = "owneridentity", nullable = false)
    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

}
