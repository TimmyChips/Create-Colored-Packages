package timmychips.colored_packages.spriteshifts;

import com.ninni.dye_depot.registry.DDDyes;
import net.minecraft.world.item.DyeColor;

// Override sprite target path to dye depot file location if it's a modded dye
public class SpriteDyeDepotPath extends SpriteDefaultPath {

    String DYE_DEPOT_TARGET_FOLDER = "block/dyed_packager_color_label/dye_depot/";

    @Override
    public String targetPath(DyeColor color) {
        if (DDDyes.isModDye(color)) return DYE_DEPOT_TARGET_FOLDER + getId(color); // dye depot path
        else return super.targetPath(color); // default path
    }
}
