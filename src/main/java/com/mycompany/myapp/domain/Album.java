package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @ManyToOne
    private Banda banda;

    @ManyToOne
    private Musico musico;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "album_genero",
               joinColumns = @JoinColumn(name="albums_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="generos_id", referencedColumnName="id"))
    private Set<Genero> generos = new HashSet<>();

    @OneToMany(mappedBy = "album")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoA> votarfavoritoas = new HashSet<>();

    @ManyToMany(mappedBy = "albums")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cancion> cancions = new HashSet<>();

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

    public Album nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Album fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Album foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Album fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Banda getBanda() {
        return banda;
    }

    public Album banda(Banda banda) {
        this.banda = banda;
        return this;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

    public Musico getMusico() {
        return musico;
    }

    public Album musico(Musico musico) {
        this.musico = musico;
        return this;
    }

    public void setMusico(Musico musico) {
        this.musico = musico;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public Album generos(Set<Genero> generos) {
        this.generos = generos;
        return this;
    }

    public Album addGenero(Genero genero) {
        this.generos.add(genero);
        genero.getAlbums().add(this);
        return this;
    }

    public Album removeGenero(Genero genero) {
        this.generos.remove(genero);
        genero.getAlbums().remove(this);
        return this;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }

    public Set<VotarFavoritoA> getVotarfavoritoas() {
        return votarfavoritoas;
    }

    public Album votarfavoritoas(Set<VotarFavoritoA> votarFavoritoAS) {
        this.votarfavoritoas = votarFavoritoAS;
        return this;
    }

    public Album addVotarfavoritoa(VotarFavoritoA votarFavoritoA) {
        this.votarfavoritoas.add(votarFavoritoA);
        votarFavoritoA.setAlbum(this);
        return this;
    }

    public Album removeVotarfavoritoa(VotarFavoritoA votarFavoritoA) {
        this.votarfavoritoas.remove(votarFavoritoA);
        votarFavoritoA.setAlbum(null);
        return this;
    }

    public void setVotarfavoritoas(Set<VotarFavoritoA> votarFavoritoAS) {
        this.votarfavoritoas = votarFavoritoAS;
    }

    public Set<Cancion> getCancions() {
        return cancions;
    }

    public Album cancions(Set<Cancion> cancions) {
        this.cancions = cancions;
        return this;
    }

    public Album addCancion(Cancion cancion) {
        this.cancions.add(cancion);
        cancion.getAlbums().add(this);
        return this;
    }

    public Album removeCancion(Cancion cancion) {
        this.cancions.remove(cancion);
        cancion.getAlbums().remove(this);
        return this;
    }

    public void setCancions(Set<Cancion> cancions) {
        this.cancions = cancions;
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
        Album album = (Album) o;
        if (album.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), album.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
