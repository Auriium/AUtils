package com.elytraforce.aUtils.core.command.model;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface LeafConsumer<T,C> {

    C accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

}
