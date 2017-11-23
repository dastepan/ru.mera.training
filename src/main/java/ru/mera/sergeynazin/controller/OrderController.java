package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.service.IngredientService;
import ru.mera.sergeynazin.service.OrderService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    private ShaurmaService shaurmaService;

    private IngredientService ingredientService;

    private Order currentOrder;

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    // BEGIN_INCLUDE(IngredientController.@Admin)
    @Admin
    @Async
    @GetMapping(value = "/{order_number}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getOrderInfoByOrderNumberInJSON(final Principal principal, @PathVariable("order_number") final String orderNumber) {
        return CompletableFuture.completedFuture(get(orderNumber));
    }

    @Admin
    @Async
    @GetMapping(value = "/{order_number}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getOrderInfoByOrderNumberInXML(final Principal principal, @PathVariable("order_number") final String orderNumber) {
        return CompletableFuture.completedFuture(get(orderNumber));
    }

    /**
     * Just getting the order by
     * @see Order#orderNumber
     * @param orderNumber orderNumber from session
     * @return 200 or 404 (don't know how to send 410)
     */
    private ResponseEntity<?> get(final String orderNumber) {
        return ResponseEntity.ok(orderService.getOrThrow(orderNumber));
    }

    @Admin
    @Async
    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Order>>> getAllOrdersInJSON(final Principal principal) {
        return CompletableFuture.completedFuture(ResponseEntity.ok(orderService.getAll()));
    }

    @Admin
    @Async
    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Order>>> getAllOrdersInXML(final Principal principal) {
        return CompletableFuture.completedFuture(ResponseEntity.ok(orderService.getAll()));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteOrderInJSON(final Principal principal, @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteOrderInXML(final Principal principal, @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    /**
     * NOT NECESSARY METHOD As if the order is not supposed to be deleted???
     * @param id order id from db
     * @return 200 or 404
     */
    private ResponseEntity<?> delete(final Long id) {
        orderService.deleteByIdOrThrow(id);
        return ResponseEntity.noContent().build();
    }
    // END_INCLUDE(IngredientController.@Admin)



    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> saveCurrentOrder(/*PAYMENT PARAMETERS*/) {
        // TODO: MOCKED -> Delete
        /*final URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{order_number}")
            .buildAndExpand(orderService.postOrThrow(currentOrder)).toUri();*/

        final URI uri = URI.create("SAMPLE_PATH/");

        // FIXME: 11/21/17 Check payment
        return CompletableFuture.completedFuture(ResponseEntity.accepted().location(uri).build());
    }

    /**
     * Adds ingredient to shaurma
     * @return 200 New Shaurma
     */
    @Async
    @PostMapping(value = "/shaurma/{id}/{ingredient_name}",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> addIngredient(@PathVariable("id") final Long id,
                                                              @PathVariable("id") final String ingredientName) {

        return CompletableFuture.completedFuture(
            ingredientService.getOptionalIsExist(ingredientName)
            .map(ingredient ->
                currentOrder.getShaurmaList()
                .parallelStream()
                .filter(shaurma -> shaurma.getId().equals(id))
                .findAny()
                .map(shaurma -> {
                    shaurma.getIngredientSet().add(ingredient);
                    return ResponseEntity.ok(shaurma);
                }).orElseThrow(() -> NotFoundException.getNew(id))
            ).orElseThrow(() -> NotFoundException.getNew(ingredientName)));
    }



//    // TODO: 11/21/17 DELETE // TODO: 11/21/17 DELETE // TODO: 11/21/17 DELETE
//    /**
//     * POSTs the whole order body with nested checks
//     * @param order body
//     * @return 201 with URI
//     */
//    @Deprecated
//    @Async
//    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
//    public CompletableFuture<ResponseEntity<?>> createNewOrder(@Valid @RequestBody final Order order) {
//        orderService.postOrThrow(order);
//        final URI created = ServletUriComponentsBuilder
//            .fromCurrentRequest()
//            .path("/{order_number}")
//            .buildAndExpand(order.getOrderNumber()).toUri();
//        return CompletableFuture.completedFuture(ResponseEntity.created(created).body(order));
//    }
//    TODO: 11/21/17 DELETE // TODO: 11/21/17 DELETE // TODO: 11/21/17 DELETE
//    @Deprecated
//    @Async
//    @PutMapping(value = "/{order_id}/add/{shaurma_id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
//    public CompletableFuture<ResponseEntity<?>> updateOrderInJSON(@PathVariable("order_id") final Long orderId,
//                                                                  @PathVariable("shaurma_id") final Long shaurmaId) {
//        return CompletableFuture.completedFuture(updateOrCreateOrderFromMenu(orderId, shaurmaId));
//    }
//    @Deprecated
//    @Async
//    @PutMapping(value = "/{order_id}/add/{shaurma_id}", produces = { MediaType.APPLICATION_XML_VALUE } )
//    public CompletableFuture<ResponseEntity<?>> updateOrderInXML(@PathVariable("order_id") final Long orderId,
//                                                                 @PathVariable("shaurma_id") final Long shaurmaId) {
//        return CompletableFuture.completedFuture(updateOrCreateOrderFromMenu(orderId, shaurmaId));
//    }
//
//    @Deprecated
//    private ResponseEntity<?> updateOrCreateOrderFromMenu(final Long orderId, final Long shaurmaId) {
//        return shaurmaService.getOptionalIsExist(shaurmaId)
//            .map(shaurma -> {
//                final Order newOrder = orderService.getOptionalIsExist(orderId)
//                    .map(order -> {
//                        order.getShaurmaList().add(shaurma);
//                        return order;
//                    }).orElseGet(() -> {
//                        // @see controllers.xml -> switched to null value shaurma list
//                        currentOrder.getShaurmaList().add(shaurma);
//                        orderService.postOrThrow(currentOrder);
//                        return currentOrder;
//                    });
//                final URI created = ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .replacePath("/{order_number}")
//                    .buildAndExpand(newOrder.getOrderNumber()).toUri();
//                return ResponseEntity.created(created).body(newOrder);
//            }).orElseThrow(() -> NotFoundException.getNew(shaurmaId));
//    }
//
//
//    @Deprecated
//    @Async
//    @PutMapping(value = "/{order_id}/add", produces = { MediaType.APPLICATION_JSON_VALUE } )
//    public CompletableFuture<ResponseEntity<?>> updateOrderInJSON(@PathVariable("order_id") final Long orderId,
//                                                                  @Valid @RequestBody final Shaurma shaurma) {
//        return CompletableFuture.completedFuture(updateOrCreateOrderFromConstructor(orderId, shaurma));
//    }
//    @Deprecated
//    @Async
//    @PutMapping(value = "/{order_id}/add", produces = { MediaType.APPLICATION_XML_VALUE } )
//    public CompletableFuture<ResponseEntity<?>> updateOrderInXML(@PathVariable("order_id") final Long orderId,
//                                                                 @Valid @RequestBody final Shaurma shaurma) {
//        return CompletableFuture.completedFuture(updateOrCreateOrderFromConstructor(orderId, shaurma));
//    }
//
//    /**
//     * Method is suitable for adding the Order with custom shaurma,
//     * or for adding MORE to the amount of currently present in the order shaurma
//     * @param orderId {@link Order#getOrderNumber()}
//     * @param shaurma {@link Shaurma} body
//     * @return 200 or 201
//     */
//    @Deprecated
//    private ResponseEntity<?> updateOrCreateOrderFromConstructor(final Long orderId, final Shaurma shaurma) {
//        return orderService.getOptionalIsExist(orderId)
//            .map(order -> {
//                if (shaurma.getIngredientSet()
//                    .parallelStream()
//                    .allMatch(ingredient -> ingredientService.getOptionalIsExist(ingredient.getId()).isPresent())) {
//                    return ResponseEntity.unprocessableEntity().body(shaurma);
//                }
//                order.getShaurmaList().add(shaurma);
//                return ResponseEntity.ok(order);
//            }).orElseGet(() -> {
//                // @see controllers.xml -> switched to null value shaurma list
//                currentOrder.getShaurmaList().add(shaurma);
//                orderService.save(currentOrder);
//                final URI created = ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .replacePath("/{order_number}")
//                    .buildAndExpand(currentOrder.getOrderNumber()).toUri();
//                return ResponseEntity.created(created).body(currentOrder);
//            });
//    }
}
