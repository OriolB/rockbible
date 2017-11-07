package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A VotarFavoritoC.
 */
@Entity
@Table(name = "votar_favorito_c")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VotarFavoritoC implements Serializable {

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
    private Cancion cancion;

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

    public VotarFavoritoC momento(ZonedDateTime momento) {
        this.momento = momento;
        return this;
    }

    public void setMomento(ZonedDateTime momento) {
        this.momento = momento;
    }

    public Integer getValoracio() {
        return valoracio;
    }

    public VotarFavoritoC valoracio(Integer valoracio) {
        this.valoracio = valoracio;
        return this;
    }

    public void setValoracio(Integer valoracio) {
        this.valoracio = valoracio;
    }

    public UserExt getUserext() {
        return userext;
    }

    public VotarFavoritoC userext(UserExt userExt) {
        this.userext = userExt;
        return this;
    }

    public void setUserext(UserExt userExt) {
        this.userext = userExt;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public VotarFavoritoC cancion(Cancion cancion) {
        this.cancion = cancion;
        return this;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
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
        VotarFavoritoC votarFavoritoC = (VotarFavoritoC) o;
        if (votarFavoritoC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), votarFavoritoC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VotarFavoritoC{" +
            "id=" + getId() +
            ", momento='" + getMomento() + "'" +
            ", valoracio='" + getValoracio() + "'" +
            "}";
    }
}
