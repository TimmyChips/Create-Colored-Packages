package timmychips.colored_packages.content.logistics.box;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.equipment.bell.BasicParticleData;
import com.simibubi.create.content.equipment.bell.SoulParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timmychips.colored_packages.AllPackagePartialModels;
import timmychips.colored_packages.AllPackageParticles;
import timmychips.colored_packages.ColoredPackages;

public class ColoredPackageParticle extends BreakingItemParticle {

    /// The particle behavior and rendering
    ColoredPackageParticle (ClientLevel arg, double d, double e, double f, double g, double h, double i, ItemStack arg2) {
        this(arg, d, e, f, arg2);
        this.xd *= (double)0.1F;
        this.yd *= (double)0.1F;
        this.zd *= (double)0.1F;
        this.xd += g;
        this.yd += h;
        this.zd += i;
    }

    protected ColoredPackageParticle(ClientLevel clientLevel, double d, double e, double f, ItemStack itemStack) {
        super(clientLevel, d, e, f, itemStack);

        // Get the particle sprite from the partial model of the package's current color
        this.setSprite(AllPackagePartialModels.coloredPartialFromColor(itemStack,
                ColoredPackageItem.getCurrentColor(itemStack)).get().getParticleIcon());
    }

    /// The particle provider
    public static class Provider implements ParticleProvider<ItemParticleOption> {

        @Override
        public @Nullable Particle createParticle(ItemParticleOption type, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ColoredPackageParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, type.getItem());
        }
    }
}
