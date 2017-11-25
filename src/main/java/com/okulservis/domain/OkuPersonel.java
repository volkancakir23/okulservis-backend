package com.okulservis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OkuPersonel.
 */
@Entity
@Table(name = "oku_personel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "okupersonel")
public class OkuPersonel implements Serializable {

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
    private OkuOkul okuOkul;

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

    public OkuPersonel kod(String kod) {
        this.kod = kod;
        return this;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getIsim() {
        return isim;
    }

    public OkuPersonel isim(String isim) {
        this.isim = isim;
        return this;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTel() {
        return tel;
    }

    public OkuPersonel tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public OkuOkul getOkuOkul() {
        return okuOkul;
    }

    public OkuPersonel okuOkul(OkuOkul okuOkul) {
        this.okuOkul = okuOkul;
        return this;
    }

    public void setOkuOkul(OkuOkul okuOkul) {
        this.okuOkul = okuOkul;
    }

    public User getUser() {
        return user;
    }

    public OkuPersonel user(User user) {
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
        OkuPersonel okuPersonel = (OkuPersonel) o;
        if (okuPersonel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), okuPersonel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OkuPersonel{" +
            "id=" + getId() +
            ", kod='" + getKod() + "'" +
            ", isim='" + getIsim() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
