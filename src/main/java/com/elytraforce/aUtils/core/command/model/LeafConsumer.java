package com.elytraforce.aUtils.core.command.model;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Functional interface that functions something like a cross between a consumer and a function
 * @param <T> Consumed type
 * @param <C> Produced type
 */
@FunctionalInterface
public interface LeafConsumer<T,C> {

    C accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

}
