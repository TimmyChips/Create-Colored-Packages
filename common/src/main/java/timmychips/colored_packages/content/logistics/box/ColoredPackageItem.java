package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.util.ColorTooltipFormattingHelper;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class ColoredPackageItem extends PackageItem {
    public ColoredPackageItem(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        // Get color from stack, or default "Red" if it doesn't have PackageColor tag

        // Red by default if ColoredPackageItem PackageColor tag is empty
        String colorStr = "red"; // The color style to get from
        String colorLang = "Red"; // The tooltip text with capitalization and spacing

        // Set color and lang from PackageColor tag
        if (hasColorTag(pStack)) {
//            CompoundTag compound = pStack.getTag();


            String input = getCurrentColor(pStack);
            colorStr = input;
            colorLang = ColorTooltipFormattingHelper.getLangName(input);
        }

        // Add tooltip with color
        pTooltipComponents.add(Component.literal(colorLang)
                .withStyle(ColorTooltipFormattingHelper.getByName(colorStr)));
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

    public static String getCurrentColor(ItemStack packageStack) {
        CompoundTag compoundTag = packageStack.getTag();
        return compoundTag.getString(TAG_COLOR);
    }

    // Ensure proper PackageColor tag (no upper case, no special characters)
    // Try to set lowercase if it can
    @Override
    public void verifyTagAfterLoad(CompoundTag compoundTag) {
        super.verifyTagAfterLoad(compoundTag);

        if (!compoundTag.contains(TAG_COLOR)) return; // Ignore if PackageColor tag not present

        String colorStr = compoundTag.getString(TAG_COLOR);

        boolean reassignTag = false;

        // Set to lower case (if any character is uppercase)
        char[] charArray = colorStr.toCharArray();
        for (char c : charArray) {
            if (!isLowerCase(c)) {
                colorStr = colorStr.toLowerCase(Locale.ROOT);
                reassignTag = true;
                break;
            }
        }

        if (DyeColor.CODEC.byName(colorStr) == null) {
            ColoredPackages.LOGGER.warn("Could not find colored package tag string in DyeColor enum: {}", colorStr);
            colorStr = "red";
            reassignTag = true;
        }

        // Only reassign compound tag if PackageColor was uppercase, or not in DyeColor enum
        if (reassignTag) compoundTag.putString(TAG_COLOR, colorStr);
    }

    public static boolean isLowerCase(char c) {
        return Character.isLetter(c) && Character.isLowerCase(c);
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

        PackageEntity packageEntity = platformNewColoredPackage(world, point);
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

        PackageEntity packageEntity = platformNewColoredPackage(world, vec);
        packageEntity.setBox(copy);
        packageEntity.setDeltaMovement(motion);
        packageEntity.tossedBy = new WeakReference<>(player);
        world.addFreshEntity(packageEntity);
    }

    public PackageEntity platformNewColoredPackage(Level world, Vec3 point) {
        return null;
    }
}
