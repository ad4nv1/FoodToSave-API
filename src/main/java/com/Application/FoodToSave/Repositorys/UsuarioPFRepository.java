package com.Application.FoodToSave.Repositorys;

import com.Application.FoodToSave.Models.UsuarioPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioPFRepository extends JpaRepository<UsuarioPF, Long> {
    public Optional<UsuarioPF> findByCpf(String cpf);
}
