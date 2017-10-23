package ru.mera.sergeynazin.service.impl;

import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repos.IRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class ShaurmaServiceImpl implements ShaurmaService {

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(final Shaurma shaurma) {
        repository.createItem(shaurma);
    }

    @Override
    public Shaurma loadAsPersistent(final Long id) {
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Shaurma> getAll() {
        CriteriaQuery<Shaurma>  criteriaQuery = repository.myCriteriaQuery();
        Root<Shaurma> root = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(root);
        return repository.readItems(criteriaQuery);
    }


    @Override
    public void update(final Shaurma detachedEntity) {
        repository.updateItem(detachedEntity);
    }

    @Override
    public void delete(final Shaurma persistentShaurma) {
        repository.deleteItem(persistentShaurma);
    }

    @Override
    public boolean tryDelete(final Long id) {
        final Shaurma shaurma = repository.get(id);
        if (shaurma != null) {
            repository.deleteItem(shaurma);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        return Optional.of(repository.get(id));
    }
}
