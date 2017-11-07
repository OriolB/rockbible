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
 * A Musico.
 */
@Entity
@Table(name = "musico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Musico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @ManyToOne
    private Banda banda;

    @ManyToOne
    private Pais pais;

    @OneToMany(mappedBy = "musico")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "musico")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoM> votarfavoritoms = new HashSet<>();

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

    public Musico nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Musico fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Musico descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Musico foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Musico fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Banda getBanda() {
        return banda;
    }

    public Musico banda(Banda banda) {
        this.banda = banda;
        return this;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

    public Pais getPais() {
        return pais;
    }

    public Musico pais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Musico albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Musico addAlbum(Album album) {
        this.albums.add(album);
        album.setMusico(this);
        return this;
    }

    public Musico removeAlbum(Album album) {
        this.albums.remove(album);
        album.setMusico(null);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<VotarFavoritoM> getVotarfavoritoms() {
        return votarfavoritoms;
    }

    public Musico votarfavoritoms(Set<VotarFavoritoM> votarFavoritoMS) {
        this.votarfavoritoms = votarFavoritoMS;
        return this;
    }

    public Musico addVotarfavoritom(VotarFavoritoM votarFavoritoM) {
        this.votarfavoritoms.add(votarFavoritoM);
        votarFavoritoM.setMusico(this);
        return this;
    }

    public Musico removeVotarfavoritom(VotarFavoritoM votarFavoritoM) {
        this.votarfavoritoms.remove(votarFavoritoM);
        votarFavoritoM.setMusico(null);
        return this;
    }

    public void setVotarfavoritoms(Set<VotarFavoritoM> votarFavoritoMS) {
        this.votarfavoritoms = votarFavoritoMS;
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
        Musico musico = (Musico) o;
        if (musico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), musico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Musico{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
