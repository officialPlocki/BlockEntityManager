package de.plocki.blenma.easy;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Piston;

public class EasyPiston {

    public void setPistonExtended(Block block, boolean extended) {
        Piston piston = (Piston) block;
        piston.setExtended(extended);
    }

    public void rotatePiston(Block block, BlockFace newDirection) {
        Directional directional = (Directional) block.getBlockData();
        directional.setFacing(newDirection);
        block.setBlockData(directional);
    }

    public void setPowered(Block block, boolean powered) {
        Powerable powerable = (Powerable) block.getBlockData();
        powerable.setPowered(powered);
        block.setBlockData(powerable);
    }

}
