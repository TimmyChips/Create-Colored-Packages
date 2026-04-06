package timmychips.colored_packages.content.logistics.box;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.logistics.box.PackageEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.Nullable;

public class ColoredPackageItem extends PackageItem {
    public ColoredPackageItem(Properties properties, PackageStyles.PackageStyle style) {
        super(properties, style);
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
}
