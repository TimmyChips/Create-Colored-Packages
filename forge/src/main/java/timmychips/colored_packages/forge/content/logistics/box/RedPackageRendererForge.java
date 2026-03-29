package timmychips.colored_packages.forge.content.logistics.box;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

public class RedPackageRendererForge extends EntityRenderer<RedPackageEntityForge> {

    public RedPackageRendererForge(EntityRendererProvider.Context pContext) {
        super(pContext);
        shadowRadius = 0.5f;
    }

    @Override
    public void render(RedPackageEntityForge entity, float yaw, float pt, PoseStack ms, MultiBufferSource buffer, int light) {
        if (!VisualizationManager.supportsVisualization(entity.level())) {
            ItemStack box = entity.box;
            if (box.isEmpty() || !PackageItem.isPackage(box)) box = AllBlocks.CARDBOARD_BLOCK.asStack();

            ColoredPackages.LOGGER.info("test!!");

            PartialModel model;
            CompoundTag compoundTag = box.getTag();
            ColoredPackages.LOGGER.info("compound tag: {}", box.getTag().getString(ColoredPackageItem.TAG_COLOR));
            if (compoundTag != null) {
                String color = compoundTag.getString(ColoredPackageItem.TAG_COLOR);
                model = AllPackagePartialModels.coloredPartialFromColor(box, color);
            }
            else model = AllPartialModels.PACKAGES.get(ForgeRegistries.ITEMS.getKey(box.getItem()));

            ColoredPackages.LOGGER.info("Partial model for render: {}", model);

            PackageRenderer.renderBox(entity, yaw, ms, buffer, light, model);
        }
        super.render(entity, yaw, pt, ms, buffer, light);
    }

    // Not needed, just return new null ResourceLocation to avoid error/warning
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RedPackageEntityForge pEntity) {
        return ColoredPackages.asResource("null");
    }
}
