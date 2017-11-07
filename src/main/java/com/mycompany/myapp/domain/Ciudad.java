package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ciudad.
 */
@Entity
@Table(name = "ciudad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "gmaps")
    private String gmaps;

    @ManyToOne
    private Pais pais;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Ciudad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGmaps() {
        return gmaps;
    }

    public Ciudad gmaps(String gmaps) {
        this.gmaps = gmaps;
        return this;
    }

    public void setGmaps(String gmaps) {
        this.gmaps = gmaps;
    }

    public Pais getPais() {
        return pais;
    }

    public Ciudad pais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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
        Ciudad ciudad = (Ciudad) o;
        if (ciudad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciudad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ciudad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", gmaps='" + getGmaps() + "'" +
            "}";
    }
}
