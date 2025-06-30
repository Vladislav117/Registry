import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vladislav117.registry.Registry;

public final class RegistryTests {
    @Test
    public void testIntegerRegistry() {
        Registry<Integer, Integer> registry = new Registry<>();

        Assertions.assertEquals(0, registry.getSize());

        registry.add(0, 1);
        registry.add(1, 2);
        registry.add(2, 3);
        registry.addIfNotExists(3, 4);
        registry.addIfNotExists(3, 10);

        Assertions.assertEquals(4, registry.getSize());

        Assertions.assertEquals(1, registry.get(0));
        Assertions.assertEquals(2, registry.getOrNull(1));
        Assertions.assertEquals(3, registry.getOrDefault(2, 10));

        registry.remove(0);
        registry.remove(2);
        registry.remove(3);

        Assertions.assertEquals(1, registry.getSize());

        Assertions.assertTrue(registry.containsId(1));
        Assertions.assertTrue(registry.containsObject(2));
        Assertions.assertFalse(registry.containsId(0));
    }
}
