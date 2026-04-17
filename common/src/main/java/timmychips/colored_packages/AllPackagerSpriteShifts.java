package timmychips.colored_packages;

import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public class AllPackagerSpriteShifts {
    public static final Map<DyeColor, SpriteShiftEntry> DYED_PACKAGERS = new EnumMap<>(DyeColor.class);

    static {
        populateMaps();
    }

    private static void populateMaps() {
        for (DyeColor color : DyeColor.values()) {
            String id = color.getSerializedName();
            // E.g. Dyed packager color labels will go from "textures/block/dyed_packager_color_label" to "textures/block/dyed_packager_color_label/red"
            DYED_PACKAGERS.put(color, get("block/dyed_packager_color_label", "block/dyed_packager_color_label/" + id));
        }
    }

    // Copied from Create's AllSpriteShifts class with namespace tweak
    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(ColoredPackages.asResource(originalLocation), ColoredPackages.asResource(targetLocation));
    }

    public static void init() {}
}
