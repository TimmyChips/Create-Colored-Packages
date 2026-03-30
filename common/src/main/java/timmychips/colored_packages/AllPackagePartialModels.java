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

    public static final PartialModel DYED_PACKAGER_COLOR_LABEL = block("dyed_packager/color_label"); // Partial model of the color label that will be rendered with Dyed Packager block

    static  {

        // Not really used in the normal game (since all colored packages are created with color suffix), but just in case to avoid null rendering errors
        // Define and put all uncolored colored packages (lol) into Create's Package partial models
        // It's not used because PartialModels don't use item model predicate overrides, so we have to define the actual COLOR packages partial models
        for (PackageStyles.PackageStyle sizeStyle : ColoredPackageStyles.STYLES) {

            ResourceLocation key = ColoredPackageStyles.getColoredItemId(sizeStyle); // Item id of package
            PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + key.getPath())); // Where the model is located at in the resources
                    // e.g. resources/assets/colored_packages/models/item/colored_package_12x10

            // Add to Create Partial Models for packages
            AllPartialModels.PACKAGE_RIGGING.put(key, PartialModel.of(sizeStyle.getRiggingModel()));
            AllPartialModels.PACKAGES.put(key, model);
        }

        // Color partial models
        // Define and register all partial models of colored package models with their color (e.g. "green_package_12x12") and put into Create's Package partial models
        for (DyeColor color : DyeColor.values()) {
            for (PackageStyles.PackageStyle sizeStyle : ColoredPackageStyles.STYLES) {

                ResourceLocation key = ColoredPackageStyles.getColoredItemId(sizeStyle);
                String path = splitStringForColor(color.getName(), key);

                ResourceLocation coloredPackageKey = ColoredPackages.asResource(path); // "Key" id of colored package
                PartialModel model = PartialModel.of(ColoredPackages.asResource("item/" + path)); // Model location (e.g. "assets/colored_packages/models/item/purple_package_10x12")

                // Add to Create Partial Models for packages
                AllPartialModels.PACKAGES.put(coloredPackageKey, model);
                AllPartialModels.PACKAGE_RIGGING.put(coloredPackageKey, PartialModel.of(sizeStyle.getRiggingModel()));
            }
        }
    }

    // Get the associated PartialModel from the colored package ResourceLocation
    public static PartialModel coloredPartialFromColor(ItemStack stack, String colorStr) {
        return AllPartialModels.PACKAGES.get(coloredResourceFromColor(stack, colorStr));
    }

    // Get ResourceLocation of the package with its color
    public static ResourceLocation coloredResourceFromColor(ItemStack stack, String colorStr) {
        ResourceLocation key = stack.getItem().arch$registryName();
        if (key == null) return null;

        return ColoredPackages.asResource(splitStringForColor(colorStr, key)); // Return resource id the color the package should be
    }

    // Splits string and appends the color for it
    // E.g. turns path string from "colored_package_12x12" to "blue_package_12x12"
    private static String splitStringForColor(String color, ResourceLocation itemId) {
        ColoredPackages.LOGGER.info("string in: {}", itemId);
        return color + itemId.getPath().split("colored")[1];
    }

    private static PartialModel block(String path) {
        return PartialModel.of(ColoredPackages.asResource("block/" + path));
    }

    public static void init() {}
}
