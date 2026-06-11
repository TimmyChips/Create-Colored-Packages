package timmychips.colored_packages.spriteshifts;

import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.world.item.DyeColor;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.compat.DyeDepotCompat;

import java.util.EnumMap;
import java.util.Map;

public class AllPackagerSpriteShifts {
    public static final Map<DyeColor, SpriteShiftEntry> DYED_PACKAGERS = new EnumMap<>(DyeColor.class);

    public static final String ORIGINAL_PATH = "block/dyed_packager_color_label"; // The original resource location that the sprite shift will replace
    // The resolver to return the target sprite's path (is replaced by Dye Depot version if that mod is enabled)
    public static ISpritePath SPRITE_RESOLVER = new SpriteDefaultPath();

    static {
        DyeDepotCompat.setDyeDepotSpriteResolver();
        populateMaps();
    }

    private static void populateMaps() {
        for (DyeColor color : DyeColor.values()) {
            // E.g. Dyed packager color labels will go from "textures/block/dyed_packager_color_label" to "textures/block/dyed_packager_color_label/red"
            DYED_PACKAGERS.put(color, get(ORIGINAL_PATH, SPRITE_RESOLVER.targetPath(color)));
        }
    }

    // Copied from Create's AllSpriteShifts class with namespace tweak
    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(ColoredPackages.asResource(originalLocation), ColoredPackages.asResource(targetLocation));
    }

    public static void init() {}
}
