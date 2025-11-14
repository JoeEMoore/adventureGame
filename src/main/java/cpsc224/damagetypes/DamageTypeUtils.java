package cpsc224.damagetypes;

import java.util.ArrayList;
import java.util.Collection;

public class DamageTypeUtils {

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
