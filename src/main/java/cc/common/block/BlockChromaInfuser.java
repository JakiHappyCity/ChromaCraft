package cc.common.block;

import cc.common.mod.CCCore;
import cc.common.tile.TileChromaInfuser;
import cc.utils.MiscUtils;
import cc.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class BlockChromaInfuser extends BlockContainer {

    public BlockChromaInfuser(Material p_i45394_1_) {
        super(p_i45394_1_);
    }

    public BlockChromaInfuser() {
        super(Material.rock);
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 0;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getRenderType()
    {
        return 2634;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int metadata) {
        // TODO Auto-generated method stub
        return new TileChromaInfuser();
    }

    @Override
    public int onBlockPlaced(World w, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return 0;
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (par1World.isRemote)
        {
            return true;
        }else
        {
            if(!par5EntityPlayer.isSneaking())
            {
                par5EntityPlayer.openGui(CCCore.instance, Config.guiID[0], par1World, par2, par3, par4);
                return true;
            }else
            {
                return false;
            }
        }
    }

}
