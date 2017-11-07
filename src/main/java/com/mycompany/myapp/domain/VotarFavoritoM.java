package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A VotarFavoritoM.
 */
@Entity
@Table(name = "votar_favorito_m")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VotarFavoritoM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "momento")
    private ZonedDateTime momento;

    @Column(name = "valoracio")
    private Integer valoracio;

    @ManyToOne
    private UserExt userext;

    @ManyToOne
    private Musico musico;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getMomento() {
        return momento;
    }

    public VotarFavoritoM momento(ZonedDateTime momento) {
        this.momento = momento;
        return this;
    }

    public void setMomento(ZonedDateTime momento) {
        this.momento = momento;
    }

    public Integer getValoracio() {
        return valoracio;
    }

    public VotarFavoritoM valoracio(Integer valoracio) {
        this.valoracio = valoracio;
        return this;
    }

    public void setValoracio(Integer valoracio) {
        this.valoracio = valoracio;
    }

    public UserExt getUserext() {
        return userext;
    }

    public VotarFavoritoM userext(UserExt userExt) {
        this.userext = userExt;
        return this;
    }

    public void setUserext(UserExt userExt) {
        this.userext = userExt;
    }

    public Musico getMusico() {
        return musico;
    }

    public VotarFavoritoM musico(Musico musico) {
        this.musico = musico;
        return this;
    }

    public void setMusico(Musico musico) {
        this.musico = musico;
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
        VotarFavoritoM votarFavoritoM = (VotarFavoritoM) o;
        if (votarFavoritoM.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), votarFavoritoM.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VotarFavoritoM{" +
            "id=" + getId() +
            ", momento='" + getMomento() + "'" +
            ", valoracio='" + getValoracio() + "'" +
            "}";
    }
}
