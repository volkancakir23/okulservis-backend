package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuOkul.
 */
@Entity
@Table(name = "oku_okul")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okuokul")
public class OkuOkul implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "isim")
    private String isim;

    @Column(name = "mudur_isim")
    private String mudurIsim;

    @Column(name = "mudur_tel")
    private String mudurTel;

    @ManyToOne
    private OkuSehir sehir;

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

    public OkuOkul kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getIsim() {
        return isim;
    }

    public OkuOkul isim(String isim) {
        this.isim = isim;
        return this;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getMudurIsim() {
        return mudurIsim;
    }

    public OkuOkul mudurIsim(String mudurIsim) {
        this.mudurIsim = mudurIsim;
        return this;
    }

    public void setMudurIsim(String mudurIsim) {
        this.mudurIsim = mudurIsim;
    }

    public String getMudurTel() {
        return mudurTel;
    }

    public OkuOkul mudurTel(String mudurTel) {
        this.mudurTel = mudurTel;
        return this;
    }

    public void setMudurTel(String mudurTel) {
        this.mudurTel = mudurTel;
    }

    public OkuSehir getSehir() {
        return sehir;
    }

    public OkuOkul sehir(OkuSehir okuSehir) {
        this.sehir = okuSehir;
        return this;
    }

    public void setSehir(OkuSehir okuSehir) {
        this.sehir = okuSehir;
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
        OkuOkul okuOkul = (OkuOkul) o;
        if (okuOkul.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuOkul.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuOkul{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", isim='" + getIsim() + "'" +
            ", mudurIsim='" + getMudurIsim() + "'" +
            ", mudurTel='" + getMudurTel() + "'" +
            "}";
    }
}
