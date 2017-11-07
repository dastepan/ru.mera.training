package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class ShaurmaServiceImpl implements ShaurmaService {

    private JpaRepository repository;

    public void setRepository(final JpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void save(final Shaurma transientEntity) {
        repository.create(transientEntity);
    }

    @Override
    public Shaurma loadAsPersistent(final Long id) {
        return repository.getById(id);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Shaurma> getAll() {
        final CriteriaQuery<Shaurma>  criteriaQuery = repository.myCriteriaQuery();
        final Root<Shaurma> root = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(root);
        return repository.getByCriteriaQuery(criteriaQuery);
    }

    @Transactional
    @Override
    public void update(final Shaurma detachedEntity) {
        repository.update(detachedEntity);
    }

    @Override
    public Shaurma merge(Shaurma detachedEntity) {
        return repository.mergeStateWithDbEntity(detachedEntity);
    }

    @Transactional
    @Override
    public void delete(final Shaurma persistentOrDetachedEntity) {
        repository.delete(persistentOrDetachedEntity);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Shaurma_managed_instance)
     */
    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        return repository.getOptionalById(id);
            //Optional.of(repository.getOptionalById(id));
    }
}
