package timmychips.colored_packages;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.HashMap;
import java.util.Map;

import static com.mojang.text2speech.Narrator.LOGGER;

public class AllPackagePartialModels {
    public static final Map<ResourceLocation, PartialModel> COLORED_PACKAGES = new HashMap<>();
    public static final PartialModel DYED_PACKAGER_COLOR_LABEL = block("dyed_packager/color_label");

//    for (PackageStyles.PackageStyle style : P) {
//        ResourceLocation key = style.getItemId();
//        PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));

    static  {

        /*
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

         */


        // Constant type
        for (PackageStyles.PackageStyle sizeStyle : ColoredPackageStyles.STYLES) {
            ResourceLocation key = ColoredPackageStyles.getColoredItemId(sizeStyle);
            PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath()));

            // Add to Create Partial Models for packages
            AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(sizeStyle.getRiggingModel()));
            AllPartialModels.PACKAGES.put(key, model);
        }

//        // Partial models for color
        for (DyeColor color : DyeColor.values()) {
            for (PackageStyles.PackageStyle sizeStyle : ColoredPackageStyles.STYLES) {

                ResourceLocation key = ColoredPackageStyles.getColoredItemId(sizeStyle);
                String path = color.getName() + key.getPath().split("colored")[1];

                PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + path));
                ResourceLocation coloredPackageKey = ColoredPackages.asResource(path);

                ColoredPackages.LOGGER.info("colored box key: {}, model: {}", coloredPackageKey, model);

                // Add to Create Partial Models for packages
//                AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(sizeStyle.getRiggingModel()));
//                AllPartialModels.PACKAGES.put(key, model);
                COLORED_PACKAGES.put(coloredPackageKey, model);
                AllPartialModels.PACKAGES.put(coloredPackageKey, model);
                AllPartialModels.PACKAGE_RIGGING.put(coloredPackageKey, PartialModel.of(sizeStyle.getRiggingModel()));
            }
        }
        LOGGER.info("Colored package partial models: {}", COLORED_PACKAGES);
        ColoredPackages.LOGGER.info("Create package partial models: {}", AllPartialModels.PACKAGES);

    }

    public static PartialModel coloredPartialFromColor(ItemStack stack, String colorStr) {
        ResourceLocation key = stack.getItem().arch$registryName();
        String path = colorStr + key.getPath().split("colored")[1];
        LOGGER.info("colored box partial path from stack: {}", ColoredPackages.asResource("item/" + path));
        LOGGER.info("partial model got: {}", COLORED_PACKAGES.get(ColoredPackages.asResource(path)));
        return COLORED_PACKAGES.get(ColoredPackages.asResource(path));
    }

    public static ResourceLocation coloredPartialResourceFromColor(ItemStack stack, String colorStr) {
        ResourceLocation key = stack.getItem().arch$registryName();
        String path = colorStr + key.getPath().split("colored")[1];
        ColoredPackages.LOGGER.info("colored box resource: {}", ColoredPackages.asResource("item/" + path));
        return ColoredPackages.asResource("item/" + path);
    }
    // with item resource
//    public static ResourceLocation coloredResourceFromColor(ItemStack stack, String colorStr) {
//        ResourceLocation key = stack.getItem().arch$registryName();
//        String path = colorStr + key.getPath().split("colored")[1];
//        ColoredPackages.LOGGER.info("colored box resource: {}", ColoredPackages.asResource("item/" + path));
//        return ColoredPackages.asResource("item/" + path);
//    }

    private static PartialModel block(String path) {
        return PartialModel.of(ColoredPackages.asResource("block/" + path));
    }

    public static void init() {}
}
