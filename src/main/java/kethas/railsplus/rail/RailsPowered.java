package kethas.railsplus.rail;

import java.util.List;

import org.lwjgl.input.Keyboard;

import kethas.railsplus.RailsPlus;
import kethas.railsplus.client.IHasModel;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Kethas on 12/02/2017.
 */
public class RailsPowered extends BlockRailPowered implements IHasModel {

    public RailsPowered(String name){
        super();
        setRegistryName(name);
        setUnlocalizedName(name);
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
        return super.getRailMaxSpeed(world, cart, pos) * 2;
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
        if (!world.getBlockState(pos).getValue(BlockRailPowered.POWERED))
            return;

        double d15 = Math.sqrt(cart.motionX * cart.motionX + cart.motionZ * cart.motionZ);

        if (d15 > 0.01D)
        {
            cart.motionX += cart.motionX / d15 * 0.06D;
            cart.motionZ += cart.motionZ / d15 * 0.06D;
        }
    }

    @Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);

        tooltip.add("\u00A7cDoes not slow down minecarts when not powered");

        boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            if (!shift){
            tooltip.add("\u00A79[SHIFT] for more info");
        }else {
            tooltip.add("\u00A79Information:");
            tooltip.add("\u00A79Rail Material: Steel");
            tooltip.add("\u00A79Sleeper Material: Wood");
            tooltip.add("\u00A79Speed: 2 x Vanilla Rails");
            tooltip.add("\u00A79Bugginess: Buggy");
        }
    }
    
    @Override
	public void registerModels() {
		RailsPlus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
