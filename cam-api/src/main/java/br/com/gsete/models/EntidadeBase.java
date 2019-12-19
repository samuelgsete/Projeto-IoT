package br.com.gsete.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class EntidadeBase {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(value = "id", required = true)
	protected Long id;
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
}