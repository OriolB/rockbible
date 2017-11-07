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
 * A UserExt.
 */
@Entity
@Table(name = "user_ext")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExt implements Serializable {

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

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "userext")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoM> votarfavoritoms = new HashSet<>();

    @OneToMany(mappedBy = "userext")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoB> votarfavoritobs = new HashSet<>();

    @OneToMany(mappedBy = "userext")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VotarFavoritoA> votarfavoritoas = new HashSet<>();

    @OneToMany(mappedBy = "userext")
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

    public UserExt nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public UserExt foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public UserExt fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public UserExt fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public User getUser() {
        return user;
    }

    public UserExt user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<VotarFavoritoM> getVotarfavoritoms() {
        return votarfavoritoms;
    }

    public UserExt votarfavoritoms(Set<VotarFavoritoM> votarFavoritoMS) {
        this.votarfavoritoms = votarFavoritoMS;
        return this;
    }

    public UserExt addVotarfavoritom(VotarFavoritoM votarFavoritoM) {
        this.votarfavoritoms.add(votarFavoritoM);
        votarFavoritoM.setUserext(this);
        return this;
    }

    public UserExt removeVotarfavoritom(VotarFavoritoM votarFavoritoM) {
        this.votarfavoritoms.remove(votarFavoritoM);
        votarFavoritoM.setUserext(null);
        return this;
    }

    public void setVotarfavoritoms(Set<VotarFavoritoM> votarFavoritoMS) {
        this.votarfavoritoms = votarFavoritoMS;
    }

    public Set<VotarFavoritoB> getVotarfavoritobs() {
        return votarfavoritobs;
    }

    public UserExt votarfavoritobs(Set<VotarFavoritoB> votarFavoritoBS) {
        this.votarfavoritobs = votarFavoritoBS;
        return this;
    }

    public UserExt addVotarfavoritob(VotarFavoritoB votarFavoritoB) {
        this.votarfavoritobs.add(votarFavoritoB);
        votarFavoritoB.setUserext(this);
        return this;
    }

    public UserExt removeVotarfavoritob(VotarFavoritoB votarFavoritoB) {
        this.votarfavoritobs.remove(votarFavoritoB);
        votarFavoritoB.setUserext(null);
        return this;
    }

    public void setVotarfavoritobs(Set<VotarFavoritoB> votarFavoritoBS) {
        this.votarfavoritobs = votarFavoritoBS;
    }

    public Set<VotarFavoritoA> getVotarfavoritoas() {
        return votarfavoritoas;
    }

    public UserExt votarfavoritoas(Set<VotarFavoritoA> votarFavoritoAS) {
        this.votarfavoritoas = votarFavoritoAS;
        return this;
    }

    public UserExt addVotarfavoritoa(VotarFavoritoA votarFavoritoA) {
        this.votarfavoritoas.add(votarFavoritoA);
        votarFavoritoA.setUserext(this);
        return this;
    }

    public UserExt removeVotarfavoritoa(VotarFavoritoA votarFavoritoA) {
        this.votarfavoritoas.remove(votarFavoritoA);
        votarFavoritoA.setUserext(null);
        return this;
    }

    public void setVotarfavoritoas(Set<VotarFavoritoA> votarFavoritoAS) {
        this.votarfavoritoas = votarFavoritoAS;
    }

    public Set<VotarFavoritoC> getVotarfavoritocs() {
        return votarfavoritocs;
    }

    public UserExt votarfavoritocs(Set<VotarFavoritoC> votarFavoritoCS) {
        this.votarfavoritocs = votarFavoritoCS;
        return this;
    }

    public UserExt addVotarfavoritoc(VotarFavoritoC votarFavoritoC) {
        this.votarfavoritocs.add(votarFavoritoC);
        votarFavoritoC.setUserext(this);
        return this;
    }

    public UserExt removeVotarfavoritoc(VotarFavoritoC votarFavoritoC) {
        this.votarfavoritocs.remove(votarFavoritoC);
        votarFavoritoC.setUserext(null);
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
        UserExt userExt = (UserExt) o;
        if (userExt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExt{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            "}";
    }
}
