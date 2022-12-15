package com.Application.FoodToSave.Controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.Application.FoodToSave.Models.Produto;
import com.Application.FoodToSave.Repositorys.ProdutoRepository;

@RestController
@RequestMapping("/Produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/all")
    public ResponseEntity<List<Produto>> findAllProduct(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable long id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> Post(@Valid @RequestBody Produto product){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Produto> Put(@Valid @RequestBody Produto product){
        return repository.findById(product.getId()).map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(product)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        Optional<Produto> product = repository.findById(id);
        if(product.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        repository.deleteById(id);
    }
}
