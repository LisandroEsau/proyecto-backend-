package com.novel.pasteleria_emanuel.controller;

import com.novel.pasteleria_emanuel.models.entities.Usuario;
import com.novel.pasteleria_emanuel.models.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository usuarioRepository;

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    // Agregar un usuario
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }

    // Actualizar un usuario
//    @PutMapping("/{id}")
//    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
//        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
//
//        if (usuarioOptional.isPresent()) {
//            Usuario usuarioExistente = usuarioOptional.get();
//            usuarioExistente.setNombre(usuarioActualizado.getNombre());
//            usuarioExistente.setEmail(usuarioActualizado.getEmail());
//            usuarioExistente.setRol(usuarioActualizado.getRol());
//
//            usuarioRepository.save(usuarioExistente);
//            return ResponseEntity.ok(usuarioExistente);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // Eliminar un usuario
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        if (usuarioRepository.existsById(id)) {
//            usuarioRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
