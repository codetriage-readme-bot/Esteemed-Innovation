package flaxbeard.steamcraft.item;

import flaxbeard.steamcraft.Steamcraft;
import flaxbeard.steamcraft.api.SteamcraftRegistry;
import flaxbeard.steamcraft.gui.GuiSteamcraftBook;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSteamcraftBook extends Item {
    public ItemSteamcraftBook() {
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        player.openGui(Steamcraft.instance, 1, world, 0, 0, 0);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking() && world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            RayTraceResult rtr = new RayTraceResult(new Vec3d(hitX, hitY, hitZ), side, pos);
            ItemStack stack = state.getBlock().getPickBlock(state, rtr, world, pos, player);

            //noinspection ConstantConditions
            if (stack != null) {
                for (ItemStack stack2 : SteamcraftRegistry.bookRecipes.keySet()) {
                    if (stack2.getItem() == stack.getItem() && stack2.getItemDamage() == stack.getItemDamage()) {
                        GuiSteamcraftBook.openRecipeFor(stack2, player);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }

        return EnumActionResult.FAIL;
    }
}
