package timmychips.colored_packages;

import com.ninni.dye_depot.registry.DDDyes;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.architectury.platform.Platform;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.compat.DyeDepotCompat;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.List;

public class AllPackagePartialModels {

    public static final PartialModel DYED_PACKAGER_COLOR_LABEL = block("dyed_packager/color_label"); // Partial model of the color label that will be rendered with Dyed Packager block
    private static final String DYE_DEPOT_DIR = "dye_depot/";

    public static List<String> HAS_LAYERED_RIGGING = List.of(
            "light_blue",
            // Dye Depot
            "tan"
    );

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
                PartialModel model = partialModelLocation(color, path); // Partial Model location

                // Add to Create Partial Models for packages
                AllPartialModels.PACKAGES.put(coloredPackageKey, model);

                // Change the rigging model for layered package models E.g. uses a thicker, "layered" package model, thus needs a wider rigging model to compensate. Unfortunately is hardcoded.
                if (HAS_LAYERED_RIGGING.contains(color.getName())) {
                    AllPartialModels.PACKAGE_RIGGING.put(coloredPackageKey, PartialModel.of(ColoredPackageStyles.getLayeredRiggingModel(sizeStyle)));
                }
                else AllPartialModels.PACKAGE_RIGGING.put(coloredPackageKey, PartialModel.of(sizeStyle.getRiggingModel()));
            }
        }
    }

    // Model location (e.g. "assets/colored_packages/models/item/purple_package_10x12")
    private static PartialModel partialModelLocation(DyeColor color, String path) {
        if (Platform.isModLoaded(DyeDepotCompat.DYE_DEPOT_ID)) {
            if (DDDyes.isModDye(color)) return PartialModel.of(ColoredPackages.asResource("item/" + DYE_DEPOT_DIR + path)); // Model will be in: 'models/item/dye_depot/verdant_package_12x12'
        }

        return PartialModel.of(ColoredPackages.asResource("item/" + path));
    }



    /**
     * Colored Package as a PartialModel
     * @param stack The package item stack
     * @param colorStr The package item's color nbt
     * @return The associated PartialModel from the colored package ResourceLocation
     */
    public static PartialModel coloredPartialFromColor(ItemStack stack, String colorStr) {
        return AllPartialModels.PACKAGES.get(coloredResourceFromColor(stack, colorStr));
    }

    /**
     * Colored Package as a ResourceLocation item id
     * @param stack The package item stack
     * @param colorStr The package item's color nbt
     * @return The ResourceLocation of the package with its color
     */
    public static ResourceLocation coloredResourceFromColor(ItemStack stack, String colorStr) {
        ResourceLocation key = stack.getItem().arch$registryName();
        if (key == null) return null;

        return ColoredPackages.asResource(splitStringForColor(colorStr, key)); // Return resource id the color the package should be
    }

    // Splits string and appends the color for it
    // E.g. turns path string from "colored_package_12x12" to "blue_package_12x12"
    private static String splitStringForColor(String color, ResourceLocation itemId) {
        return color + itemId.getPath().split("colored")[1];
    }

    private static PartialModel block(String path) {
        return PartialModel.of(ColoredPackages.asResource("block/" + path));
    }

    public static void init() {}
}
