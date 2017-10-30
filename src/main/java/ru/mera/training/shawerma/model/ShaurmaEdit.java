package ru.mera.training.shawerma.model;

import ru.mera.training.shawerma.objects.Ingredient;
import ru.mera.training.shawerma.objects.Shaurma;

// TODO реализовать проверку админских прав для критических изменений
public interface ShaurmaEdit {
    boolean save(Shaurma shaurma);

    boolean delete(Shaurma shaurma);

    boolean edit(Shaurma shaurma);

    boolean edit(Shaurma shaurma, Ingredient ingredient);

    boolean add(Shaurma shaurma);

    boolean add(Shaurma shaurma, Ingredient ingredient);

}
