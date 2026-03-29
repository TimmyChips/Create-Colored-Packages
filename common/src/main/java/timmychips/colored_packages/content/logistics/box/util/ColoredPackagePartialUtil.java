package timmychips.colored_packages.content.logistics.box.util;

import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

public class ColoredPackagePartialUtil {
    // Gets the partial model object from the box item stack's PackageColor nbt tag
    public static PartialModel getPartialFromTag(ItemStack box) {
        PartialModel model;
        CompoundTag compoundTag = box.getTag();
        if (compoundTag != null) {
            ColoredPackages.LOGGER.info("compound tag: {}", box.getTag().getString(ColoredPackageItem.TAG_COLOR));
            String color = compoundTag.getString(ColoredPackageItem.TAG_COLOR);
            model = AllPackagePartialModels.coloredPartialFromColor(box, color);
        } else
            model = AllPartialModels.PACKAGES.get(box.getItem().arch$registryName()); // get PartialModel from the entity's box item ResourceLocation
        return model;
    }

    // Gets the box item's ResourceLocation item id with its PackageColor nbt tag
    public static ResourceLocation getPartialResourceFromTag(ItemStack box) {
        ResourceLocation resource;
        CompoundTag compoundTag = box.getTag();
        if (compoundTag != null) {
            ColoredPackages.LOGGER.info("compound tag: {}", box.getTag().getString(ColoredPackageItem.TAG_COLOR));
            String color = compoundTag.getString(ColoredPackageItem.TAG_COLOR);
            resource = AllPackagePartialModels.coloredPartialResourceFromColor(box, color);
        } else
            resource = box.getItem().arch$registryName(); // get PartialModel from the entity's box item ResourceLocation
        return resource;
    }
}