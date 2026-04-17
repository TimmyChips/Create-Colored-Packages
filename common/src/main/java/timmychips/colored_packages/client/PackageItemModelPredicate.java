package timmychips.colored_packages.client;

import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.world.item.DyeColor;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

public class PackageItemModelPredicate {
    public static void register() {
        for (DyeColor color : DyeColor.values()) {
            ItemPropertiesRegistry.registerGeneric(ColoredPackages.asResource(color.getName()), (itemStack, level, livingEntity, i) -> {
                return ColoredPackageItem.hasColor(itemStack, color) ? 1.0F : 0.0F;
            });
        }
    }
}
