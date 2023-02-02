package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.dto.Dto;

import java.util.Collection;
import java.util.Optional;

public interface CrudService<T, ID> {
    T save(Dto<T> dto);
    T update(ID id, Dto<T> dto);
    void delete(ID id);
    Optional<T> find(ID id);
    Collection<T> findAll();
}
