package ru.mera.training.shawerma.model;

import ru.mera.training.shawerma.objects.Ingredient;
import ru.mera.training.shawerma.objects.Shaurma;

import java.util.Set;

public interface ShaurmaSearch {
    Set<Shaurma> getShaurma(); // весь список

    Set<Shaurma> getShaurma(String name); // поиск пр названию

    Set<Shaurma> getShaurma(Integer id); // поиск по id

    Set<Shaurma> getShaurma(Ingredient ingredient); // споисок, включающий заданный ингредиент

}
