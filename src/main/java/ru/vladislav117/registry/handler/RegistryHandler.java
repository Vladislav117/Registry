package ru.vladislav117.registry.handler;

import java.util.function.BiConsumer;

/**
 * Обработчик реестра.
 *
 * @param <IdType>     Класс id
 * @param <ObjectType> Класс объекта
 */
public class RegistryHandler<IdType, ObjectType> {
    protected final float priority;
    protected final BiConsumer<IdType, ObjectType> handler;

    /**
     * Создание обработчика реестра.
     *
     * @param priority Приоритет, чем меньше число, тем раньше будет вызван обработчик
     * @param handler  Вызываемая функция
     */
    public RegistryHandler(float priority, BiConsumer<IdType, ObjectType> handler) {
        this.priority = priority;
        this.handler = handler;
    }

    /**
     * Получение приоритета. Чем меньше число, тем раньше будет вызван обработчик.
     *
     * @return Приоритет.
     */
    public float getPriority() {
        return priority;
    }

    /**
     * Получение вызываемой функции.
     *
     * @return Вызываемая функция.
     */
    public BiConsumer<IdType, ObjectType> getHandler() {
        return handler;
    }

    /**
     * Обработка объекта реестра.
     *
     * @param id     Id объекта
     * @param object Объект
     * @return Этот же обработчик реестра.
     */
    public RegistryHandler<IdType, ObjectType> handle(IdType id, ObjectType object) {
        handler.accept(id, object);
        return this;
    }
}
