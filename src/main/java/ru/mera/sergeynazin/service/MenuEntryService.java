package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.MenuEntry;
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


public interface MenuEntryService {
    void save(MenuEntry menuEntry);
    List<MenuEntry> getAll();
    /**
     * This one ONLY when you, say, already POSTed or smth alike, so that entity is supposedly persistent
     */
    MenuEntry loadAsPersistent(Long id);
    void update(MenuEntry detachedEntity);
    void delete(MenuEntry persistentEntity);
    boolean tryDelete(Long id);
    Optional<MenuEntry> optionalIsExist(Long id);
}
