package timmychips.colored_packages;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.HashMap;
import java.util.Map;

import static com.mojang.text2speech.Narrator.LOGGER;
import static timmychips.colored_packages.content.logistics.box.ColoredPackageStyles.RED_PACKAGE_STYLE;

public class AllPackagePartialModels {
    public static final Map<ResourceLocation, PartialModel> COLORED_PACKAGES = new HashMap<>();

//    for (PackageStyles.PackageStyle style : P) {
//        ResourceLocation key = style.getItemId();
//        PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));

    static  {

        for (PackageStyles.PackageStyle style : ColoredPackageStyles.COLORED_STYLES) {
//            ResourceLocation key = style.getItemId();
            ResourceLocation key = ColoredPackageStyles.getColoredItemId(style);
            PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));

            // Add to Create Partial Models for packages
            AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(PackageStyles.STYLES.get(0).getRiggingModel()));
            AllPartialModels.PACKAGES.put(key, model);

            COLORED_PACKAGES.put(key, model);

            // DEBUG
            LOGGER.info("Partial Models: {}", COLORED_PACKAGES);
        }

//        ResourceLocation key = ColoredPackageStyles.getColoredItemId(RED_PACKAGE_STYLE);
//        PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));
//
//        // Add to Create Partial Models for packages
//        AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(PackageStyles.STYLES.get(0).getRiggingModel()));
//        AllPartialModels.PACKAGES.put(key, model);
//
//        COLORED_PACKAGES.put(key, model);
//        LOGGER.info("Partial Models: {}", COLORED_PACKAGES);
    }

    public static void init() {}
}
