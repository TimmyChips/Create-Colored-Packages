package timmychips.colored_packages.neoforge;

import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.world.item.Item;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageItemForge;


public class AllPackageItemsImpl {
    // Return Forge specific colored package item
    public static Item getPlatformPackageItem(Item.Properties properties, PackageStyles.PackageStyle style) {
        return new ColoredPackageItemForge(properties, style);
    }
}
