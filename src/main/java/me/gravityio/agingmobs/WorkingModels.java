package me.gravityio.agingmobs;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class WorkingModels {

    public static Map<Class<? extends EntityModel<?>>, Boolean> models = new HashMap<>();
    public static Map<EntityType<? extends Entity>, Boolean> entities = new HashMap<>();

    public static <T extends Entity, S extends EntityModel<T>> void add(Class<S> model) {
        add(model, null);
    }

    public static <T extends Entity, S extends EntityModel<T>> void add(Class<S> model, @Nullable EntityType<T> entity) {
        models.put(model, true);
        if (entity == null) return;
        entities.put(entity, true);
    }

    public static boolean isWorking(@NotNull EntityModel<?> entity) {
        return models.containsKey(entity.getClass());
    }

    public static boolean isWorking(EntityType<?> type) {
        return entities.containsKey(type);
    }
}
