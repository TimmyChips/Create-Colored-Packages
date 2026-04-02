package timmychips.colored_packages.content.logistics.box;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.ColoredPackages;

import java.util.*;

public class ColoredPackageStyles extends PackageStyles {

    public static final List<PackageStyle> COLORED_STYLES_OLD = ImmutableList.of(
            new PackageStyle("red", 12, 12, 23f, false),
            new PackageStyle("red", 12, 10, 21f, false),
            new PackageStyle("lime", 12, 12, 23f, false)
    );

    public static List<PackageStyle> COLORED_STYLES = new ArrayList<>();
    public static List<PackageStyle> STYLES = new ArrayList<>(); // type is the same but dimensions and rigging offset is unique

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

    private static PackageStyle newColoredPackageStyle(DyeColor color, PackageStyleSize packageSize) {
        return new PackageStyle(color.getName(), packageSize.width(), packageSize.height(), packageSize.riggingOffset(), false);
    }
    private static PackageStyle newColoredPackageStyleConstantType(PackageStyleSize packageSize) {
        return new PackageStyle("colored", packageSize.width(), packageSize.height(), packageSize.riggingOffset(), false);
    }

    // New colored package styles for each dye color and package sizes
    public static void initColoredPackageStyles() {
        for (DyeColor color : DyeColor.values()) {
            for (PackageStyleSize packageSize : PACKAGE_STYLE_SIZES) {
                COLORED_STYLES.add(newColoredPackageStyle(color, packageSize));
            }
        }

        // For version of constant colored type
        for (PackageStyleSize packageSize : PACKAGE_STYLE_SIZES) {
            STYLES.add(newColoredPackageStyleConstantType(packageSize));
        }
    }

    public static List<PackageItem> ALL_COLORED_BOXES = new ArrayList<>();
    public static Map<DyeColor, ArrayList<PackageItem>> BOXES_BY_COLOR = new EnumMap<>(DyeColor.class); // Map of all colors and their respective list of ColoredPackageItems
    public static List<PackageItem> ALL_COLORED_BOXES_CONSTANT = new ArrayList<>();

    public static final PackageStyles.PackageStyle RED_PACKAGE_STYLE =
            new PackageStyles.PackageStyle("red", 12, 12, 23f, false);

    private static final Random STYLE_PICKER = new Random();
    private static final int RARE_CHANCE = 7500;

    // Create's PackageStyles.PackageStyle getItemId() method always has "create" id in front, this band-aid method replaces the namespace with ours
    public static ResourceLocation getColoredItemId(PackageStyles.PackageStyle style) {
        String itemPath = style.getItemId().getPath();
        return new ResourceLocation(ColoredPackages.MOD_ID, itemPath);
    }

    /**
     * Adds colored package item to respective color list in colored boxes map
     * @param packageItem the colored package item object
     */
    public static void addColoredPackageItemToMap(PackageItem packageItem) {

        // Get DyeColor from the package item's style type (i.e. red, blue, lime, etc.)
        DyeColor color = DyeColor.byName(packageItem.style.type(), DyeColor.WHITE);
        ColoredPackages.LOGGER.info("DyeColor color: {}", color);

        // Try to fetch colored package list of this specific color, else create new list; then add to it
        // E.g. the first red package item will create a new list, subsequent red packages will get and add to that list
        ArrayList<PackageItem> coloredPackages = BOXES_BY_COLOR.getOrDefault(color, new ArrayList<>());
        coloredPackages.add(packageItem);

        ColoredPackages.LOGGER.info("coloredPackages list for this color: {}", coloredPackages);

        BOXES_BY_COLOR.put(color, coloredPackages); // Adds colored list to colored boxes map

        PackageStyles.STANDARD_BOXES.remove(packageItem); // Remove colored package item from Create's list so the normal Packager doesn't create colored packages
        PackageStyles.ALL_BOXES.remove(packageItem);
    }

    /**
     *
     * @param color The dye color of the package this method will try to look for and get
     * @return Random colored package ItemStack of the input color; or rarely gets one of the Create rare Easter egg packages
     */
    public static ItemStack getRandomColoredBox(DyeColor color) {
        // Get all the ColoredPackageItems from the map based on input color
        List<? extends PackageItem> coloredPackages = BOXES_BY_COLOR.getOrDefault(color, new ArrayList<>(PackageStyles.STANDARD_BOXES));

        // Get Create rare boxes list rarely, or else get the list of the specified color package items
        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
                PackageStyles.RARE_BOXES : coloredPackages;
        ColoredPackages.LOGGER.info("returned random item stack box: {}", new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))));
        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))); // Return random package item from list
    }

    public static ItemStack getRandomConstantTypeBox() {
                // Get Create rare boxes list rarely, or else get the list of the specified color package items
        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
                PackageStyles.RARE_BOXES : ALL_COLORED_BOXES_CONSTANT;
        ColoredPackages.LOGGER.info("returned random item stack box: {}", new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))));
        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))); // Return random package item from list
    }

    // For Ponder scene
    public static ItemStack getDefaultColoredBox() {
        ItemStack box = new ItemStack(ALL_COLORED_BOXES_CONSTANT.get(0));
        ColoredPackageItem.setColor(box, DyeColor.RED);
        return box;
    }
}
