package timmychips.colored_packages.neoforge.content.logistics.box;

import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.chute.ChuteBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import timmychips.colored_packages.neoforge.AllPackageEntityTypesForge;

public class ColoredPackageEntityForge extends PackageEntity implements IEntityWithComplexSpawn {

    private Entity originalEntity;
    public String model;

    public ColoredPackageEntityForge(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        box = ItemStack.EMPTY;
        setYRot(this.random.nextFloat() * 360.0F);
        setYHeadRot(getYRot());
        yRotO = getYRot();
        insertionDelay = 30;
    }

    public ColoredPackageEntityForge(Level worldIn, double x, double y, double z) {
        this(AllPackageEntityTypesForge.COLORED_PACKAGE_ENTITY_FORGE.get(), worldIn);
        this.setPos(x, y, z);
        this.refreshDimensions();
    }

    // Unused, refer to PackageEntityMixinForge.class
    public static ColoredPackageEntityForge fromItemStack(Level world, Vec3 position, ItemStack itemstack) {
        ColoredPackageEntityForge packageEntity = AllPackageEntityTypesForge.COLORED_PACKAGE_ENTITY_FORGE.get()
                .create(world);
        packageEntity.setPos(position);
        packageEntity.setBox(itemstack);
        return packageEntity;
    }

    public static EntityType.Builder<?> build(EntityType.Builder<?> builder) {
        @SuppressWarnings("unchecked")
        EntityType.Builder<ColoredPackageEntityForge> boxBuilder = (EntityType.Builder<ColoredPackageEntityForge>) builder;
        return boxBuilder.sized(1, 1);
    }

    // From dropped item
    public static ColoredPackageEntityForge fromDroppedItem(Level world, Entity originalEntity, ItemStack itemstack) {
        ColoredPackageEntityForge packageEntity = AllPackageEntityTypesForge.COLORED_PACKAGE_ENTITY_FORGE.get()
                .create(world);

        Vec3 position = originalEntity.position();
        packageEntity.setPos(position);
        packageEntity.setBox(itemstack);
        packageEntity.setDeltaMovement(originalEntity.getDeltaMovement()
                .scale(1.5f));
        packageEntity.originalEntity = originalEntity;

        if (world != null && !world.isClientSide)
            if (ChuteBlock.isChute(world.getBlockState(BlockPos.containing(position.x, position.y + .5f, position.z))))
                packageEntity.setYRot(((int) packageEntity.getYRot()) / 90 * 90);

        return packageEntity;
    }

    /*
     * Forge created package entities even when an ItemEntity is spawned as 'fake'.
     * See: GiveCommand#giveItem. This method discards the package if it originated
     * from such a fake item
     */
    // COPIED FROM CREATE
    @Override
    protected void verifyInitialEntity() {
        if (!(originalEntity instanceof ItemEntity itemEntity))
            return;
        CompoundTag nbt = new CompoundTag();
        itemEntity.addAdditionalSaveData(nbt);
        if (nbt.getInt("PickupDelay") != 32767) // See: ItemEntity#makeFakeItem
            return;
        discard();
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        ItemStack.STREAM_CODEC.encode(buffer, getBox());
        Vec3 motion = getDeltaMovement();
        buffer.writeFloat((float) motion.x);
        buffer.writeFloat((float) motion.y);
        buffer.writeFloat((float) motion.z);
    }

    public static AttributeSupplier.Builder createPackageAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5f)
                .add(Attributes.MOVEMENT_SPEED, 1f);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        box = ItemStack.parseOptional(level().registryAccess(), compound.getCompound("Box"));
        refreshDimensions();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Box", box.saveOptional(level().registryAccess()));
    }
}
