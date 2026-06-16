package timmychips.colored_packages.content.logistics.box.util;

import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

// Useful methods for getting the Partial or Resource from the colored package ItemStack
public class ColoredPackagePartialUtil {

    /**
     * Gets the partial model object from the box item stack's PackageColor nbt tag
     * @param box The ItemStack package
     * @return The PartialModel for this colored box
     */
    public static PartialModel getPartialFromTagColor(ItemStack box) {
        PartialModel model = AllPartialModels.PACKAGES.get(getResourceKeyFromTagColor(box));

        if (model == null) model = AllPartialModels.PACKAGES.get(box.getItem().arch$registryName()); // Revert to getting the default model from the ItemStack if it can't find colored model
        return model;
    }

    /** Gets the box item's ResourceLocation item id with its PackageColor nbt tag
     * <p> This is also used for the conveyor renderer since that one doesn't use a PartialModel
     *
     * @param box The ItemStack package
     * @return Returns the resource/id the package should be on the based on it's PackageColor nbt tag
     * <p>I.e. it will return "colored_packages:red_package_12x12" from "colored_packages:colored_package_12x12"
     */
    public static ResourceLocation getResourceKeyFromTagColor(ItemStack box) {

        if (box.getItem().arch$registryName().getNamespace().equals("colored_packages")) { // Get for items with "colored_packages" id namespace
            CompoundTag compoundTag = box.getTag();
            if (compoundTag != null && !compoundTag.isEmpty()) {
                String color = compoundTag.getString(ColoredPackageItem.TAG_COLOR);
                return AllPackagePartialModels.coloredResourceFromColor(box, color);
            }
        }

        return box.getItem().arch$registryName(); // If no color tag, get the PartialModel from the box's item id
    }
}