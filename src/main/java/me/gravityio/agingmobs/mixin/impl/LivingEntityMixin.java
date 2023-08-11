package me.gravityio.agingmobs.mixin.impl;

import me.gravityio.agingmobs.ModConfig;
import me.gravityio.agingmobs.WorkingModels;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean isBaby();

    /**
     * Makes the hitbox also scale using the entities age <br>
     * Probably doesn't work with mobs that are not exactly half the size of the adult
     */
    @Inject(method = "getScaleFactor", at = @At("RETURN"), cancellable = true)
    private void getGrowing(CallbackInfoReturnable<Float> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!ModConfig.INSTANCE.grow_hitbox
                || !WorkingModels.isWorking(this.getType())
                || !this.isBaby()
                || !(self instanceof PassiveEntity passiveEntity)) return;

        float num = 0.5f * (2f - (float) passiveEntity.getBreedingAge() / PassiveEntity.BABY_AGE);
        cir.setReturnValue(num);
    }
}
