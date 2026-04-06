package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank private String firebaseUid;
    @NotBlank private String nombre;
    @NotBlank private String apellidos;
    @NotBlank private String email;
    private String telefono;
    private String rol = "CLIENTE";

    public @NotBlank String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(@NotBlank String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public @NotBlank String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank String getApellidos() {
        return apellidos;
    }

    public void setApellidos(@NotBlank String apellidos) {
        this.apellidos = apellidos;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
