package com.Application.FoodToSave.Controllers;


import com.Application.FoodToSave.Models.DTOs.LogarDto;
import com.Application.FoodToSave.Models.UsuarioPJ;
import com.Application.FoodToSave.Repositorys.UsuarioPJRepository;
import javax.validation.Valid;

import com.Application.FoodToSave.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/UsuarioPJ")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioPJController {

    @Autowired
    private UsuarioPJRepository repository;

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
    public ResponseEntity<UsuarioPJ> getById(@PathVariable long id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/logar")
    public ResponseEntity Autentication(@Valid @RequestBody LogarDto user){
        try {
            if(repository.findByCnpj(user.getCpfCnpj()).isPresent()) {
                Optional<UsuarioPJ> userLogin = repository.findByCnpj(user.getCpfCnpj());
                if(user.getPassword().equals(userLogin.get().getSenha()))
                    return ResponseEntity.status(HttpStatus.OK).body(userLogin);
                else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login ou senha incorretos");
            }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario não encontrado");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e);
        }

    }

    @PostMapping("/cadastrar")
    public ResponseEntity Post(@Valid @RequestBody UsuarioPJ usuario){
        if(repository.findByCnpj(usuario.getCnpj()).isPresent()) {
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
    public ResponseEntity<UsuarioPJ> Put(@Valid @RequestBody UsuarioPJ user){
        return service.updateUser(user)
                .map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        Optional<UsuarioPJ> user = repository.findById(id);
        if(user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        repository.deleteById(id);
    }
}
