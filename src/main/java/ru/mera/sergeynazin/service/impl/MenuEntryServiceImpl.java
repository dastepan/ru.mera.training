package ru.mera.sergeynazin.service.impl;

import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.repos.IRepository;
import ru.mera.sergeynazin.service.MenuEntryService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Only for shaurmamaker
 */

public class MenuEntryServiceImpl implements MenuEntryService {

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(final MenuEntry menuEntry) {
        repository.createItem(menuEntry);
    }

    @Override
    public MenuEntry loadAsPersistent(final Long id) {
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MenuEntry> getAll() {
        CriteriaQuery<MenuEntry>  criteriaQuery = repository.myCriteriaQuery();
        Root<MenuEntry> root = criteriaQuery.from(MenuEntry.class);
        criteriaQuery.select(root);
        return repository.readItems(criteriaQuery);
    }

    @Override
    public void update(final MenuEntry detachedEntity) {
        repository.updateItem(detachedEntity);
    }

    @Override
    public void delete(final MenuEntry persistentMenuEntry) {
        repository.deleteItem(persistentMenuEntry);
    }

    @Override
    public boolean tryDelete(final Long id) {
        final MenuEntry menuEntry = repository.get(id);
        if (menuEntry != null) {
            repository.deleteItem(menuEntry);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<MenuEntry> optionalIsExist(final Long id) {
        return Optional.of(repository.get(id));
    }
}
