package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuYolcu.
 */
@Entity
@Table(name = "oku_yolcu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okuyolcu")
public class OkuYolcu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bindi_mi")
    private Boolean bindiMi;

    @Column(name = "ulasti_mi")
    private Boolean ulastiMi;

    @ManyToOne
    private OkuSefer sefer;

    @ManyToOne
    private OkuOgrenci ogrenci;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isBindiMi() {
        return bindiMi;
    }

    public OkuYolcu bindiMi(Boolean bindiMi) {
        this.bindiMi = bindiMi;
        return this;
    }

    public void setBindiMi(Boolean bindiMi) {
        this.bindiMi = bindiMi;
    }

    public Boolean isUlastiMi() {
        return ulastiMi;
    }

    public OkuYolcu ulastiMi(Boolean ulastiMi) {
        this.ulastiMi = ulastiMi;
        return this;
    }

    public void setUlastiMi(Boolean ulastiMi) {
        this.ulastiMi = ulastiMi;
    }

    public OkuSefer getSefer() {
        return sefer;
    }

    public OkuYolcu sefer(OkuSefer okuSefer) {
        this.sefer = okuSefer;
        return this;
    }

    public void setSefer(OkuSefer okuSefer) {
        this.sefer = okuSefer;
    }

    public OkuOgrenci getOgrenci() {
        return ogrenci;
    }

    public OkuYolcu ogrenci(OkuOgrenci okuOgrenci) {
        this.ogrenci = okuOgrenci;
        return this;
    }

    public void setOgrenci(OkuOgrenci okuOgrenci) {
        this.ogrenci = okuOgrenci;
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
        OkuYolcu okuYolcu = (OkuYolcu) o;
        if (okuYolcu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuYolcu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuYolcu{" +
            "id=" + getId() +
            ", bindiMi='" + isBindiMi() + "'" +
            ", ulastiMi='" + isUlastiMi() + "'" +
            "}";
    }
}
