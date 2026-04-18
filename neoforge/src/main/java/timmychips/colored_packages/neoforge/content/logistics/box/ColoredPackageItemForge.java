package timmychips.colored_packages.neoforge.content.logistics.box;

import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import java.util.Optional;

public class ColoredPackageItemForge extends ColoredPackageItem {

    public ColoredPackageItemForge(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);

        // Add this colored package item, and it's style to array list to later retrieve
        ColoredPackageStyles.ALL_COLORED_BOXES_CONSTANT.add(this);
        PackageStyles.STANDARD_BOXES.removeIf(stack -> stack.style.type().equals("colored")); // Make sure to remove it from Create's list so default Create Packager doesn't pick the wrong package
        PackageStyles.ALL_BOXES.removeIf(stack -> stack.style.type().equals("colored"));

    }

    @Override
    public PackageEntity platformNewColoredPackage(Level world, Vec3 point) {
        return new ColoredPackageEntityForge(world, point.x, point.y, point.z);
    }

    // For when package item is dropped with Q
    @Override
    public Entity createEntity(Level world, Entity location, ItemStack itemstack) {
        return ColoredPackageEntityForge.fromDroppedItem(world, location, itemstack);
    }

    /**
     * Creates a random colored box based on the DyedPackager block's color
     * <p>Forge version of this method ({@link ItemStackHandler} is on Forge api side only)
     * @param stacks The handler to serialize the NBT for the box item
     * @param color The input box passed in from the DyedPackager block entity the randomly created box will be
     * @return New random colored box with the items packaged
     */
    public static ItemStack coloredContaining(ItemStackHandler stacks, Optional<DyeColor> color) {
        ItemStack box;
        if (color.isEmpty()) {
            ColoredPackages.LOGGER.warn("Dye Color on Dyed Packager isn't set yet! Defaulting to Create's packages.");
            box = PackageStyles.getRandomBox(); // get Create random box if color isn't present
        }

        else box = ColoredPackageStyles.getRandomConstantTypeBox(); // Get random box from the input color

        box.set(AllDataComponents.PACKAGE_CONTENTS, ItemHelper.containerContentsFromHandler(stacks));
        if (color.isPresent()) {
            setColor(box, color.get());
        }
        return box;
    }
}
