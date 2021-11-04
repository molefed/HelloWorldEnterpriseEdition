package ru.molefed.utils;

import ru.molefed.persister.entity.AEntityWithId;

public class EntityWithIdUtils {

    public static boolean isEmptyId(AEntityWithId entity) {
        return entity == null || entity.getId() == null;
    }

    public static boolean equalsId(AEntityWithId obj1, AEntityWithId obj2) {
        if (isEmptyId(obj1) || isEmptyId(obj2))
            return false;

        return obj1.getId().equals(obj2.getId());
    }


}
