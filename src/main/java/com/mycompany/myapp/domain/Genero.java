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
 * A Genero.
 */
@Entity
@Table(name = "genero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "generos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banda> bandas = new HashSet<>();

    @ManyToMany(mappedBy = "generos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Album> albums = new HashSet<>();

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

    public Genero nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Banda> getBandas() {
        return bandas;
    }

    public Genero bandas(Set<Banda> bandas) {
        this.bandas = bandas;
        return this;
    }

    public Genero addBanda(Banda banda) {
        this.bandas.add(banda);
        banda.getGeneros().add(this);
        return this;
    }

    public Genero removeBanda(Banda banda) {
        this.bandas.remove(banda);
        banda.getGeneros().remove(this);
        return this;
    }

    public void setBandas(Set<Banda> bandas) {
        this.bandas = bandas;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Genero albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Genero addAlbum(Album album) {
        this.albums.add(album);
        album.getGeneros().add(this);
        return this;
    }

    public Genero removeAlbum(Album album) {
        this.albums.remove(album);
        album.getGeneros().remove(this);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
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
        Genero genero = (Genero) o;
        if (genero.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genero.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Genero{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
