package timmychips.colored_packages.content.logistics.box;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.ColoredPackages;

import java.util.*;

public class ColoredPackageStyles extends PackageStyles {

    public static final List<PackageStyle> COLORED_STYLES = ImmutableList.of(
            new PackageStyle("red", 12, 12, 23f, false),
            new PackageStyle("red", 12, 10, 22f, false),
            new PackageStyle("lime", 12, 12, 23f, false)
    );

    public static List<PackageItem> ALL_COLORED_BOXES = new ArrayList<>();
    public static Map<DyeColor, ArrayList<PackageItem>> BOXES_BY_COLOR = new EnumMap<>(DyeColor.class); // Map of all colors and their respective list of ColoredPackageItems

    public static final List<PackageStyle> RED_STYLES = ImmutableList.of(
            new PackageStyle("red", 12, 12, 23f, false)
    );

    public static final PackageStyles.PackageStyle RED_PACKAGE_STYLE =
            new PackageStyles.PackageStyle("red", 12, 12, 23f, false);

    private static final Random STYLE_PICKER = new Random();
    private static final int RARE_CHANCE = 7500;

    // Create's PackageStyles.PackageStyle getItemId() method always has "create" id in front, this band-aid method replaces the namespace with ours
    public static ResourceLocation getColoredItemId(PackageStyles.PackageStyle style) {
        String itemPath = style.getItemId().getPath();
        return new ResourceLocation(ColoredPackages.MOD_ID, itemPath);
    }

    public static void addColoredPackageToMap1(PackageItem packageItem) {
//         Perform for each dye color

        DyeColor color = DyeColor.byName(packageItem.style.type(), DyeColor.WHITE);

        ColoredPackages.LOGGER.info("DyeColor color: {}", color);

        // Stream and create new List of PackageItem for specific color
//            List<PackageItem> coloredPackages = ColoredPackageStyles.ALL_COLORED_BOXES.stream()
//                    .filter(packageItem -> packageItem.style.type().equals(color.getName()))
//                    .toList();

//            PackageItem packageItem1 = ALL_COLORED_BOXES.get(0);

        ArrayList<PackageItem> coloredPackages = BOXES_BY_COLOR.getOrDefault(color, new ArrayList<>());

//            ArrayList<PackageItem> coloredPackages = new ArrayList<>();
        coloredPackages.add(packageItem);

//            coloredPackages.add((PackageItem) packageItem);


        ColoredPackages.LOGGER.info("coloredPackages list for this color: {}", coloredPackages);

        BOXES_BY_COLOR.put(color, coloredPackages);
    }

//    @ExpectPlatform
//    public static ItemStack getRandomColoredBox(DyeColor color) {
//        return ItemStack.EMPTY;
//    }

    public static ItemStack getRandomColoredBox(DyeColor color) {
        // Get all the ColoredPackageItems from the map based on input color
        List<? extends PackageItem> coloredPackages = BOXES_BY_COLOR.getOrDefault(color, new ArrayList<>(PackageStyles.STANDARD_BOXES));

        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
                PackageStyles.RARE_BOXES : coloredPackages;
        ColoredPackages.LOGGER.info("returned random item stack box: {}", new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))));
        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size())));
    }

    public static void init() {
//        initColoredPackageMaps();
    }
}
