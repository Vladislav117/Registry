package ru.vladislav117.registry.error;

/**
 * Ошибка, возникающая при существовании объекта по заданному id.
 */
public class RegistryIdExistsError extends RegistryError {
    /**
     * Создание ошибки, возникающей при существовании объекта по заданному id.
     *
     * @param id Указанный id
     */
    public RegistryIdExistsError(Object id) {
        super("Id \"" + id + "\" already exists in registry");
    }
}
