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
    // GET

    List<Shaurma> getAll();

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(Shaurma_managed_instance)
     */
    Optional<Shaurma> getOptionalIsExist(Long id);

    Shaurma getOrThrow(Long id);

    // POST

    /**
     * ONLY valid body shaurma (with valid nested fields)
     * should be saved
     * @param transient_ entity
     * @return id after performed INSERT statement
     */
    Shaurma postOrThrow(Shaurma transient_);

    // PUT
    Shaurma putOrThrow(Shaurma newDetached);

    // DELETE
    Shaurma deleteByIdOrThrow(Long id);

    // helpers
    boolean validateExistsOrThrow(Shaurma... shaurmas);
}
