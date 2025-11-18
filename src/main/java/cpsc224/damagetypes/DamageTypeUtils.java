package cpsc224.damagetypes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Helper class for the DamageType Enum.
 */
public class DamageTypeUtils {

    /**
     * Returns a collection of damage types not including the Pure damage type.
     * @return collection of damage types
     */
    public static Collection<DamageType> getDamageTypes() {
        Collection<DamageType> types = new ArrayList<>();
        for (DamageType type : DamageType.values()) {
            if (type == DamageType.Pure)
                continue;

            types.add(type);
        }
        return types;
    }
}
