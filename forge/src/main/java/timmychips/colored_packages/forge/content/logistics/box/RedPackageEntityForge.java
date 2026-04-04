package timmychips.colored_packages.forge.content.logistics.box;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.chute.ChuteBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.PlayMessages;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.forge.AllPackageEntityTypesForge;

public class RedPackageEntityForge extends PackageEntity implements IEntityAdditionalSpawnData {

    private Entity originalEntity;
    public String model;

    public RedPackageEntityForge(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        box = ItemStack.EMPTY;
        setYRot(this.random.nextFloat() * 360.0F);
        setYHeadRot(getYRot());
        yRotO = getYRot();
        insertionDelay = 30;
    }

    public RedPackageEntityForge(EntityType<?> entityType, Level world, double x, double y, double z, String model) {
        super(entityType, world);
        this.setPos(x, y, z);
        this.model = model;
        this.refreshDimensions();
    }

    public RedPackageEntityForge(Level worldIn, double x, double y, double z) {
        this(AllPackageEntityTypesForge.RED_COLORED_PACKAGE_FORGE.get(), worldIn);
        this.setPos(x, y, z);
        this.refreshDimensions();
    }

    // Unused, refer to PackageEntityMixinForge.class
    public static RedPackageEntityForge fromItemStack(Level world, Vec3 position, ItemStack itemstack) {
        RedPackageEntityForge packageEntity = AllPackageEntityTypesForge.RED_COLORED_PACKAGE_FORGE.get()
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

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.setPos(this.getX(), this.getY(), this.getZ());
    }

    public static EntityType.Builder<?> build(EntityType.Builder<?> builder) {
        @SuppressWarnings("unchecked")
        EntityType.Builder<RedPackageEntityForge> boxBuilder = (EntityType.Builder<RedPackageEntityForge>) builder;
        return boxBuilder.setCustomClientFactory(RedPackageEntityForge::spawn)
                .sized(1, 1);
    }

    // From dropped item
    public static RedPackageEntityForge fromDroppedItem(Level world, Entity originalEntity, ItemStack itemstack) {
        RedPackageEntityForge packageEntity = AllPackageEntityTypesForge.RED_COLORED_PACKAGE_FORGE.get()
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
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeItem(getBox());
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
        box = ItemStack.of(compound.getCompound("Box"));
        ColoredPackages.LOGGER.info("read box: {}", box); // Always air item
        refreshDimensions();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Box", box.serializeNBT()); // Always air item
    }
}
