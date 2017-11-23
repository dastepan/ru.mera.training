package ru.mera.sergeynazin.service.impl;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.MenuEntryService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Only for shaurmamaker
 */

@Transactional(readOnly = true)
public class MenuEntryServiceImpl implements MenuEntryService {

    private JpaRepository menuEntryRepository, shaurmaRepository;

    public void setMenuEntryRepository(final JpaRepository menuEntryRepository) {
        this.menuEntryRepository = menuEntryRepository;
    }

    public void setShaurmaRepository(final JpaRepository shaurmaRepository) {
        this.shaurmaRepository = shaurmaRepository;
    }

    @Transactional
    @Override
    public void save(final MenuEntry transient_) {
        menuEntryRepository.create(transient_);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<MenuEntry> getAll() {
        final CriteriaQuery<MenuEntry>  criteriaQuery = menuEntryRepository.myCriteriaQuery();
        final Root<MenuEntry> from = criteriaQuery.from(MenuEntry.class);
        criteriaQuery.select(from);
        return menuEntryRepository.getByCriteriaQuery(criteriaQuery);
    }

    @Transactional
    @Override
    public void delete(final MenuEntry detached) {
        menuEntryRepository.remove(detached);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(MenuEntry_managed_instance)
     */
    @Override
    public Optional<MenuEntry> optionalIsExist(final Long id) {
        return menuEntryRepository.getOptionalById(id);
            // Optional.ofNullable(repository.get(id));
    }

    @Override
    public Optional<MenuEntry> getOptionalIsExist(final Long shaurmaId) {
        final CriteriaBuilder criteriaBuilder = menuEntryRepository.myCriteriaBuilder();
        final CriteriaQuery<MenuEntry> criteriaQuery = criteriaBuilder.createQuery(MenuEntry.class);
        final Root<MenuEntry> ingredientRoot = criteriaQuery.from(MenuEntry.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("shaurma_id"), shaurmaId));

        final MenuEntry nullable = menuEntryRepository.getUniqueByCriteriaQuery(criteriaQuery);

        //TODO Temporary for testing
        if (nullable != null) {
            Hibernate.initialize(nullable.getShaurma());
        }

        return Optional.ofNullable(nullable);
    }
}
