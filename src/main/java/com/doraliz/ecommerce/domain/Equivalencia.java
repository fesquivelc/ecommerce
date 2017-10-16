package com.doraliz.ecommerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Equivalencia.
 */
@Entity
@Table(name = "equivalencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equivalencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valor", precision=10, scale=2, nullable = false)
    private BigDecimal valor;

    @ManyToOne
    private UnidadMedida desde;

    @ManyToOne
    private UnidadMedida hasta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Equivalencia valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public UnidadMedida getDesde() {
        return desde;
    }

    public Equivalencia desde(UnidadMedida unidadMedida) {
        this.desde = unidadMedida;
        return this;
    }

    public void setDesde(UnidadMedida unidadMedida) {
        this.desde = unidadMedida;
    }

    public UnidadMedida getHasta() {
        return hasta;
    }

    public Equivalencia hasta(UnidadMedida unidadMedida) {
        this.hasta = unidadMedida;
        return this;
    }

    public void setHasta(UnidadMedida unidadMedida) {
        this.hasta = unidadMedida;
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
        Equivalencia equivalencia = (Equivalencia) o;
        if (equivalencia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equivalencia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Equivalencia{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
