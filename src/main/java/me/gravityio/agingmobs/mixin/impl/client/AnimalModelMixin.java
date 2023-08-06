package me.gravityio.agingmobs.mixin.impl.client;

import me.gravityio.agingmobs.ModConfig;
import me.gravityio.agingmobs.mixin.MixinUtils;
import me.gravityio.agingmobs.mixin.TransitiveModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalModel.class)
public abstract class AnimalModelMixin<E extends Entity> extends EntityModel<E> {
    @Shadow @Final private float childHeadYOffset;
    @Shadow @Final private float childHeadZOffset;
    @Shadow @Final private float invertedChildHeadScale;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/util/math/MatrixStack.translate (FFF)V", shift = At.Shift.AFTER, ordinal = 0))
    private void scaleAndTranslateHeadWithAge(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        var s = 1 - TransitiveModelData.AGE / -24000f;
        var data = MixinUtils.getModelData(this, 1f);

        var minModel = 1f;
        var maxModel = data.maxModel();
        var t = 1 / MathHelper.lerp(s, minModel, maxModel);
        matrices.scale(t, t, t);
    }
}
