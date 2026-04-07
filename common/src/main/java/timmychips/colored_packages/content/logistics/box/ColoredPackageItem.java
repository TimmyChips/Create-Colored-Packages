package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import timmychips.colored_packages.ColoredPackages;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class ColoredPackageItem extends PackageItem {
    public ColoredPackageItem(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);

        // Add this colored package item, and it's style to array list to later retrieve
        ColoredPackageStyles.ALL_COLORED_BOXES_CONSTANT.add(this);
    }

    public static final String TAG_COLOR = "PackageColor"; // The color tag the item will have that determines the color

    // Set tag color
    public static void setColor(ItemStack packageStack, DyeColor color) {
        CompoundTag colorTag = packageStack.getOrCreateTag();
        colorTag.putString(TAG_COLOR, color.getName());
    }

    // Check if tag contains color input
    public static boolean hasColor(ItemStack itemStack, DyeColor color) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag != null && compoundTag.getString(TAG_COLOR).equals(color.getName());
    }

    // Check if color tag is present (and not blank)
    public static boolean hasColorTag(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null) {
            String colorStr = compoundTag.getString(TAG_COLOR);
            return !colorStr.isBlank(); // Color tag isn't blank, has color string
        }
        return false;
    }

    @Override
    public Entity createEntity(Level world, Entity location, ItemStack itemstack) {
        return platformCreateEntity(world, location, itemstack);
    }

    @ExpectPlatform
    private static Entity platformCreateEntity(Level world, Entity location, ItemStack itemstack) {
        return null;
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

        platformAddEntityFromUse(context, point, world);
        return InteractionResult.SUCCESS;
    }

    // useOn method's entity creation is handled per platform
    @ExpectPlatform
    private static void platformAddEntityFromUse(UseOnContext context, Vec3 point, Level world) {}

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

        platformAddEntityFromThrown(world, vec, copy, motion, player);
    }

    @ExpectPlatform
    private static void platformAddEntityFromThrown(Level world, Vec3 vec, ItemStack copiedStack, Vec3 motion, Player player) {}

    /**
     * Creates a random colored box based on the DyedPackager block's color
     * @param stacks The handler to serialize the NBT for the box item
     * @param color The input box passed in from the DyedPackager block entity the randomly created box will be
     * @return New random colored box with the items packaged
     */
    public static ItemStack coloredContaining(NonNullList<ItemStack> stacks, Optional<DyeColor> color) {
        ItemStack box;
        if (color.isEmpty()) {
            ColoredPackages.LOGGER.warn("Dye Color on Dyed Packager isn't set yet! Defaulting to Create's packages.");
            box = PackageStyles.getRandomBox(); // get Create random box if color isn't present
        }

        else box = ColoredPackageStyles.getRandomConstantTypeBox(); // Get random box from the input color

        box = platformSerializeStacks(box, stacks);
        if (color.isPresent()) {
            setColor(box, color.get());
        }
        return box;
    }

    @ExpectPlatform
    private static ItemStack platformSerializeStacks(ItemStack box, NonNullList<ItemStack> stacks) {
        throw new AssertionError();
    }
}
