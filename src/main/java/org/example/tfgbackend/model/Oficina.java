package org.example.tfgbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "oficina")
public class Oficina {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "activo_empresa", nullable = false)
    private boolean activoEmpresa = true;

    public Oficina() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public boolean isActivoEmpresa() { return activoEmpresa; }
    public void setActivoEmpresa(boolean a) { this.activoEmpresa = a; }
}