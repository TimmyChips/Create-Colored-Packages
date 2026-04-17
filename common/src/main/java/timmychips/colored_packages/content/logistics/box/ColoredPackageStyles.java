package timmychips.colored_packages.content.logistics.box;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.ColoredPackages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColoredPackageStyles extends PackageStyles {

    public static List<PackageStyle> STYLES = new ArrayList<>(); // Type is the same but dimensions and rigging offset is unique

    // Record for declaring all colored package style's sizes
    public record PackageStyleSize(int width, int height, float riggingOffset) {
        public ResourceLocation getItemId() {
            String size = "_" + width + "x" + height;
            String id = "colored_package" + size;

            return ColoredPackages.asResource(id);
        }
    }

    // All package style sizes for all colored packages to use/get created from
    public static final List<PackageStyleSize> PACKAGE_STYLE_SIZES = ImmutableList.of(
            new PackageStyleSize(12, 12, 23f),
            new PackageStyleSize(10, 12, 22f),
            new PackageStyleSize(10, 8, 18f),
            new PackageStyleSize(12, 10, 21f)
    );

    // Create new package style from the defined size
    private static PackageStyle newColoredPackageStyleConstantType(PackageStyleSize packageSize) {
        return new PackageStyle("colored", packageSize.width(), packageSize.height(), packageSize.riggingOffset(), false);
    }

    /**
     * New colored package style for each package size
     * <p> Creates package styles for the type, "colored"; i.e. a created style will be "colored, 12, 12, 23f, false" or "colored, 10, 8, 18f, false"
     * <p> The package color style does not define all the colored models, but rather a template for creating all the package items.
     * <p> The color is fetched from the ItemStack's 'PackageColor' tag.
     */
    public static void initColoredPackageStyles() {
        for (PackageStyleSize packageSize : PACKAGE_STYLE_SIZES) {
            STYLES.add(newColoredPackageStyleConstantType(packageSize));
        }
    }

    // The default colored package item list to add and retrieve from
    // Reminder that we get the model from the package item's PackageColor tag, not their item id
    public static List<PackageItem> ALL_COLORED_BOXES_CONSTANT = new ArrayList<>();

    // For randomization
    private static final Random STYLE_PICKER = new Random();
    private static final int RARE_CHANCE = 7500; // Chance of getting Create's rare package box instead

    // Create's PackageStyles.PackageStyle getItemId() method always has "create" id in front, this band-aid method replaces the namespace with ours
    public static ResourceLocation getColoredItemId(PackageStyles.PackageStyle style) {
        String itemPath = style.getItemId().getPath();
        return new ResourceLocation(ColoredPackages.MOD_ID, itemPath);
    }

    /**
     *
     * @return Random colored box; it's color not yet initialized
     */
    public static ItemStack getRandomConstantTypeBox() {
                // Get Create rare boxes list rarely, or else get the list of the specified color package items
        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
                PackageStyles.RARE_BOXES : ALL_COLORED_BOXES_CONSTANT;
        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))); // Return random package item from list
    }

    // For Ponder scene
    public static ItemStack getDefaultColoredBox() {
        ItemStack box = new ItemStack(ALL_COLORED_BOXES_CONSTANT.get(0));
        ColoredPackageItem.setColor(box, DyeColor.RED);
        return box;
    }

    // Returns rigging model for the "layered package" styles
    public static ResourceLocation getLayeredRiggingModel(PackageStyle sizeStyle) {
        int width = sizeStyle.width();
        int height = sizeStyle.height();

        String size = width + "x" + height;
        return ColoredPackages.asResource("item/rigging/layered_" + size);
    }
}
