package timmychips.colored_packages.neoforge;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import timmychips.colored_packages.ModifyCreativeMenu;
import java.util.List;
import java.util.function.Function;

public class ModifyCreativeMenuForge extends ModifyCreativeMenu {

    /**
     * Adds colored packages with a default PackageColor tag to the Creative Mode search tab
     * @param event The build contents event
     */
    public static void addTaggedPackagesForge(BuildCreativeModeTabContentsEvent event) {

        if (event.getTab() != CreativeModeTabs.searchTab()) return; // Only perform for Search tab (so it doesn't do it for every single tab)

        List<Item> packageItems = collectPackageItems();
        Function<Item, ItemStack> creativeModeStackFunc = inCreativeMenuStackFunc(); // The function to modify the package items

        for (Item item : packageItems) {
            event.accept(creativeModeStackFunc.apply(item), CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
        }
    }
}
