package timmychips.colored_packages.content.logistics.box.forge;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import timmychips.colored_packages.forge.content.logistics.box.RedPackageEntityForge;

public class ColoredPackageItemImpl extends PackageItem {

    public ColoredPackageItemImpl(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);
    }

    public static Object ColoredPackageEntity(Level world, Vec3 point) {
        return new RedPackageEntityForge(world, point.x, point.y, point.z);
    }
}
