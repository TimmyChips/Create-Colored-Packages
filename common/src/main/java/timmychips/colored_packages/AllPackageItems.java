package timmychips.colored_packages;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.ArrayList;
import java.util.Locale;

public class AllPackageItems {

    public static ArrayList<ItemProviderEntry<?>> packageItemEntries = new ArrayList<>();

    static {
        // For constant package item type
        for (PackageStyles.PackageStyle sizeStyle : ColoredPackageStyles.STYLES) {
            ItemBuilder<?, CreateRegistrate> packageItem = coloredPackageItem(sizeStyle);

            packageItem.setData(ProviderType.LANG, NonNullBiConsumer.noop());
            ItemEntry<?> packageEntry = packageItem.register();

            packageItemEntries.add(packageEntry); // Add registered item entry to map for ModifyCreativeMenu item generator
        }
    }

    public static ItemBuilder<?, CreateRegistrate> coloredPackageItem(PackageStyles.PackageStyle style) {
                return ColoredPackages.REGISTRATE.item(style.getItemId()
                        .getPath(), p -> {
                    return getPlatformPackageItem(p, style);
                })
                    .properties(p -> p.stacksTo(1))
                    .tag(AllTags.AllItemTags.PACKAGES.tag)
                    .removeTab(CreativeModeTabs.SEARCH) // Remove packages from creative tab since we're adding it later
                    .lang((style.rare() ? "Rare"
                        : style.type()
                        .substring(0, 1)
                        .toUpperCase(Locale.ROOT)
                        + style.type()
                        .substring(1))
                        + " Package");
    }

    // Return package item per platform
    @ExpectPlatform
    public static Item getPlatformPackageItem(Item.Properties properties, PackageStyles.PackageStyle style) {
        return Items.AIR;
    }

    public static void register() {}
}
