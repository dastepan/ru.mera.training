package org.pizza.model.supportClasses;

import org.pizza.model.Ingredient;
import org.pizza.model.Pizza;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ingredient.class)
public abstract class Ingredient_ {

	public static volatile SingularAttribute<Ingredient, Float> cost;
	public static volatile ListAttribute<Ingredient, Pizza> pizzas;
	public static volatile SingularAttribute<Ingredient, String> name;
	public static volatile SingularAttribute<Ingredient, Integer> id;

}

