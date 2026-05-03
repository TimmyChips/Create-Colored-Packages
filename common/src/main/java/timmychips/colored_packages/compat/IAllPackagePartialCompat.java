package timmychips.colored_packages.compat;

import com.ninni.dye_depot.registry.DDDyes;
import net.minecraft.world.item.DyeColor;

public interface IAllPackagePartialCompat {
    static void dyeDepotPartialCompat(DyeColor color) {
        if (DDDyes.isModDye(color)) {

        }
    }
}
