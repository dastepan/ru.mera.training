package org.pizza.model.supportClasses;

import org.pizza.model.Menu;
import org.pizza.model.Pizza;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Menu.class)
public abstract class Menu_ {

	public static volatile ListAttribute<Menu, Pizza> pizzas;
	public static volatile SingularAttribute<Menu, String> name;
	public static volatile SingularAttribute<Menu, Integer> id;

}

