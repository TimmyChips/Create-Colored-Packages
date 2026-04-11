package timmychips.colored_packages;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.content.logistics.DisplayItemsGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PackageCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> HIDDEN_CREATIVE_TABS =
            DeferredRegister.create(ColoredPackages.MOD_ID, Registries.CREATIVE_MODE_TAB);


    public static final RegistrySupplier<CreativeModeTab> HIDDEN_PACKAGE_TAB =
            HIDDEN_CREATIVE_TABS.register("colored_packages", () ->
                    CreativeTabRegistry.create(builder -> {
                        builder.title(Component.translatable("itemGroup." + ColoredPackages.MOD_ID + ".base"));
                        builder.icon(AllDyedBlocks.DYED_PACKAGER::asStack);
                        builder.displayItems(new DisplayItemsGenerator());
                    })
            );

//    public static final RegistrySupplier<CreativeModeTab> HIDDEN_PACKAGE_TAB =
//            HIDDEN_CREATIVE_TABS.register("colored_packages", () ->
//                    CreativeTabRegistry.modifyBuiltin(CreativeModeTabs.searchTab().buildContents();)
//            );

    public static void register() {
        HIDDEN_CREATIVE_TABS.register();
//        CreativeTabRegistry.modify(HIDDEN_PACKAGE_TAB, (flags, output, canUseGameMasterBlocks) -> {
//            List<Item> items = new ReferenceArrayList<>();
//            Collection<ItemStack> itemCollection = new ArrayList<>();
//
//            for (RegistryEntry<Item> entry : ColoredPackages.REGISTRATE.getAll(Registries.ITEM)) {
//                Item item = entry.get();
//                items.add(item);
//
//                itemCollection.add(new ItemStack(item));
//            }
//
//            output.acceptAll(itemCollection, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
//        });

        CreativeTabRegistry.modifyBuiltin(CreativeModeTabs.searchTab(), (flags, output, canUseGameMasterBlocks) -> {
                        Collection<ItemStack> itemCollection = new ArrayList<>();

            for (RegistryEntry<Item> entry : ColoredPackages.REGISTRATE.getAll(Registries.ITEM)) {
                Item item = entry.get();
                itemCollection.add(new ItemStack(item));
            }

            output.acceptAll(DisplayItemsGenerator.appliedStacks, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
        });
    }
}
