package timmychips.colored_packages.content.logistics.box.forge;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.List;
import java.util.Random;

public class ColoredPackageStylesImpl {
    private static final Random STYLE_PICKER = new Random();
    private static final int RARE_CHANCE = 7500;



    public static ItemStack getRandomColoredBox(DyeColor color) {

        // Get list of PackageStyle for specific color
//        List<PackageStyles.PackageStyle> colorStyle = ColoredPackageStyles.RED_STYLES.stream()
//                .filter(style -> style.type().equals(color.getName()))
//                .toList();

        ColoredPackages.LOGGER.info("DyeColor color: {}", color);

        // Get list of PackageStyle for specific color
        List<? extends PackageItem> coloredPackages = ColoredPackageStyles.ALL_COLORED_BOXES.stream()
                .filter(packageItem -> packageItem.style.type().equals(color.getName()))
                .toList();

        ColoredPackages.LOGGER.info("coloredPackages list for this color: {}", coloredPackages);

//        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
//                PackageStyles.RARE_BOXES : ColoredPackageStyles.RED_STYLES;
//        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size())));

        List<? extends PackageItem> pool = STYLE_PICKER.nextInt(RARE_CHANCE) == 0 ?
                PackageStyles.RARE_BOXES : coloredPackages;
        ColoredPackages.LOGGER.info("returned random item stack box: {}", new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size()))));
        return new ItemStack(pool.get(STYLE_PICKER.nextInt(pool.size())));
    }
}
