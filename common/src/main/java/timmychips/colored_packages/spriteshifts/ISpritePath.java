package timmychips.colored_packages.spriteshifts;

import net.minecraft.world.item.DyeColor;

public interface ISpritePath {
    String TARGET_FOLDER = "block/dyed_packager_color_label/";

    // Target override sprite path (e.g. 'block/dyed_packager_color_label/red')
    default String targetPath(DyeColor color) {
        return TARGET_FOLDER + getId(color);
    }

    default String getId(DyeColor color) {
        return color.getSerializedName();
    }
}
