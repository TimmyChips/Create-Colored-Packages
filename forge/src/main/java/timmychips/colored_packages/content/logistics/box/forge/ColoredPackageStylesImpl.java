package timmychips.colored_packages.content.logistics.box.forge;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.List;
import java.util.Random;

import static timmychips.colored_packages.content.logistics.box.ColoredPackageStyles.ALL_COLORED_BOXES;
import static timmychips.colored_packages.content.logistics.box.ColoredPackageStyles.BOXES_BY_COLOR;

public class ColoredPackageStylesImpl {
    private static final Random STYLE_PICKER = new Random();
    private static final int RARE_CHANCE = 7500;

    public static void addColoredPackageToMap(PackageItem packageItem) {
        if (packageItem.equals(ItemStack.EMPTY)) {
            ColoredPackages.LOGGER.info("colored package item in is air, ignoring");
            return;
        }

        ColoredPackages.LOGGER.info("packageItm in: {}", packageItem);
        ColoredPackages.LOGGER.info("packageItm style in: {}", packageItem.style.type());
//        DyeColor color = DyeColor.valueOf(packageItem.style.type()); // Get DyeColor by colored package item's style type "i.e. red, blue"
        DyeColor color = DyeColor.byName(packageItem.style.type(), DyeColor.WHITE);

        ColoredPackages.LOGGER.info("addColoredPackageToMap color: {}", color);

        // Get current list of PackageItems of this color, else create new list
//        List<PackageItem> coloredPackages = BOXES_BY_COLOR.getOrDefault(color, List.of());
//        coloredPackages.add(packageItem);
//
//        BOXES_BY_COLOR.put(color, coloredPackages);
    }


//    public static ItemStack getRandomColoredBox(DyeColor color) {
//        ColoredPackages.LOGGER.info("DyeColor color: {}", color);
//
//        // Get list of PackageItem for specific color
//        List<? extends PackageItem> coloredPackages = ColoredPackageStyles.ALL_COLORED_BOXES.stream()
//                .filter(packageItem -> packageItem.style.type().equals(color.getName()))
//                .toList();
//
//        ColoredPackages.LOGGER.info("coloredPackages list for this color: {}", coloredPackages);
//
//        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
//                PackageStyles.RARE_BOXES : coloredPackages;
//        ColoredPackages.LOGGER.info("returned random item stack box: {}", new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))));
//        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size())));
//    }
}
