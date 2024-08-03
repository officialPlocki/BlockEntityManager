package de.plocki.blenma.easy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;

public class EasyBlock {

    private final Gson gson;

    public EasyBlock() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void rotateBlock(Block block, BlockFace newFacing) {
        BlockData blockData = block.getBlockData();

        if (blockData instanceof Directional directional) {
            directional.setFacing(newFacing);

            block.setBlockData(directional);
        } else if (blockData instanceof Rotatable rotatable) {
            rotatable.setRotation(newFacing);

            block.setBlockData(rotatable);
        }
    }

    public void rotateBlock(Block block, Axis newAxis) {
        BlockData blockData = block.getBlockData();

        if (blockData instanceof Orientable orientable) {
            orientable.setAxis(newAxis);

            block.setBlockData(orientable);
        }
    }

    public String getBlockInformation(Location location) {
        Block block = location.getBlock();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("type", block.getType().name());

        BlockData blockData = block.getBlockData();
        jsonObject.add("blockData", blockDataToJson(block));

        JsonObject locationObject = new JsonObject();
        locationObject.addProperty("world", location.getWorld().getName());
        locationObject.addProperty("x", location.getX());
        locationObject.addProperty("y", location.getY());
        locationObject.addProperty("z", location.getZ());
        jsonObject.add("location", locationObject);

        jsonObject = addChecks(block, blockData, jsonObject);

        return gson.toJson(jsonObject);
    }

    //und ja, diese methode ist mit chatgpt gemacht, war zu faul die properties einzeln zu schreiben xd
    public JsonObject addChecks(Block block, BlockData blockData, JsonObject jsonObject) {
        if (blockData instanceof Waterlogged) {
            jsonObject.addProperty("waterlogged", ((Waterlogged) blockData).isWaterlogged());
        }

        // Check for Ageable interface
        if (blockData instanceof Ageable) {
            jsonObject.addProperty("age", ((Ageable) blockData).getAge());
        }

        // Check for AnaloguePowerable interface
        if (blockData instanceof AnaloguePowerable) {
            jsonObject.addProperty("power", ((AnaloguePowerable) blockData).getPower());
        }

        // Check for Attachable interface
        if (blockData instanceof Attachable) {
            jsonObject.addProperty("attached", ((Attachable) blockData).isAttached());
        }

        // Check for Bisected interface
        if (blockData instanceof Bisected) {
            jsonObject.addProperty("half", ((Bisected) blockData).getHalf().name());
        }

        // Check for Directional interface
        if (blockData instanceof Directional) {
            jsonObject.addProperty("facing", ((Directional) blockData).getFacing().name());
        }

        // Check for FaceAttachable interface
        if (blockData instanceof FaceAttachable) {
            jsonObject.addProperty("attachedFace", ((FaceAttachable) blockData).getAttachedFace().name());
        }

        // Check for Levelled interface
        if (blockData instanceof Levelled) {
            jsonObject.addProperty("level", ((Levelled) blockData).getLevel());
        }

        // Check for Lightable interface
        if (blockData instanceof Lightable) {
            jsonObject.addProperty("lit", ((Lightable) blockData).isLit());
        }

        // Check for MultipleFacing interface
        if (blockData instanceof MultipleFacing multipleFacing) {
            JsonObject faces = new JsonObject();
            for (org.bukkit.block.BlockFace face : multipleFacing.getFaces()) {
                faces.addProperty(face.name().toLowerCase(), true);
            }
            jsonObject.add("faces", faces);
        }

        // Check for Openable interface
        if (blockData instanceof Openable) {
            jsonObject.addProperty("open", ((Openable) blockData).isOpen());
        }

        // Check for Orientable interface
        if (blockData instanceof Orientable) {
            jsonObject.addProperty("axis", ((Orientable) blockData).getAxis().name());
        }

        // Check for Powerable interface
        if (blockData instanceof Powerable) {
            jsonObject.addProperty("powered", ((Powerable) blockData).isPowered());
        }

        // Check for Rail interface
        if (blockData instanceof Rail) {
            jsonObject.addProperty("shape", ((Rail) blockData).getShape().name());
        }

        // Check for RedstoneRail interface
        if (blockData instanceof RedstoneRail) {
            jsonObject.addProperty("poweredRail", ((RedstoneRail) blockData).isPowered());
        }

        // Check for Rotatable interface
        if (blockData instanceof Rotatable) {
            jsonObject.addProperty("rotation", ((Rotatable) blockData).getRotation().name());
        }

        // Check for SculkSensor interface
        if (blockData instanceof SculkSensor) {
            jsonObject.addProperty("sculkSensorPhase", ((SculkSensor) blockData).getPhase().name());
        }

        // Check for Snowable interface
        if (blockData instanceof Snowable) {
            jsonObject.addProperty("snowy", ((Snowable) blockData).isSnowy());
        }

        // Check for Switch interface
        if (blockData instanceof Switch) {
            jsonObject.addProperty("faceAttached", ((Switch) blockData).getAttachedFace().name());
        }

        // Check for TNT interface
        if (blockData instanceof TNT) {
            jsonObject.addProperty("unstableTNT", ((TNT) blockData).isUnstable());
        }

        // Check for TechnicalPiston interface
        if (blockData instanceof TechnicalPiston) {
            jsonObject.addProperty("pistonType", ((TechnicalPiston) blockData).getType().name());
            jsonObject.addProperty("extendedPiston", ((Piston) block).isExtended());
        }

        // Check for TrapDoor interface
        if (blockData instanceof TrapDoor) {
            jsonObject.addProperty("half", ((TrapDoor) blockData).getHalf().name());
            jsonObject.addProperty("open", ((TrapDoor) blockData).isOpen());
        }

        // Check for Waterlogged interface
        if (blockData instanceof Waterlogged) {
            jsonObject.addProperty("waterlogged", ((Waterlogged) blockData).isWaterlogged());
        }

        // Check for WallSign interface
        if (blockData instanceof WallSign) {
            jsonObject.addProperty("facingSign", ((WallSign) blockData).getFacing().name());
        }

        return jsonObject;
    }

    public JsonObject blockDataToJson(Block block) {
        JsonObject jsonObject = new JsonObject();

        BlockData blockData = block.getBlockData();
        String dataString = blockData.getAsString();

        int bracketIndex = dataString.indexOf('[');
        String type = bracketIndex != -1 ? dataString.substring(0, bracketIndex) : dataString;
        String properties = bracketIndex != -1 ? dataString.substring(bracketIndex + 1, dataString.length() - 1) : "";

        jsonObject.addProperty("type", type);

        JsonObject propertiesJson = new JsonObject();
        if (!properties.isEmpty()) {
            String[] propertyPairs = properties.split(",");
            for (String pair : propertyPairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    propertiesJson.addProperty(keyValue[0], keyValue[1]);
                }
            }
        }

        jsonObject.add("properties", propertiesJson);

        return jsonObject;
    }

}
