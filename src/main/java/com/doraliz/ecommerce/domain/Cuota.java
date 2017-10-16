package com.doraliz.ecommerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.doraliz.ecommerce.domain.enumeration.CuotaEstado;

/**
 * A Cuota.
 */
@Entity
@Table(name = "cuota")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cuota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @NotNull
    @Column(name = "fecha_comunicado", nullable = false)
    private LocalDate fechaComunicado;

    @NotNull
    @Column(name = "monto", precision=10, scale=2, nullable = false)
    private BigDecimal monto;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private CuotaEstado estado;

    @ManyToOne
    private Pedido pedido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Cuota fechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
        return this;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaComunicado() {
        return fechaComunicado;
    }

    public Cuota fechaComunicado(LocalDate fechaComunicado) {
        this.fechaComunicado = fechaComunicado;
        return this;
    }

    public void setFechaComunicado(LocalDate fechaComunicado) {
        this.fechaComunicado = fechaComunicado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public Cuota monto(BigDecimal monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public CuotaEstado getEstado() {
        return estado;
    }

    public Cuota estado(CuotaEstado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(CuotaEstado estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Cuota pedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
        Cuota cuota = (Cuota) o;
        if (cuota.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cuota.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cuota{" +
            "id=" + getId() +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", fechaComunicado='" + getFechaComunicado() + "'" +
            ", monto='" + getMonto() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
