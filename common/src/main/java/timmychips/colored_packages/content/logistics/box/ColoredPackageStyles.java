package timmychips.colored_packages.content.logistics.box;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.ColoredPackages;

import java.util.ArrayList;
import java.util.List;

public class ColoredPackageStyles extends PackageStyles {

    public static final List<PackageStyle> COLORED_STYLES = ImmutableList.of(
            new PackageStyle("red", 12, 12, 23f, false),
            new PackageStyle("red", 12, 10, 22f, false),
            new PackageStyle("lime", 12, 12, 23f, false)
    );

    public static final List<PackageItem> ALL_COLORED_BOXES = new ArrayList<>();

    public static final List<PackageStyle> RED_STYLES = ImmutableList.of(
            new PackageStyle("red", 12, 12, 23f, false)
    );

    public static final PackageStyles.PackageStyle RED_PACKAGE_STYLE =
            new PackageStyles.PackageStyle("red", 12, 12, 23f, false);

    // Create's PackageStyles.PackageStyle getItemId() method always has "create" id in front, this band-aid method replaces the namespace with ours
    public static ResourceLocation getColoredItemId(PackageStyles.PackageStyle style) {
        String itemPath = style.getItemId().getPath();
        return new ResourceLocation(ColoredPackages.MOD_ID, itemPath);
    }

    @ExpectPlatform
    public static ItemStack getRandomColoredBox(DyeColor color) {
        return ItemStack.EMPTY;
    }

//    public static void init() {
//        COLORED_STYLES.add(RED_PACKAGE_STYLE);
//    }
}
