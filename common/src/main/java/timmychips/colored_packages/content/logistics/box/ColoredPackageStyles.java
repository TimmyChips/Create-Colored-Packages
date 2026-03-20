package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.tterrag.registrate.fabric.RegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import timmychips.colored_packages.ColoredPackages;

import java.util.List;

public class ColoredPackageStyle extends PackageStyles {

    public static List<PackageStyles.PackageStyle> COLORED_PACKAGE_STYLES;

    public static final PackageStyles.PackageStyle RED_PACKAGE_STYLE =
            new PackageStyles.PackageStyle("red", 12, 12, 23f, false);


    public class PackageStyle implements PackageStyles.PackageStyle {
        @Override
        public ResourceLocation getItemId() {
            String size = "_" + width + "x" + height;
            String id = super.type + "_package" + (rare ? "" : size);
            return ColoredPackages.asResource(id);
        }
    }
}
