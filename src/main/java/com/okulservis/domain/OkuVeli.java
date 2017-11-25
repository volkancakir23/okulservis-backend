package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuVeli.
 */
@Entity
@Table(name = "oku_veli")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okuveli")
public class OkuVeli implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "isim")
    private String isim;

    @Column(name = "tel")
    private String tel;

    @ManyToOne
    private OkuOgrenci okuOgrenci;

    @ManyToOne
    private User user;

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

    public OkuVeli kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getIsim() {
        return isim;
    }

    public OkuVeli isim(String isim) {
        this.isim = isim;
        return this;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTel() {
        return tel;
    }

    public OkuVeli tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public OkuOgrenci getOkuOgrenci() {
        return okuOgrenci;
    }

    public OkuVeli okuOgrenci(OkuOgrenci okuOgrenci) {
        this.okuOgrenci = okuOgrenci;
        return this;
    }

    public void setOkuOgrenci(OkuOgrenci okuOgrenci) {
        this.okuOgrenci = okuOgrenci;
    }

    public User getUser() {
        return user;
    }

    public OkuVeli user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        OkuVeli okuVeli = (OkuVeli) o;
        if (okuVeli.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuVeli.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuVeli{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", isim='" + getIsim() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
