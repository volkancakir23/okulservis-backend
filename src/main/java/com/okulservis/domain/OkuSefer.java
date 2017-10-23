package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.okulservis.domain.enumeration.OkuServis;

/**
 * A OkuSefer.
 */
@Entity
@Table(name = "oku_sefer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okusefer")
public class OkuSefer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Enumerated(EnumType.STRING)
    @Column(name = "servis")
    private OkuServis servis;

    @Column(name = "yapildi_mi")
    private Boolean yapildiMi;

    @ManyToOne
    private OkuGuzergah guzergah;

    @ManyToOne
    private OkuSofor sofor;

    @ManyToOne
    private OkuArac arac;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public OkuSefer tarih(LocalDate tarih) {
        this.tarih = tarih;
        return this;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public OkuServis getServis() {
        return servis;
    }

    public OkuSefer servis(OkuServis servis) {
        this.servis = servis;
        return this;
    }

    public void setServis(OkuServis servis) {
        this.servis = servis;
    }

    public Boolean isYapildiMi() {
        return yapildiMi;
    }

    public OkuSefer yapildiMi(Boolean yapildiMi) {
        this.yapildiMi = yapildiMi;
        return this;
    }

    public void setYapildiMi(Boolean yapildiMi) {
        this.yapildiMi = yapildiMi;
    }

    public OkuGuzergah getGuzergah() {
        return guzergah;
    }

    public OkuSefer guzergah(OkuGuzergah okuGuzergah) {
        this.guzergah = okuGuzergah;
        return this;
    }

    public void setGuzergah(OkuGuzergah okuGuzergah) {
        this.guzergah = okuGuzergah;
    }

    public OkuSofor getSofor() {
        return sofor;
    }

    public OkuSefer sofor(OkuSofor okuSofor) {
        this.sofor = okuSofor;
        return this;
    }

    public void setSofor(OkuSofor okuSofor) {
        this.sofor = okuSofor;
    }

    public OkuArac getArac() {
        return arac;
    }

    public OkuSefer arac(OkuArac okuArac) {
        this.arac = okuArac;
        return this;
    }

    public void setArac(OkuArac okuArac) {
        this.arac = okuArac;
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
        OkuSefer okuSefer = (OkuSefer) o;
        if (okuSefer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuSefer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuSefer{" +
            "id=" + getId() +
            ", tarih='" + getTarih() + "'" +
            ", servis='" + getServis() + "'" +
            ", yapildiMi='" + isYapildiMi() + "'" +
            "}";
    }
}
