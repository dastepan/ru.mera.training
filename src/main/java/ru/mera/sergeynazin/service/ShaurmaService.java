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

    Serializable save(Shaurma shaurma);
    List<Shaurma> getAll();
    /**
     * This one ONLY when you, say, already POSTed or smth alike, so that entity is supposedly persistent
     */
    Shaurma loadAsPersistent(Serializable id);
    void update(Shaurma detachedEntity);

    /**
     *
     * @param detachedNewStatefulEntityWithoutId detached New Stateful Entity WithoutId
     * @return persistent Shaurma with updated state
     */
    Shaurma updateShaurmaStateInDb(Long id, Shaurma detachedNewStatefulEntityWithoutId);

    void delete(Shaurma persistentEntity);
    boolean tryDelete(Long id);
    Optional<Shaurma> optionalIsExist(Long id);

}
