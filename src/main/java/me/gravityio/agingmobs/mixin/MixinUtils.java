package me.gravityio.agingmobs.mixin;

import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.RabbitEntityModel;

public class MixinUtils {

    /**
     * Gets information about a specific model <br>
     * <ul>
     *     <li>Shadow size when it's a baby in order to scale with the age</li>
     *     <li>How much to scale the baby model in order to reach adult size</li>
     * </ul>
     */
    public static ModelData getModelData(EntityModel<?> model, float maxShadow) {
        if (model instanceof RabbitEntityModel<?>) {
            return new ModelData(0.18f, 1.5f);
        } else if (model instanceof AnimalModel<?> animal) {
            return new ModelData(maxShadow / animal.invertedChildBodyScale, animal.invertedChildBodyScale);
        }
        return new ModelData(0.25f, 2f);
    }

    public record ModelData(float minShadow, float maxModel) {}
}
