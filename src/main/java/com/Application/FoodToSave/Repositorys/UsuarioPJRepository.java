package com.Application.FoodToSave.Repositorys;

import com.Application.FoodToSave.Models.UsuarioPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioPJRepository extends JpaRepository<UsuarioPJ, Long> {
    public Optional<UsuarioPJ> findByCnpj(String email);
}
