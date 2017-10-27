package ru.mera.sergeynazin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.IRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class ShaurmaServiceImpl implements ShaurmaService {

    /**
     * Test
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(final Shaurma shaurma) {
        repository.create(shaurma);
    }

    @Override
    public Shaurma loadAsPersistent(final Serializable id) {
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Shaurma> getAll() {
        CriteriaQuery<Shaurma>  criteriaQuery = repository.myCriteriaQuery();
        Root<Shaurma> root = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(root);
        return repository.read(criteriaQuery);
    }


    @Override
    public void update(final Shaurma detachedEntity) {
        repository.update(detachedEntity);
    }

    @Override
    public void delete(final Shaurma persistentShaurma) {
        repository.delete(persistentShaurma);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Shaurma_managed_instance)
     */
    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        return repository.getOptional(id);
            //Optional.of(repository.getOptional(id));
    }
}
