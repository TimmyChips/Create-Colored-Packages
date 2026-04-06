package timmychips.colored_packages.forge.content.logistics.box;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;

public class ColoredPackageItemForge extends ColoredPackageItem {

//    private static DyeColor color;

    public ColoredPackageItemForge(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);

        // Add this colored package item, and it's style to array list to later retrieve
        ColoredPackageStyles.ALL_COLORED_BOXES_CONSTANT.add(this);
    }

    // For when package item is dropped with Q
    @Override
    public Entity createEntity(Level world, Entity location, ItemStack itemstack) {
        return RedPackageEntityForge.fromDroppedItem(world, location, itemstack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer().isShiftKeyDown()) {
            return open(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        }

        Vec3 point = context.getClickLocation();
        float h = style.height() / 16f;
        float r = style.width() / 2f / 16f;

        if (context.getClickedFace() == Direction.DOWN)
            point = point.subtract(0, h + .25f, 0);
        else if (context.getClickedFace()
                .getAxis()
                .isHorizontal())
            point = point.add(Vec3.atLowerCornerOf(context.getClickedFace()
                            .getNormal())
                    .scale(r));

        AABB scanBB = new AABB(point, point).inflate(r, 0, r)
                .expandTowards(0, h, 0);
        Level world = context.getLevel();
        if (!world.getEntities(AllEntityTypes.PACKAGE.get(), scanBB, e -> true)
                .isEmpty())
            return super.useOn(context);

        RedPackageEntityForge packageEntity = new RedPackageEntityForge(world, point.x, point.y, point.z);;
        ItemStack itemInHand = context.getItemInHand();
        packageEntity.setBox(itemInHand.copy());
        world.addFreshEntity(packageEntity);
        itemInHand.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int ticks) {
        if (!(entity instanceof Player player))
            return;
        int i = this.getUseDuration(stack) - ticks;
        if (i < 0)
            return;

        float f = getPackageVelocity(i);
        if (f < 0.1D)
            return;
        if (world.isClientSide)
            return;

        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL, 0.5F, 0.5F);

        ItemStack copy = stack.copy();
        if (!player.getAbilities().instabuild)
            stack.shrink(1);

        Vec3 vec = new Vec3(entity.getX(), entity.getY() + entity.getBoundingBox()
                .getYsize() / 2f, entity.getZ());
        Vec3 motion = entity.getLookAngle()
                .scale(f * 2);
        vec = vec.add(motion);

        RedPackageEntityForge packageEntity = new RedPackageEntityForge(world, vec.x, vec.y, vec.z);
        packageEntity.setBox(copy);
        packageEntity.setDeltaMovement(motion);
        packageEntity.tossedBy = new WeakReference<>(player);
        world.addFreshEntity(packageEntity);
    }

    /**
     * Creates a random colored box based on the DyedPackager block's color
     * @param stacks The handler to serialize the NBT for the box item
     * @param color The input box passed in from the DyedPackager block entity the randomly created box will be
     * @return New random colored box with the items packaged
     */
    public static ItemStack coloredContaining(ItemStackHandler stacks, Optional<DyeColor> color) {
        ItemStack box;
        if (color.isEmpty()) {
            ColoredPackages.LOGGER.warn("Dye Color on Dyed Packager isn't set yet! Defaulting to Create's packages.");
            box = PackageStyles.getRandomBox(); // get Create random box if color isn't present
        }
//        else box = ColoredPackageStyles.getRandomColoredBox(color.get()); // Get random colored box from the input color

        else box = ColoredPackageStyles.getRandomConstantTypeBox(); // Get random box from the input color

        CompoundTag compound = new CompoundTag();
        compound.put("Items", stacks.serializeNBT());
        box.setTag(compound);
        if (color.isPresent()) {
            setColor(box, color.get());
        }
        return box;
    }

    // For repacking
    public static ItemStack coloredContaining(List<ItemStack> stacks, Optional<DyeColor> color) {
        ItemStackHandler newInv = new ItemStackHandler(9);
        stacks.forEach(s -> ItemHandlerHelper.insertItemStacked(newInv, s, false));
        return coloredContaining(newInv, color);
    }
}
