package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "pais")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ciudad> ciudads = new HashSet<>();

    @OneToMany(mappedBy = "pais")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Musico> musicos = new HashSet<>();

    @OneToMany(mappedBy = "pais")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banda> bandas = new HashSet<>();

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

    public Pais nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Ciudad> getCiudads() {
        return ciudads;
    }

    public Pais ciudads(Set<Ciudad> ciudads) {
        this.ciudads = ciudads;
        return this;
    }

    public Pais addCiudad(Ciudad ciudad) {
        this.ciudads.add(ciudad);
        ciudad.setPais(this);
        return this;
    }

    public Pais removeCiudad(Ciudad ciudad) {
        this.ciudads.remove(ciudad);
        ciudad.setPais(null);
        return this;
    }

    public void setCiudads(Set<Ciudad> ciudads) {
        this.ciudads = ciudads;
    }

    public Set<Musico> getMusicos() {
        return musicos;
    }

    public Pais musicos(Set<Musico> musicos) {
        this.musicos = musicos;
        return this;
    }

    public Pais addMusico(Musico musico) {
        this.musicos.add(musico);
        musico.setPais(this);
        return this;
    }

    public Pais removeMusico(Musico musico) {
        this.musicos.remove(musico);
        musico.setPais(null);
        return this;
    }

    public void setMusicos(Set<Musico> musicos) {
        this.musicos = musicos;
    }

    public Set<Banda> getBandas() {
        return bandas;
    }

    public Pais bandas(Set<Banda> bandas) {
        this.bandas = bandas;
        return this;
    }

    public Pais addBanda(Banda banda) {
        this.bandas.add(banda);
        banda.setPais(this);
        return this;
    }

    public Pais removeBanda(Banda banda) {
        this.bandas.remove(banda);
        banda.setPais(null);
        return this;
    }

    public void setBandas(Set<Banda> bandas) {
        this.bandas = bandas;
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
        Pais pais = (Pais) o;
        if (pais.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pais.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pais{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
