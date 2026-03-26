package timmychips.colored_packages;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.HashMap;
import java.util.Map;

import static com.mojang.text2speech.Narrator.LOGGER;
import static timmychips.colored_packages.content.logistics.box.ColoredPackageStyles.RED_PACKAGE_STYLE;

public class AllPackagePartialModels {
    public static final Map<ResourceLocation, PartialModel> COLORED_PACKAGES = new HashMap<>();
    public static final PartialModel DYED_PACKAGER_COLOR_LABEL = block("dyed_packager/color_label");

//    for (PackageStyles.PackageStyle style : P) {
//        ResourceLocation key = style.getItemId();
//        PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));

    static  {

        for (PackageStyles.PackageStyle style : ColoredPackageStyles.COLORED_STYLES) {
//            ResourceLocation key = style.getItemId();
            ResourceLocation key = ColoredPackageStyles.getColoredItemId(style);
            PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));

            // Add to Create Partial Models for packages
            AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(style.getRiggingModel()));
            AllPartialModels.PACKAGES.put(key, model);

            COLORED_PACKAGES.put(key, model);

            // DEBUG
            LOGGER.info("Partial Models: {}", COLORED_PACKAGES);
        }
        ColoredPackages.LOGGER.info("Dyed packager color label partial: {}", DYED_PACKAGER_COLOR_LABEL);
    }

    private static PartialModel block(String path) {
        return PartialModel.of(ColoredPackages.asResource("block/" + path));
    }

    public static void init() {}
}
