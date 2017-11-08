package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuArac.
 */
@Entity
@Table(name = "oku_arac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okuarac")
public class OkuArac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "plaka")
    private String plaka;

    @Column(name = "marka")
    private String marka;

    @Column(name = "renk")
    private String renk;

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

    public OkuArac kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getPlaka() {
        return plaka;
    }

    public OkuArac plaka(String plaka) {
        this.plaka = plaka;
        return this;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getMarka() {
        return marka;
    }

    public OkuArac marka(String marka) {
        this.marka = marka;
        return this;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getRenk() {
        return renk;
    }

    public OkuArac renk(String renk) {
        this.renk = renk;
        return this;
    }

    public void setRenk(String renk) {
        this.renk = renk;
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
        OkuArac okuArac = (OkuArac) o;
        if (okuArac.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuArac.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuArac{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", plaka='" + getPlaka() + "'" +
            ", marka='" + getMarka() + "'" +
            ", renk='" + getRenk() + "'" +
            "}";
    }
}
