package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Order;
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

public interface OrderService {
//    void save(Order transient_);
//    List<Order> getAll();
//    void update(Order detached);
//    void delete(Order detached);
//    Optional<Order> optionalIsExist(Long id);
//    Optional<Order> optionalIsExist(String orderNumber);


    // GET

    List<Order> getAll();

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(Shaurma_managed_instance)
     */
    Optional<Order> getOptionalIsExist(Long id);

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param orderNumber natural id
     * @return Optional.ofNullable(Shaurma_managed_instance)
     */
    Optional<Order> getOptionalIsExist(String orderNumber);

    Order getOrThrow(Long id);
    Order getOrThrow(String orderNumber);

    // POST

    /**
     * ONLY valid body shaurma (with valid nested fields)
     * should be saved
     * @param transient_ entity
     * @return id after performed INSERT statement
     */
    Long postOrThrow(Order transient_);

    // PUT
    Order putOrThrow(Order newDetached);

    // DELETE
    Order deleteByIdOrThrow(Long id);

    // helpers
    boolean validateExistsOrThrow(Order... orders);




}
