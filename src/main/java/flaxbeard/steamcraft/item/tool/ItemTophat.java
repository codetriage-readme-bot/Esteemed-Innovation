package flaxbeard.steamcraft.item.tool;

import flaxbeard.steamcraft.api.exosuit.ExosuitSlot;
import flaxbeard.steamcraft.api.exosuit.IExosuitUpgrade;
import flaxbeard.steamcraft.api.exosuit.ModelExosuitUpgrade;
import flaxbeard.steamcraft.client.render.model.ModelTophat;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTophat extends ItemArmor implements IExosuitUpgrade {

    private static ModelTophat modelTophat;

    boolean emerald;

    public ItemTophat(ArmorMaterial armorMaterial, int renderIndex, int armorType, boolean isEmerald) {
        super(armorMaterial, renderIndex, armorType);
        emerald = isEmerald;
    }

    public void onCreated(ItemStack me, World world, EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if ("Flaxbeard".equals(name) || "ForgeDevName".equals(name)) {
            me.setTagCompound(new NBTTagCompound());
            me.getTagCompound().setBoolean("Flaxbeard", true);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack me) {
        if (me.hasTagCompound() && me.getTagCompound().hasKey("Flaxbeard")) {
            return EnumRarity.EPIC;
        }
        return super.getRarity(me);
    }

    @Override
    public String getUnlocalizedName(ItemStack me) {
        if (me.hasTagCompound() && me.getTagCompound().hasKey("Flaxbeard")) {
            return "item.steamcraft:kek";
        }
        return super.getUnlocalizedName(me);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return emerald ? "steamcraft:textures/models/armor/tophatemerald.png" : "steamcraft:textures/models/armor/tophat.png";
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return par2ItemStack.isItemEqual(new ItemStack(Items.LEATHER)) || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public int renderPriority() {
        return 0;
    }

    @Override
    public ExosuitSlot getSlot() {
        return ExosuitSlot.HEAD_HELM;
    }

    @Override
    public ResourceLocation getOverlay() {
        return null;
    }

    @Override
    public Class<? extends ModelExosuitUpgrade> getModel() {
        return null;
    }

    @Override
    public void updateModel(ModelBiped parentModel, EntityLivingBase entityLivingBase, ItemStack itemStack, ModelExosuitUpgrade modelExosuitUpgrade) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        if (modelTophat == null)
            modelTophat = new ModelTophat();

        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("level")) {
            modelTophat.level = itemStack.getTagCompound().getInteger("level");
        }

        return modelTophat;
    }

    @Override
    public void writeInfo(List list) {

    }

    @Override
    public void addInformation(ItemStack me, EntityPlayer player, List<String> list, boolean par4) {
        super.addInformation(me, player, list, par4);
        if (me.hasTagCompound()) {
            if (me.getTagCompound().hasKey("level")) {
                int level = me.getTagCompound().getInteger("level");
                // TODO: Proper string formatting.
                list.add(TextFormatting.GREEN + I18n.format("steamcraft.exosuit.level") + " " + level);
            }
        }
    }
}
