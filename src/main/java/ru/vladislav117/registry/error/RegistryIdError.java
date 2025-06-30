package ru.vladislav117.registry.error;

/**
 * Ошибка, возникающая при отсутствии объекта по заданному id.
 */
public class RegistryIdError extends RegistryError {
    /**
     * Создание ошибки, возникающей при отсутствии объекта по заданному id.
     *
     * @param id Указанный id
     */
    public RegistryIdError(Object id) {
        super("Id \"" + id + "\" not found in registry");
    }
}
