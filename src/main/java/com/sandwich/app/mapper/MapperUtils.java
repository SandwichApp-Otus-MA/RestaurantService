package com.sandwich.app.mapper;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Вспомогательный класс для маппинга.
 */
public class MapperUtils {

    /**
     * Функция маппинга коллекций вложенных сущностей.
     *
     * @param toCollection   - коллекция entity
     * @param fromCollection - коллекция ТО
     * @param comparator     - как сравниваем сущности (по id)
     * @param supplier       - добавление новой сущности
     * @param mapper         - как маппим сущности
     * @param <F>            - type entity
     * @param <T>            - type TO
     */
    public static <F, T> void map(Collection<T> toCollection,
                                  Collection<F> fromCollection,
                                  BiPredicate<T, F> comparator,
                                  Supplier<T> supplier,
                                  BiFunction<T, F, T> mapper) {
        // 1. Если сущность есть и там и там: то мапим через fromTo
        mappingExistElements(toCollection, fromCollection, comparator, mapper);

        // 2. Если сущность есть в to, но нет в from: то её удалили
        deleteFromCollection(toCollection, fromCollection, comparator);

        // 3. Если сущности нет в to, но есть в from: то она новая и вызываем supplier
        addNewInCollection(toCollection, fromCollection, comparator, supplier, mapper);
    }

    /**
     * Функция маппинга коллекций вложенных сущностей с дополнительным вызовом consumer-а.
     *
     * @param toCollection   - коллекция entity
     * @param fromCollection - коллекция ТО
     * @param comparator     - как сравниваем сущности (по id)
     * @param supplier       - добавление новой сущности
     * @param mapper         - как маппим сущности
     * @param consumer       - consumer для удаления ссылки
     * @param <F>            - type entity
     * @param <T>            - type TO
     */
    public static <F, T> void map(Collection<T> toCollection,
                                  Collection<F> fromCollection,
                                  BiPredicate<T, F> comparator,
                                  Supplier<T> supplier,
                                  BiFunction<T, F, T> mapper,
                                  Consumer<T> consumer) {
        // 1. Если сущность есть и там и там: то мапим через fromTo
        mappingExistElements(toCollection, fromCollection, comparator, mapper);

        // 2. Если сущность есть в to, но нет в from: то её удалили
        deleteFromCollection(toCollection, fromCollection, comparator, consumer);

        // 3. Если сущности нет в to, но есть в from: то она новая и вызываем supplier
        addNewInCollection(toCollection, fromCollection, comparator, supplier, mapper);
    }

    private static <F, T> void mappingExistElements(Collection<T> toCollection,
                                                    Collection<F> fromCollection,
                                                    BiPredicate<T, F> comparator,
                                                    BiFunction<T, F, T> mapper) {
        fromCollection.forEach(f -> toCollection.forEach(t -> {
            if (comparator.test(t, f)) {
                mapper.apply(t, f);
            }
        }));
    }

    private static <F, T> void addNewInCollection(Collection<T> toCollection,
                                                  Collection<F> fromCollection,
                                                  BiPredicate<T, F> comparator,
                                                  Supplier<T> supplier, BiFunction<T, F, T> mapper) {
        toCollection.addAll(fromCollection.stream()
            .filter(f -> toCollection.stream().noneMatch(t -> comparator.test(t, f)))
            .map(f -> mapper.apply(supplier.get(), f))
            .collect(Collectors.toList()));
    }

    private static <F, T> void deleteFromCollection(Collection<T> toCollection,
                                                    Collection<F> fromCollection,
                                                    BiPredicate<T, F> comparator) {
        toCollection.removeIf(t -> fromCollection.stream()
            .noneMatch(dto -> comparator.test(t, dto)));
    }

    private static <F, T> void deleteFromCollection(Collection<T> toCollection,
                                                    Collection<F> fromCollection,
                                                    BiPredicate<T, F> comparator,
                                                    Consumer<T> deleteLink) {
        // 2. Если сущность есть в to, но нет в from: то её удалили
        toCollection.removeIf(t -> {
            boolean compare = fromCollection.stream()
                .noneMatch(dto -> comparator.test(t, dto));
            if (compare) {
                deleteLink.accept(t);
            }
            return compare;
        });
    }

}