package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages;
import timmychips.colored_packages.content.logistics.box.forge.AllPackageEntityTypesImpl;

public class RedPackageEntityForge extends PackageEntity {
    public RedPackageEntityForge(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public RedPackageEntityForge(Level worldIn, double x, double y, double z) {
        this(AllPackageEntityTypesImpl.RED_COLORED_PACKAGE_FORGE.get(), worldIn);
        this.setPos(x, y, z);
        this.refreshDimensions();
    }

    public static RedPackageEntityForge fromItemStack(Level world, Vec3 position, ItemStack itemstack) {
        RedPackageEntityForge packageEntity = AllPackageEntityTypesImpl.RED_COLORED_PACKAGE_FORGE.get()
                .create(world);
        packageEntity.setPos(position);
        packageEntity.setBox(itemstack);
        return packageEntity;
    }

    public static RedPackageEntityForge spawn(PlayMessages.SpawnEntity spawnEntity, Level world) {
        RedPackageEntityForge packageEntity =
                new RedPackageEntityForge(world, spawnEntity.getPosX(), spawnEntity.getPosY(), spawnEntity.getPosZ());
        packageEntity.setDeltaMovement(spawnEntity.getVelX(), spawnEntity.getVelY(), spawnEntity.getVelZ());
        packageEntity.clientPosition = packageEntity.position();
        return packageEntity;
    }

    public static EntityType.Builder<?> build(EntityType.Builder<?> builder) {
        @SuppressWarnings("unchecked")
        EntityType.Builder<PackageEntity> boxBuilder = (EntityType.Builder<PackageEntity>) builder;
        return boxBuilder.setCustomClientFactory(PackageEntity::spawn)
                .sized(1, 1);
    }
}
