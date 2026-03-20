package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.ColoredPackages;

import java.util.List;

public class ColoredPackageStyles extends PackageStyles {

    public static List<PackageStyles.PackageStyle> COLORED_PACKAGE_STYLES;

    public static final PackageStyles.PackageStyle RED_PACKAGE_STYLE =
            new PackageStyles.PackageStyle("red", 12, 12, 23f, false);

    // Create's PackageStyles.PackageStyle getItemId() method always has "create" id in front, this band-aid method replaces the namespace with ours
    public static ResourceLocation getColoredItemId(PackageStyles.PackageStyle style) {
        String itemPath = style.getItemId().getPath();
        return new ResourceLocation(ColoredPackages.MOD_ID, itemPath);
    }
}
