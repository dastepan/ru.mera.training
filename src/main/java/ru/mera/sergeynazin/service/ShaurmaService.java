package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.IRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see IRepository
 *
 * @author sergeynazin
 * */

public interface ShaurmaService {
    void save(Shaurma shaurma);
    List<Shaurma> getAll();
    Shaurma loadAsPersistent(Serializable id);
    void update(Shaurma detachedEntity);
    void delete(Shaurma persistentEntity);
    Optional<Shaurma> optionalIsExist(Long id);
}
