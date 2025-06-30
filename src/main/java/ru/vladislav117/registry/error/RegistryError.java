package ru.vladislav117.registry.error;

/**
 * Ошибка, связанная с реестром.
 */
public class RegistryError extends Error {
    /**
     * Создание ошибки, связанной с реестром.
     *
     * @param message Сообщение об ошибке
     */
    public RegistryError(String message) {
        super(message);
    }
}
