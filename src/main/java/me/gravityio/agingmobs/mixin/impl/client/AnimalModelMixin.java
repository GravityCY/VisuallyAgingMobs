package me.gravityio.agingmobs.mixin.impl.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalModel.class)
public abstract class AnimalModelMixin<E extends Entity> extends EntityModel<E> {


    /**
     * Needs to inversely scale the head by some value but when that happens
     * you also need to offset the scaled head to where it would be if it wasn't scaled
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.translate (FFF)V", shift = At.Shift.AFTER, ordinal = 0))
    private void scaleAndTranslateHeadWithAge(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
//        var s = 1 - TransitiveModelData.AGE / -24000f;
//        var data = MixinUtils.getModelData(this, 1f);
//
//        var minModel = 1f;
//        var maxModel = data.maxModel();
//        var t = 1 / MathHelper.lerp(s, minModel, maxModel);
//        matrices.scale(t, t, t);
    }
}
