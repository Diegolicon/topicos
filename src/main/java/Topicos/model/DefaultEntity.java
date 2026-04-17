package Topicos.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class DefaultEntity extends PanacheEntity {
    @Column(name = "criado_em")
    private Long criadoEm;

    @Column(name = "atualizado_em")
    private Long atualizadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = System.currentTimeMillis();
        this.atualizadoEm = System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = System.currentTimeMillis();
    }

    public Long getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Long criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Long atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

