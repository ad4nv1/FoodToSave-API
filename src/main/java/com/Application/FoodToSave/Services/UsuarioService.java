package com.Application.FoodToSave.Services;

import com.Application.FoodToSave.Models.UsuarioPF;
import com.Application.FoodToSave.Models.UsuarioPJ;
import com.Application.FoodToSave.Repositorys.UsuarioPFRepository;
import com.Application.FoodToSave.Repositorys.UsuarioPJRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioPFRepository repositoryPF;

    @Autowired
    private UsuarioPJRepository repositoryPJ;

    public Optional<UsuarioPF> updateUser(UsuarioPF user) {
        if (repositoryPF.findById(user.getId()).isPresent()) {
            Optional<UsuarioPF> buscaUsuario = repositoryPF.findByCpf(user.getCpf());
            if (buscaUsuario.isPresent()) {
                if (buscaUsuario.get().getId() != user.getId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
            }
            return Optional.of(repositoryPF.save(user));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
    }

    public Optional<UsuarioPJ> updateUser(UsuarioPJ user) {
        if (repositoryPJ.findById(user.getId()).isPresent()) {
            Optional<UsuarioPJ> buscaUsuario = repositoryPJ.findByCnpj(user.getCnpj());
            if (buscaUsuario.isPresent()) {
                if (buscaUsuario.get().getId() != user.getId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
            }
            return Optional.of(repositoryPJ.save(user));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);
    }
}
