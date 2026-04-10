package timmychips.colored_packages.content.logistics;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.AllPackageItems;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

    public DisplayItemsGenerator() {}

    private static Function<Item, ItemStack> inCreativeMenuStackFunc() {
        Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>(); // Factory with Item and function to apply for items in creative menu

        // Map of all the item entries and the function to set the PackageColor tag for the item stack in the creative mode menu
        Map<ItemProviderEntry<?>, Function<Item, ItemStack>> itemInCreativeMenuFactory = new Reference2ReferenceOpenHashMap<>();

        // Set PackageColor for each package item entry
        AllPackageItems.packageItemEntries.forEach(packageEntry -> {
            itemInCreativeMenuFactory.put(packageEntry, item -> {
                ItemStack stack = new ItemStack(item);
                ColoredPackageItem.setColor(stack, DyeColor.RED);
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

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        ColoredPackages.LOGGER.info("Accepting display items generator");
        Function<Item, ItemStack> creativeModeStackFunc = inCreativeMenuStackFunc();

        List<Item> items = collectItems();

        outputAll(output, items, creativeModeStackFunc);
    }

    private static void outputAll(CreativeModeTab.Output output, List<Item> items, Function<Item, ItemStack> stackFunc) {
        for (Item item : items) {
            output.accept(stackFunc.apply(item));
        }
    }

    private List<Item> collectItems() {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryEntry<Item> entry : ColoredPackages.REGISTRATE.getAll(Registries.ITEM)) {
            Item item = entry.get();
            items.add(item);
        }
        return items;
    }
}
