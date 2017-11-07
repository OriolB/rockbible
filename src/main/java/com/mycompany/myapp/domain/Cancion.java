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
 * A Cancion.
 */
@Entity
@Table(name = "cancion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cancion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "duracion")
    private Double duracion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cancion_album",
               joinColumns = @JoinColumn(name="cancions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="albums_id", referencedColumnName="id"))
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "cancion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoC> votarfavoritocs = new HashSet<>();

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

    public Cancion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDuracion() {
        return duracion;
    }

    public Cancion duracion(Double duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Cancion albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Cancion addAlbum(Album album) {
        this.albums.add(album);
        album.getCancions().add(this);
        return this;
    }

    public Cancion removeAlbum(Album album) {
        this.albums.remove(album);
        album.getCancions().remove(this);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<VotarFavoritoC> getVotarfavoritocs() {
        return votarfavoritocs;
    }

    public Cancion votarfavoritocs(Set<VotarFavoritoC> votarFavoritoCS) {
        this.votarfavoritocs = votarFavoritoCS;
        return this;
    }

    public Cancion addVotarfavoritoc(VotarFavoritoC votarFavoritoC) {
        this.votarfavoritocs.add(votarFavoritoC);
        votarFavoritoC.setCancion(this);
        return this;
    }

    public Cancion removeVotarfavoritoc(VotarFavoritoC votarFavoritoC) {
        this.votarfavoritocs.remove(votarFavoritoC);
        votarFavoritoC.setCancion(null);
        return this;
    }

    public void setVotarfavoritocs(Set<VotarFavoritoC> votarFavoritoCS) {
        this.votarfavoritocs = votarFavoritoCS;
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
        Cancion cancion = (Cancion) o;
        if (cancion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cancion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cancion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", duracion='" + getDuracion() + "'" +
            "}";
    }
}
