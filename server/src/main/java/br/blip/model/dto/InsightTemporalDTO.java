package br.blip.model.dto;

import java.util.Date;

/**
 * DTO (Data Transfer Object) para representar o insight temporal com uma chave (key) do tipo Date e um valor (value).
 */
public class InsightTemporalDTO {

    private Date key;
    private Long value;

    public InsightTemporalDTO(Date key, Long value) {
        this.key = key;
        this.value = value;
    }

    // Construtor vazio necessário para serialização/desserialização JSON
    public InsightTemporalDTO() {

    }

    public Date getKey() {
        return key;
    }

    public void setKey(Date key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
