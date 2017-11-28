package org.pizza.model.supportClasses;

import org.pizza.model.Order;
import org.pizza.model.Pizza;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, String> orderNumber;
	public static volatile SingularAttribute<Order, Float> totalCoast;
	public static volatile SetAttribute<Order, Pizza> pizzaSet;

}

