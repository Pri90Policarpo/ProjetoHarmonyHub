package br.blip.model.dto;

/**
 * DTO (Data Transfer Object) para representar o insight com uma chave (key) e um valor (value).
 */
public class InsightDTO {

    private String key;
    private Long value;

    public InsightDTO(String key, Long value) {
        this.key = key;
        this.value = value;
    }

    // Construtor vazio necessário para serialização/desserialização JSON
    public InsightDTO() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
