package de.plocki.blenma.easy;

import de.plocki.blenma.easy.enums.SignWriteOrientation;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;

import java.util.Arrays;

public class EasySign {

    public void editSignSideText(Block block, Side orientation, String... rows) {
        Sign sign = (Sign) block;
        SignSide side = sign.getSide(orientation);
        for (String row : rows) {
            side.lines().set(Arrays.stream(rows).toList().indexOf(row), Component.text(row));
        }
    }

    public void setSignWaxed(Block block, boolean waxed) {
        Sign sign = (Sign) block;
        sign.setWaxed(waxed);
    }

    public SignSide getSignSide(Block block, Side side) {
        Sign sign = (Sign) block;
        return sign.getSide(side);
    }

    public void setSignSideClowing(Block block, Side side, DyeColor color) {
        SignSide signSide = getSignSide(block, side);

        signSide.setColor(color);
    }

    public void setSignSideClowing(Block block, Side side, boolean glowing) {
        SignSide signSide = getSignSide(block, side);

        signSide.setGlowingText(glowing);
    }

}
