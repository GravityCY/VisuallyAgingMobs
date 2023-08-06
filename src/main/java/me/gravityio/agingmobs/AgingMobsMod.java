package me.gravityio.agingmobs;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgingMobsMod implements ModInitializer, PreLaunchEntrypoint {
    public static final String MOD_ID = "agingmobs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static ConfigHolder<ModConfig> CONFIG_HOLDER;

    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        CONFIG_HOLDER = AutoConfig.getConfigHolder(ModConfig.class);
        ModConfig.INSTANCE = CONFIG_HOLDER.get();
    }

    @Override
    public void onInitialize() {
        WorkingModels.add(CowEntityModel.class, EntityType.COW);
        WorkingModels.add(PigEntityModel.class, EntityType.PIG);
        WorkingModels.add(ChickenEntityModel.class, EntityType.CHICKEN);
        WorkingModels.add(VillagerResemblingModel.class, EntityType.VILLAGER);
        WorkingModels.add(BeeEntityModel.class, EntityType.BEE);
        WorkingModels.add(SheepEntityModel.class, EntityType.SHEEP);
        WorkingModels.add(SheepWoolEntityModel.class);
        WorkingModels.add(WolfEntityModel.class, EntityType.WOLF);
    }


}
