package ru.mera.sergeynazin.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface SpringRepository {

    void deleteAllInBatch();
    <S> void deleteInBatch(Collection<S> entities);

    <T> List<T> findAll();
    <S> List<S> findAllById(Collection<Serializable> ids);
    //<S> List<S> findAll(org.springframework.data.domain.Example<S> example);
    //    <S> long 	count(org.springframework.data.domain.Example<S> example);
    boolean existsById(Serializable id);

}
