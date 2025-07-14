package ru.vladislav117.registry.handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Группа обработчиков реестра.
 *
 * @param <IdType>     Класс id
 * @param <ObjectType> Класс объекта
 */
public class RegistryHandlers<IdType, ObjectType> {
    protected List<RegistryHandler<IdType, ObjectType>> handlers = new ArrayList<>();

    /**
     * Перерасчёт порядка обработчиков.
     */
    protected void reorderHandlers() {
        this.handlers.sort(Comparator.comparingDouble(RegistryHandler::getPriority));
    }

    /**
     * Проверка, является ли список обработчиков пустым.
     *
     * @return Является ли список обработчиков пустым.
     */
    public boolean isEmpty() {
        return handlers.isEmpty();
    }

    /**
     * Добавление обработчика.
     *
     * @param handler Обработчик
     * @return Эта же группа обработчиков.
     */
    @SuppressWarnings("UnusedReturnValue")
    public RegistryHandlers<IdType, ObjectType> add(RegistryHandler<IdType, ObjectType> handler) {
        this.handlers.add(handler);
        this.reorderHandlers();
        return this;
    }

    /**
     * Добавление обработчика.
     *
     * @param priority Приоритет, чем меньше число, тем раньше будет вызван обработчик
     * @param handler  Вызываемая функция
     * @return Эта же группа обработчиков.
     */
    @SuppressWarnings("UnusedReturnValue")
    public RegistryHandlers<IdType, ObjectType> add(float priority, BiConsumer<IdType, ObjectType> handler) {
        add(new RegistryHandler<>(priority, handler));
        return this;
    }

    /**
     * Обработка объекта реестра.
     *
     * @param id     Id объекта
     * @param object Объект
     * @return Эта же группа обработчиков.
     */
    @SuppressWarnings("UnusedReturnValue")
    public RegistryHandlers<IdType, ObjectType> handle(IdType id, ObjectType object) {
        for (RegistryHandler<IdType, ObjectType> handler : handlers) {
            handler.handle(id, object);
        }
        return this;
    }
}
