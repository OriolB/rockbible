package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Discografica.
 */
@Entity
@Table(name = "discografica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Discografica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "discografica_banda",
               joinColumns = @JoinColumn(name="discograficas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="bandas_id", referencedColumnName="id"))
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

    public Discografica nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Discografica foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Discografica fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Set<Banda> getBandas() {
        return bandas;
    }

    public Discografica bandas(Set<Banda> bandas) {
        this.bandas = bandas;
        return this;
    }

    public Discografica addBanda(Banda banda) {
        this.bandas.add(banda);
        banda.getDiscograficas().add(this);
        return this;
    }

    public Discografica removeBanda(Banda banda) {
        this.bandas.remove(banda);
        banda.getDiscograficas().remove(this);
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
        Discografica discografica = (Discografica) o;
        if (discografica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discografica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Discografica{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
