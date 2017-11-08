package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuGuzergah.
 */
@Entity
@Table(name = "oku_guzergah")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okuguzergah")
public class OkuGuzergah implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "rota")
    private String rota;

    @Column(name = "harita")
    private String harita;

    @ManyToOne
    private OkuOkul okul;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKod() {
        return kod;
    }

    public OkuGuzergah kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getRota() {
        return rota;
    }

    public OkuGuzergah rota(String rota) {
        this.rota = rota;
        return this;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public String getHarita() {
        return harita;
    }

    public OkuGuzergah harita(String harita) {
        this.harita = harita;
        return this;
    }

    public void setHarita(String harita) {
        this.harita = harita;
    }

    public OkuOkul getOkul() {
        return okul;
    }

    public OkuGuzergah okul(OkuOkul okuOkul) {
        this.okul = okuOkul;
        return this;
    }

    public void setOkul(OkuOkul okuOkul) {
        this.okul = okuOkul;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OkuGuzergah okuGuzergah = (OkuGuzergah) o;
        if (okuGuzergah.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuGuzergah.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuGuzergah{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", rota='" + getRota() + "'" +
            ", harita='" + getHarita() + "'" +
            "}";
    }
}
