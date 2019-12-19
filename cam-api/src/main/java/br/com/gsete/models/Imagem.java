package br.com.gsete.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Imagem")
public class Imagem extends EntidadeBase {

    @Column(name="data", nullable = true)
    @Temporal(TemporalType.DATE)
    @JsonProperty(value = "data", required = true)
    protected Date data;

    @Column(name = "base64", length = 10485760)
	@JsonProperty(value = "base64")
    private String base64;

    public Imagem() { }


    public Imagem(Date data, String base64) {
        this.data = data;
        this.base64 = base64;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getBase64() {
        return this.base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Imagem data(Date data) {
        this.data = data;
        return this;
    }

    public Imagem base64(String base64) {
        this.base64 = base64;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " data='" + getData() + "'" +
            ", base64='" + getBase64() + "'" +
            "}";
    }
}