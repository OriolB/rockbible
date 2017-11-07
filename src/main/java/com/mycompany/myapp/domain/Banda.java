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
 * A Banda.
 */
@Entity
@Table(name = "banda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "years_activos")
    private LocalDate yearsActivos;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @ManyToOne
    private Pais pais;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "banda_genero",
               joinColumns = @JoinColumn(name="bandas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="generos_id", referencedColumnName="id"))
    private Set<Genero> generos = new HashSet<>();

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Musico> musicos = new HashSet<>();

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoB> votarfavoritobs = new HashSet<>();

    @ManyToMany(mappedBy = "bandas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Discografica> discograficas = new HashSet<>();

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

    public Banda nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Banda fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getYearsActivos() {
        return yearsActivos;
    }

    public Banda yearsActivos(LocalDate yearsActivos) {
        this.yearsActivos = yearsActivos;
        return this;
    }

    public void setYearsActivos(LocalDate yearsActivos) {
        this.yearsActivos = yearsActivos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Banda descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Banda estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Banda foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Banda fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Pais getPais() {
        return pais;
    }

    public Banda pais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public Banda generos(Set<Genero> generos) {
        this.generos = generos;
        return this;
    }

    public Banda addGenero(Genero genero) {
        this.generos.add(genero);
        genero.getBandas().add(this);
        return this;
    }

    public Banda removeGenero(Genero genero) {
        this.generos.remove(genero);
        genero.getBandas().remove(this);
        return this;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }

    public Set<Musico> getMusicos() {
        return musicos;
    }

    public Banda musicos(Set<Musico> musicos) {
        this.musicos = musicos;
        return this;
    }

    public Banda addMusico(Musico musico) {
        this.musicos.add(musico);
        musico.setBanda(this);
        return this;
    }

    public Banda removeMusico(Musico musico) {
        this.musicos.remove(musico);
        musico.setBanda(null);
        return this;
    }

    public void setMusicos(Set<Musico> musicos) {
        this.musicos = musicos;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Banda albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Banda addAlbum(Album album) {
        this.albums.add(album);
        album.setBanda(this);
        return this;
    }

    public Banda removeAlbum(Album album) {
        this.albums.remove(album);
        album.setBanda(null);
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<VotarFavoritoB> getVotarfavoritobs() {
        return votarfavoritobs;
    }

    public Banda votarfavoritobs(Set<VotarFavoritoB> votarFavoritoBS) {
        this.votarfavoritobs = votarFavoritoBS;
        return this;
    }

    public Banda addVotarfavoritob(VotarFavoritoB votarFavoritoB) {
        this.votarfavoritobs.add(votarFavoritoB);
        votarFavoritoB.setBanda(this);
        return this;
    }

    public Banda removeVotarfavoritob(VotarFavoritoB votarFavoritoB) {
        this.votarfavoritobs.remove(votarFavoritoB);
        votarFavoritoB.setBanda(null);
        return this;
    }

    public void setVotarfavoritobs(Set<VotarFavoritoB> votarFavoritoBS) {
        this.votarfavoritobs = votarFavoritoBS;
    }

    public Set<Discografica> getDiscograficas() {
        return discograficas;
    }

    public Banda discograficas(Set<Discografica> discograficas) {
        this.discograficas = discograficas;
        return this;
    }

    public Banda addDiscografica(Discografica discografica) {
        this.discograficas.add(discografica);
        discografica.getBandas().add(this);
        return this;
    }

    public Banda removeDiscografica(Discografica discografica) {
        this.discograficas.remove(discografica);
        discografica.getBandas().remove(this);
        return this;
    }

    public void setDiscograficas(Set<Discografica> discograficas) {
        this.discograficas = discograficas;
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
        Banda banda = (Banda) o;
        if (banda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), banda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Banda{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", yearsActivos='" + getYearsActivos() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + isEstado() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
