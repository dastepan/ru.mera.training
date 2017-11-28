package org.pizza.model.supportClasses;

import org.pizza.model.Ingredient;
import org.pizza.model.Menu;
import org.pizza.model.Order;
import org.pizza.model.Pizza;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pizza.class)
public abstract class Pizza_ {

	public static volatile SingularAttribute<Pizza, String> name;
	public static volatile ListAttribute<Pizza, Ingredient> ingredients;
	public static volatile ListAttribute<Pizza, Order> orders;
	public static volatile SingularAttribute<Pizza, Integer> id;
	public static volatile SingularAttribute<Pizza, Menu> menu;

}

