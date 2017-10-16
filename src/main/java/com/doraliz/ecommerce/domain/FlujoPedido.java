package com.doraliz.ecommerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FlujoPedido.
 */
@Entity
@Table(name = "flujo_pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FlujoPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @ManyToOne
    private Cargo cargo;

    @ManyToOne
    private FlujoPedido anterior;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActivo() {
        return activo;
    }

    public FlujoPedido activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public FlujoPedido cargo(Cargo cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public FlujoPedido getAnterior() {
        return anterior;
    }

    public FlujoPedido anterior(FlujoPedido flujoPedido) {
        this.anterior = flujoPedido;
        return this;
    }

    public void setAnterior(FlujoPedido flujoPedido) {
        this.anterior = flujoPedido;
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
        FlujoPedido flujoPedido = (FlujoPedido) o;
        if (flujoPedido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), flujoPedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FlujoPedido{" +
            "id=" + getId() +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
