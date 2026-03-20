package timmychips.colored_packages;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

import static timmychips.colored_packages.content.logistics.box.ColoredPackageStyle.RED_PACKAGE_STYLE;

public class AllPackageItems {

    static {

        ItemBuilder<PackageItem, CreateRegistrate> packageItem = (ColoredPackages.REGISTRATE.item(
                        new ResourceLocation(ColoredPackages.MOD_ID,"red")
                                .getPath(), p -> new PackageItem(p, RED_PACKAGE_STYLE))
                .properties(p -> p.stacksTo(1))
                .tag(AllTags.AllItemTags.PACKAGES.tag)
                .model((c, p) -> {
                    p.withExistingParent(c.getName(), p.modLoc("item/package/custom"))
                            .texture("2", p.modLoc("item/package/" + RED_PACKAGE_STYLE.type()));
                })
                .lang((RED_PACKAGE_STYLE.rare() ? "Rare"
                        : RED_PACKAGE_STYLE.type()
                        .substring(0, 1)
                        .toUpperCase(Locale.ROOT)
                        + RED_PACKAGE_STYLE.type()
                        .substring(1))
                        + " Package")
        );

        packageItem.setData(ProviderType.LANG, NonNullBiConsumer.noop());

        packageItem.register();
    }

    public static void register() {}
}
