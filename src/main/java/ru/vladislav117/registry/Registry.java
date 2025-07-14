package ru.vladislav117.registry;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.registry.error.RegistryIdError;
import ru.vladislav117.registry.error.RegistryIdExistsError;
import ru.vladislav117.registry.handler.RegistryHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реестр объектов.
 *
 * @param <IdType>     Класс id
 * @param <ObjectType> Класс объекта
 */
public class Registry<IdType, ObjectType> {
    protected Map<IdType, ObjectType> objectMap;
    protected List<ObjectType> objectList;
    protected List<IdType> idList;
    protected RegistryHandlers<IdType, ObjectType> objectAdditionHandlers = new RegistryHandlers<>();
    protected RegistryHandlers<IdType, ObjectType> objectRemovalHandlers = new RegistryHandlers<>();

    /**
     * Создание нового реестра объектов.
     *
     * @param initialCapacity Начальная вместимость реестра
     */
    public Registry(int initialCapacity) {
        objectMap = new HashMap<>(initialCapacity);
        objectList = new ArrayList<>(initialCapacity);
        idList = new ArrayList<>(initialCapacity);
    }

    /**
     * Создание нового реестра объектов.
     */
    public Registry() {
        objectMap = new HashMap<>();
        objectList = new ArrayList<>();
        idList = new ArrayList<>();
    }

    /**
     * Получение обработчиков добавления объекта.
     * Обработчики добавления объекта вызываются после добавления объекта.
     *
     * @return Обработчики добавления объекта.
     */
    public RegistryHandlers<IdType, ObjectType> getObjectAdditionHandlers() {
        return objectAdditionHandlers;
    }

    /**
     * Получение обработчиков удаления объекта.
     * Обработчики удаления объекта вызываются после удаления объекта.
     *
     * @return Обработчики удаления объекта.
     */
    public RegistryHandlers<IdType, ObjectType> getObjectRemovalHandlers() {
        return objectRemovalHandlers;
    }

    /**
     * Проверка, имеется ли id в реестре.
     *
     * @param id Id
     * @return Наличие id в реестре.
     */
    public boolean containsId(IdType id) {
        return objectMap.containsKey(id);
    }

    /**
     * Проверка, имеется ли объект в реестре.
     *
     * @param object Объект
     * @return Наличие объекта в реестре.
     */
    public boolean containsObject(ObjectType object) {
        return objectList.contains(object);
    }

    /**
     * Получение размера реестра.
     *
     * @return Размер реестра.
     */
    public int getSize() {
        return objectMap.size();
    }

    /**
     * Получение объекта из реестра.
     * Если указанного id нет в реестре, будет вызвано исключение.
     *
     * @param id Id объекта
     * @return Объект.
     * @throws RegistryIdError Если указанного id нет в реестре, будет вызвано исключение.
     */
    public ObjectType get(IdType id) {
        if (!objectMap.containsKey(id)) throw new RegistryIdError(id);
        return objectMap.get(id);
    }

    /**
     * Получение объекта из реестра.
     * Если указанного id нет в реестре, будет возвращено null.
     *
     * @param id Id объекта
     * @return Объект или null.
     */
    public @Nullable ObjectType getOrNull(IdType id) {
        return objectMap.get(id);
    }

    /**
     * Получение объекта из реестра.
     * Если указанного id нет в реестре, будет возвращено значение по умолчанию.
     *
     * @param id            Id объекта
     * @param defaultObject Объект по умолчанию
     * @return Объект или значение по умолчанию.
     */
    public ObjectType getOrDefault(IdType id, ObjectType defaultObject) {
        return objectMap.getOrDefault(id, defaultObject);
    }

    /**
     * Получение всех объектов в реестре в порядке добавления.
     *
     * @return Все объекты реестра в порядке добавления.
     */
    public List<ObjectType> getAll() {
        return objectList;
    }

    /**
     * Получение id всех объектов в реестре в порядке добавления.
     *
     * @return Id всех объектов реестра в порядке добавления.
     */
    public List<IdType> getAllIds() {
        return idList;
    }

    /**
     * Добавление объекта в реестр.
     * Если указанный id уже есть в реестре, будет вызвано исключение.
     * Обработчики добавления объекта вызываются после добавления объекта.
     *
     * @param id     Id объекта
     * @param object Объект
     * @return Этот же объект.
     * @throws RegistryIdExistsError Если указанный id уже есть в реестре, будет вызвано исключение.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Registry<IdType, ObjectType> add(IdType id, ObjectType object) {
        if (objectMap.containsKey(id)) throw new RegistryIdExistsError(id);
        objectMap.put(id, object);
        objectList.add(object);
        idList.add(id);
        if (!objectAdditionHandlers.isEmpty()) objectAdditionHandlers.handle(id, object);
        return this;
    }

    /**
     * Добавление объекта в реестр.
     * Если указанный id уже есть в реестре, то ничего не произойдёт.
     *
     * @param id     Id объекта
     * @param object Объект
     * @return Этот же объект.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Registry<IdType, ObjectType> addIfNotExists(IdType id, ObjectType object) {
        if (!objectMap.containsKey(id)) add(id, object);
        return this;
    }

    /**
     * Удаление объекта из реестра.
     * Обработчики удаления объекта вызываются после удаления объекта.
     *
     * @param id Id объекта
     * @return Этот же реестр.
     */
    @SuppressWarnings("UnusedReturnValue")
    public Registry<IdType, ObjectType> remove(IdType id) {
        if (!objectMap.containsKey(id)) return this;
        ObjectType object = objectMap.get(id);
        objectMap.remove(id);
        objectList.removeIf(o -> (o == object || o.equals(object))); // TODO: Change this
        idList.removeIf(o -> (o == id || o.equals(id))); // TODO: Change this
        if (!objectRemovalHandlers.isEmpty()) objectRemovalHandlers.handle(id, object);
        return this;
    }

    /**
     * Очистка реестра.
     * Обработчики удаления объекта вызываются после удаления всех объектов.
     *
     * @return Этот же реестр.
     */
    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public Registry<IdType, ObjectType> clear() {
        IdType[] ids = null;
        ObjectType[] objects = null;
        if (!objectRemovalHandlers.isEmpty()) {
            ids = (IdType[]) idList.toArray(Object[]::new);
            objects = (ObjectType[]) objectList.toArray(Object[]::new);
        }
        objectMap.clear();
        objectList.clear();
        idList.clear();
        if (ids != null) {
            for (int index = 0; index < ids.length; index++) {
                objectRemovalHandlers.handle(ids[index], objects[index]);
            }
        }
        return this;
    }
}
