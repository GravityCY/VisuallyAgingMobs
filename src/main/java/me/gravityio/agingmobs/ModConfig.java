package me.gravityio.agingmobs;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = AgingMobsMod.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public static ModConfig INSTANCE;
    @ConfigEntry.Gui.Tooltip
    public boolean mob_aging = true;
    @ConfigEntry.Gui.Tooltip
    public boolean grow_hitbox = false;
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public AgeMobOnly only = AgeMobOnly.ALL;

    public boolean canDo(AgeMobOnly type) {
        return mob_aging || only == AgeMobOnly.ALL || only == type;
    }

    public boolean cantDo(AgeMobOnly type) {
        return !mob_aging || (only != AgeMobOnly.ALL && only != type);
    }

    public enum AgeMobOnly {
        ALL,
        ANIMALS,
        VILLAGERS
    }
}
