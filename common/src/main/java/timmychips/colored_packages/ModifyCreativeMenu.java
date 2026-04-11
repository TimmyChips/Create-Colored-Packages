package timmychips.colored_packages;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ModifyCreativeMenu {
    /**
     * Applies a function to the colored package ItemStacks to set their PackageColor to a default color (red)
     * @return The function to apply
     */
    public static Function<Item, ItemStack> inCreativeMenuStackFunc() {
        Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>(); // Factory with Item and function to apply for items in creative menu

        // Map of all the item entries and the function to set the PackageColor tag for the item stack in the creative mode menu
        Map<ItemProviderEntry<?>, Function<Item, ItemStack>> itemInCreativeMenuFactory = new Reference2ReferenceOpenHashMap<>();

        // Set PackageColor for each package item entry
        AllPackageItems.packageItemEntries.forEach(packageEntry -> {
            itemInCreativeMenuFactory.put(packageEntry, item -> {
                ItemStack stack = new ItemStack(item);
                ColoredPackageItem.setColor(stack, DyeColor.BROWN);
                return stack;
            });
        });

        // Place ItemEntry, Function into new map to perform on
        itemInCreativeMenuFactory.forEach((entry, factory) -> {
            factories.put(entry.asItem(), factory);
        });

        // Returns each item stack after applying function to it
        return item -> {
            Function<Item, ItemStack> factory = factories.get(item);
            if (factory != null) {
                return factory.apply(item);
            }
            return new ItemStack(item);
        };
    }


    /**
     * @return Collect and returns the list of colored package items
     */
    public static List<Item> collectPackageItems() {
        List<Item> items = new ReferenceArrayList<>();
        for (ItemProviderEntry<?> packageEntry : AllPackageItems.packageItemEntries) {
            Item packageItem = packageEntry.asItem();
            items.add(packageItem);
        }
        return items;
    }
}
