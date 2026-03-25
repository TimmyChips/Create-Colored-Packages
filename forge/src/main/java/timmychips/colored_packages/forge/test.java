package timmychips.colored_packages.forge;

import com.simibubi.create.content.logistics.box.PackageItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.forge.content.logistics.box.ColoredPackageItemForge;

public class test {
    public static void addColoredPackageToMap(ColoredPackageItemForge packageItem) {
        if (packageItem.equals(ItemStack.EMPTY)) {
            ColoredPackages.LOGGER.info("colored package item in is air, ignoring");
            return;
        }

//        ColoredPackages.LOGGER.info("TYPE: {}", packageItem);

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
}
