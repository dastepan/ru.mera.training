package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.MenuEntry;
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

public interface MenuEntryService {
    void save(MenuEntry transient_);
    List<MenuEntry> getAll();
    void delete(MenuEntry detached);
    Optional<MenuEntry> optionalIsExist(Long id);
    Optional<MenuEntry> getOptionalIsExist(Long shaurmaId);
}
