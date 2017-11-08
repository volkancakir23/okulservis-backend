package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuOgrenci.
 */
@Entity
@Table(name = "oku_ogrenci")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okuogrenci")
public class OkuOgrenci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "no")
    private String no;

    @Column(name = "isim")
    private String isim;

    @Column(name = "tc")
    private String tc;

    @Column(name = "aile_isim")
    private String aileIsim;

    @Column(name = "aile_tel")
    private String aileTel;

    @ManyToOne
    private OkuOkul okul;

    @ManyToOne
    private OkuGuzergah guzergah;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public OkuOgrenci no(String no) {
        this.no = no;
        return this;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getIsim() {
        return isim;
    }

    public OkuOgrenci isim(String isim) {
        this.isim = isim;
        return this;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTc() {
        return tc;
    }

    public OkuOgrenci tc(String tc) {
        this.tc = tc;
        return this;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getAileIsim() {
        return aileIsim;
    }

    public OkuOgrenci aileIsim(String aileIsim) {
        this.aileIsim = aileIsim;
        return this;
    }

    public void setAileIsim(String aileIsim) {
        this.aileIsim = aileIsim;
    }

    public String getAileTel() {
        return aileTel;
    }

    public OkuOgrenci aileTel(String aileTel) {
        this.aileTel = aileTel;
        return this;
    }

    public void setAileTel(String aileTel) {
        this.aileTel = aileTel;
    }

    public OkuOkul getOkul() {
        return okul;
    }

    public OkuOgrenci okul(OkuOkul okuOkul) {
        this.okul = okuOkul;
        return this;
    }

    public void setOkul(OkuOkul okuOkul) {
        this.okul = okuOkul;
    }

    public OkuGuzergah getGuzergah() {
        return guzergah;
    }

    public OkuOgrenci guzergah(OkuGuzergah okuGuzergah) {
        this.guzergah = okuGuzergah;
        return this;
    }

    public void setGuzergah(OkuGuzergah okuGuzergah) {
        this.guzergah = okuGuzergah;
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
        OkuOgrenci okuOgrenci = (OkuOgrenci) o;
        if (okuOgrenci.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuOgrenci.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuOgrenci{" +
            "id=" + getId() +
            ", no='" + getNo() + "'" +
            ", isim='" + getIsim() + "'" +
            ", tc='" + getTc() + "'" +
            ", aileIsim='" + getAileIsim() + "'" +
            ", aileTel='" + getAileTel() + "'" +
            "}";
    }
}
