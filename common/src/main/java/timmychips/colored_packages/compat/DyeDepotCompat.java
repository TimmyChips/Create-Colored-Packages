package timmychips.colored_packages.compat;

import dev.architectury.platform.Platform;
import timmychips.colored_packages.spriteshifts.AllPackagerSpriteShifts;
import timmychips.colored_packages.spriteshifts.SpriteDyeDepotPath;

// Handles replacing implemented classes with ones for Dye Depot handling
public class DyeDepotCompat {
    public static final String DYE_DEPOT_ID = "dye_depot";
    public static boolean HAS_DYE_DEPOT;

    static {
        HAS_DYE_DEPOT = Platform.isModLoaded(DYE_DEPOT_ID);
    }

    // Could be refactored a bit more with ternary operator?
    public static void setDyeDepotSpriteResolver() {
        if (HAS_DYE_DEPOT) {
            // Replace SpriteDefaultPath with DyeDepot one
            AllPackagerSpriteShifts.SPRITE_RESOLVER = new SpriteDyeDepotPath();
        }
    }
}