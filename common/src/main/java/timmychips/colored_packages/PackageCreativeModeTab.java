package timmychips.colored_packages;

import com.simibubi.create.AllBlocks;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import timmychips.colored_packages.content.logistics.DisplayItemsGenerator;

import java.util.function.Consumer;

public class PackageCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> HIDDEN_CREATIVE_TABS =
            DeferredRegister.create(ColoredPackages.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final CreativeModeTab.Builder TAB_BUILDER = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup." + ColoredPackages.MOD_ID + ".base"))
            .icon(AllDyedBlocks.DYED_PACKAGER::asStack)
            .displayItems(new DisplayItemsGenerator());

//    public static final RegistrySupplier<CreativeModeTab> HIDDEN_PACKAGE_TAB =
//            HIDDEN_CREATIVE_TABS.register("colored_packages", () ->
//                    CreativeTabRegistry.create(
//                            Component.translatable("itemGroup." + ColoredPackages.MOD_ID + ".base"),
//                            AllDyedBlocks.DYED_PACKAGER::asStack
//                    )
//            );
    public static final RegistrySupplier<CreativeModeTab> HIDDEN_PACKAGE_TAB =
            HIDDEN_CREATIVE_TABS.register("colored_packages", () ->
                    CreativeTabRegistry.create(builder -> {
                        builder.title(Component.translatable("itemGroup." + ColoredPackages.MOD_ID + ".base"));
                        builder.icon(AllDyedBlocks.DYED_PACKAGER::asStack);
                        builder.displayItems(new DisplayItemsGenerator());
                    })
            );

    public static void register() {
        HIDDEN_CREATIVE_TABS.register();
    }
}
