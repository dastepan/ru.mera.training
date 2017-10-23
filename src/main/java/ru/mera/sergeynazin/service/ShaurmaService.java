package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repos.IRepository;

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
    /**
     * This one ONLY when you, say, already POSTed or smth alike, so that entity is supposedly persistent
     */
    Shaurma loadAsPersistent(Long id);
    void update(Shaurma detachedEntity);
    void delete(Shaurma persistentEntity);
    boolean tryDelete(Long id);
    Optional<Shaurma> optionalIsExist(Long id);
}
