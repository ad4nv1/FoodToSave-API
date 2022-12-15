package com.Application.FoodToSave.Controllers;

import com.Application.FoodToSave.Models.DTOs.LogarDto;
import com.Application.FoodToSave.Models.UsuarioPF;
import com.Application.FoodToSave.Repositorys.UsuarioPFRepository;
import com.Application.FoodToSave.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/UsuarioPF")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioPFController {
    @Autowired
    private UsuarioPFRepository repository;

    @Autowired
    private UsuarioService service;

    @GetMapping("/all")
    public ResponseEntity findAllUser(){
        try {
            return ResponseEntity.ok(repository.findAll());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPF> getById(@PathVariable long id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/logar")
    public ResponseEntity Autentication(@Valid @RequestBody LogarDto user){
        try {
            if(repository.findByCpf(user.getCpfCnpj()).isPresent()) {
                Optional<UsuarioPF> userLogin = repository.findByCpf(user.getCpfCnpj());
                if(user.getPassword().equals(userLogin.get().getSenha()))
                    return ResponseEntity.status(HttpStatus.OK).body(userLogin);
                else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login ou senha incorretos");
            }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario não encontrado");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e);
        }

    }

    @PostMapping("/cadastrar")
    public ResponseEntity Post(@Valid @RequestBody UsuarioPF usuario){
        if(repository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
            }catch(Exception e) {
                return ResponseEntity.status(HttpStatus.OK).body(e);
            }

        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioPF> Put(@Valid @RequestBody UsuarioPF user){
        return service.updateUser(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        Optional<UsuarioPF> user = repository.findById(id);
        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        repository.deleteById(id);
    }
}
