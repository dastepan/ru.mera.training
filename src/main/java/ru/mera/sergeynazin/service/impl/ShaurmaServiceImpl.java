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
        logger.info("ShaurmaServiceImpl::setRepository() called with: repository = [" + repository + "]");
        this.repository = repository;
    }

    /**
     * For educational purposes this is done not directly, like repository.getSession().save(shaurma)
      * @param shaurma transient newly created entity
     * @return newly assigned primary key of entity after making it managed by calling getId() straight after persist()
     */
    @Override
    public Serializable save(final Shaurma shaurma) {
        logger.info("ShaurmaServiceImpl::save() called with: shaurma = [" + shaurma + "]");
        repository.persist(shaurma);
        return shaurma.getId();
    }

    @Override
    public Shaurma loadAsPersistent(final Serializable id) {
        logger.info("loadAsPersistent() called with: id = [" + id + "]");
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Shaurma> getAll() {
        logger.info("ShaurmaServiceImpl::getAll() called");
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
    public Shaurma updateShaurmaStateInDb(final Long idGuarantiedIsInDb,
                                          final Shaurma detachedNewStatefulEntityWithoutId) {
        detachedNewStatefulEntityWithoutId.setId(idGuarantiedIsInDb);
        return repository.mergeStateWithDbEntity(detachedNewStatefulEntityWithoutId);
    }

    @Override
    public void delete(final Shaurma persistentShaurma) {
        logger.info("ShaurmaServiceImpl::delete() called with: persistentShaurma = [" + persistentShaurma + "]");
        repository.delete(persistentShaurma);
    }

    /**
     * @see ShaurmaServiceImpl#optionalIsExist(Long)
     */
    @Override
    public boolean tryDelete(final Long id) {
        logger.info("ShaurmaServiceImpl::tryDelete() called with: id = [" + id + "]");
        return repository.getOptional(id)
            .map(shaurma -> {
                repository.delete(shaurma);
                return true;
            }).orElse(false);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Shaurma_managed_instance)
     */
    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        logger.info("ShaurmaServiceImpl::optionalIsExist() called with: id = [" + id + "]");
        return repository.getOptional(id);
            //Optional.of(repository.getOptional(id));
    }
}
