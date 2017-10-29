package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see JpaRepository
 *
 * @author sergeynazin
 * */

public interface ShaurmaService {
    void save(Shaurma transientEntity);
    List<Shaurma> getAll();
    Shaurma loadAsPersistent(Long id);
    void update(Shaurma detachedEntity);
    void delete(Shaurma persistentOrDetachedEntity);
    Optional<Shaurma> optionalIsExist(Long id);
}
