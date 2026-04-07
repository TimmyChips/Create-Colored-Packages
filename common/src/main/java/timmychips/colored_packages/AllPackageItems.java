package timmychips.colored_packages;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.Locale;

import static timmychips.colored_packages.ColoredPackages.LOGGER;
import static timmychips.colored_packages.content.logistics.box.ColoredPackageStyles.PACKAGE_STYLE_SIZES;

public class AllPackageItems {

    static {

        LOGGER.info("Registering packages");

//        for (PackageStyles.PackageStyle style : ColoredPackageStyles.COLORED_STYLES) {
//
////        ItemBuilder<PackageItem, CreateRegistrate> packageItem = packageItem(RED_PACKAGE_STYLE);
////        ItemBuilder<ColoredPackageItem, CreateRegistrate> packageItem = coloredPackageItem(RED_PACKAGE_STYLE);
//            ItemBuilder<?, CreateRegistrate> packageItem = coloredPackageItem(style);
//
//            packageItem.setData(ProviderType.LANG, NonNullBiConsumer.noop());
//
//            packageItem.register();
//        }

        // For constant package item type
        for (PackageStyles.PackageStyle sizeStyle : ColoredPackageStyles.STYLES) {
            ItemBuilder<?, CreateRegistrate> packageItem = coloredPackageItemNew(sizeStyle);

            packageItem.setData(ProviderType.LANG, NonNullBiConsumer.noop());
            packageItem.register();
        }
    }

    public static ItemBuilder<PackageItem, CreateRegistrate> packageItem(PackageStyles.PackageStyle style) {
        String size = "_" + style.width() + "x" + style.height();
        return ColoredPackages.REGISTRATE.item(style.getItemId()
                        .getPath(), p -> new PackageItem(p, style))
                .properties(p -> p.stacksTo(1))
                .tag(AllTags.AllItemTags.PACKAGES.tag)
                .model((c, p) -> {
//                    if (style.rare())
//                        p.withExistingParent(c.getName(), p.modLoc("item/package/custom" + size))
//                                .texture("2", p.modLoc("item/package/" + style.type()));
//                    else {
//                        p.withExistingParent(c.getName(), "create:models/item/package/cardboard" + size)
//                                .texture("2", "create:textures/item/package/" + "cardboard");
//                        LOGGER.info("TEST!!!");
//                    }
                    LOGGER.info("TEST");
                })
                .lang((style.rare() ? "Rare"
                        : style.type()
                        .substring(0, 1)
                        .toUpperCase(Locale.ROOT)
                        + style.type()
                        .substring(1))
                        + " Package");
    }

    public static ItemBuilder<?, CreateRegistrate> coloredPackageItemNew(PackageStyles.PackageStyle style) {
                return ColoredPackages.REGISTRATE.item(style.getItemId()
                        .getPath(), p -> {
                    return new ColoredPackageItem(p, style);
                })
                .properties(p -> p.stacksTo(1))
                .tag(AllTags.AllItemTags.PACKAGES.tag)
                .lang((style.rare() ? "Rare"
                        : style.type()
                        .substring(0, 1)
                        .toUpperCase(Locale.ROOT)
                        + style.type()
                        .substring(1))
                        + " Package");
    }

    // Return package item per platform
//    @ExpectPlatform
//    public static Item getPlatformPackageItem(Item.Properties properties, PackageStyles.PackageStyle style) {
//        return Items.AIR;
//    }

    public static void register() {}
}
