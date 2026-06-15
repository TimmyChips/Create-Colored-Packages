package timmychips.colored_packages.compat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.Map;

public class VibrantVaultsCompat {
    public static final String VIBRANT_VAULTS_ID = "create_vibrant_vaults";
    public static boolean HAS_VIBRANT_VAULTS;
    public static Map<ResourceLocation, DyeColor> VIBRANT_PACKAGERS_MAP;

    static {
        HAS_VIBRANT_VAULTS = Platform.isModLoaded(VIBRANT_VAULTS_ID);
    }

    @ExpectPlatform
    public static void setVibrantPackagerSet() {
    }
}
