package me.gravityio.agingmobs.mixin.impl.client;

import me.gravityio.agingmobs.ModConfig;
import me.gravityio.agingmobs.ModConfig.AgeMobOnly;
import me.gravityio.agingmobs.mixin.MixinUtils;
import me.gravityio.agingmobs.mixin.TransitiveModelData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class AgingEntityMixins {

    @Mixin(LivingEntityRenderer.class)
    public static abstract class LivingBabyGrowsMixin<T extends LivingEntity, M extends EntityModel<T>>
            extends EntityRenderer<T> {
        @Shadow
        protected M model;
        @Unique
        private float baseShadow = -1;

        protected LivingBabyGrowsMixin(EntityRendererFactory.Context ctx) {
            super(ctx);
        }

        /**
         * Scales the whole MatrixStack of the model using it's age
         */
        @Inject(
                method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
                at = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;scale(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V",
                        shift = At.Shift.AFTER)
        )
        private void scaleByAge(T entity, float f, float g, MatrixStack stack, VertexConsumerProvider v, int i, CallbackInfo ci) {
            if (this.baseShadow == -1) this.baseShadow = this.shadowRadius;
            if (this.shadowRadius != this.baseShadow) this.shadowRadius = this.baseShadow;

            if (ModConfig.INSTANCE.cantDo(AgeMobOnly.ANIMALS)
                    || !(entity instanceof PassiveEntity passiveEntity)
                    || !passiveEntity.isBaby()) return;

            var s = 1 - passiveEntity.getBreedingAge() / -24000f;
            var data = MixinUtils.getModelData(this.model, this.baseShadow);

            var minModel = 1f;
            var maxModel = data.maxModel();
            var minShadow = data.minShadow();
            var maxShadow = this.baseShadow * 2;

            var t = MathHelper.lerp(s, minModel, maxModel);
            stack.scale(t, t, t);
            this.shadowRadius = MathHelper.lerp(s, minShadow, maxShadow);
            TransitiveModelData.AGE = passiveEntity.getBreedingAge();
        }
    }
}