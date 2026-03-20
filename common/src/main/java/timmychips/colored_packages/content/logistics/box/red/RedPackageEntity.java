package timmychips.colored_packages.content.logistics.box.red;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import timmychips.colored_packages.content.logistics.box.AllPackageEntityTypes;

public class RedPackageEntity extends PackageEntity {

    public RedPackageEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public RedPackageEntity(Level worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

}
