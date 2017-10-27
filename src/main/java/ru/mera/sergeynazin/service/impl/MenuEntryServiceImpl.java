package ru.mera.sergeynazin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.repository.IRepository;
import ru.mera.sergeynazin.service.MenuEntryService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Only for shaurmamaker
 */

public class MenuEntryServiceImpl implements MenuEntryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        logger.info("MenuEntryServiceImpl::setRepository() called with: repository = [" + repository + "]");
        this.repository = repository;
    }

    @Override
    public void save(final MenuEntry menuEntry) {
        logger.info("MenuEntryServiceImpl::save() called with: menuEntry = [" + menuEntry + "]");
        repository.create(menuEntry);
    }

    @Override
    public MenuEntry loadAsPersistent(final Long id) {
        logger.info("MenuEntryServiceImpl::loadAsPersistent() called with: id = [" + id + "]");
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MenuEntry> getAll() {
        logger.info("MenuEntryServiceImpl::getAll() called");
        CriteriaQuery<MenuEntry>  criteriaQuery = repository.myCriteriaQuery();
        Root<MenuEntry> root = criteriaQuery.from(MenuEntry.class);
        criteriaQuery.select(root);
        return repository.read(criteriaQuery);
    }

    @Override
    public void update(final MenuEntry detachedEntity) {
        logger.info("MenuEntryServiceImpl::update() called with: detachedEntity = [" + detachedEntity + "]");
        repository.update(detachedEntity);
    }

    @Override
    public void delete(final MenuEntry persistentMenuEntry) {
        logger.info("MenuEntryServiceImpl::delete() called with: persistentMenuEntry = [" + persistentMenuEntry + "]");
        repository.delete(persistentMenuEntry);
    }

    /**
     * @see MenuEntryServiceImpl#optionalIsExist(Long)
     */
    @Override
    public boolean tryDelete(final Long id) {
        logger.info("MenuEntryServiceImpl::tryDelete() called with: id = [" + id + "]");
        return repository.getOptional(id)
            .map(menuEntry -> {
                repository.delete(menuEntry);
                return true;
            }).orElse(false);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(MenuEntry_managed_instance)
     */
    @Override
    public Optional<MenuEntry> optionalIsExist(final Long id) {
        logger.info("MenuEntryServiceImpl::optionalIsExist() called with: id = [" + id + "]");
        return repository.getOptional(id);
            // Optional.of(repository.get(id));
    }
}
