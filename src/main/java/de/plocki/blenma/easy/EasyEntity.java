package de.plocki.blenma.easy;

import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public class EasyEntity {

    public JsonObject getEntityInfo(Entity entity) {
        JsonObject entityInfo = new JsonObject();

        entityInfo.addProperty("UUID", entity.getUniqueId().toString());
        entityInfo.addProperty("Type", entity.getType().toString());
        entityInfo.addProperty("Name", entity.getName());
        entityInfo.addProperty("CustomName", entity.getName());
        entityInfo.add("Location", locationToJson(entity.getLocation()));
        entityInfo.addProperty("World", entity.getWorld().getName());
        entityInfo.add("Velocity", vectorToJson(entity.getVelocity()));
        entityInfo.addProperty("FireTicks", entity.getFireTicks());
        entityInfo.addProperty("MaxFireTicks", entity.getMaxFireTicks());
        entityInfo.addProperty("IsDead", entity.isDead());
        entityInfo.addProperty("IsOnGround", entity.isOnGround());
        entityInfo.addProperty("IsGlowing", entity.isGlowing());
        entityInfo.addProperty("IsInvulnerable", entity.isInvulnerable());
        entityInfo.addProperty("IsSilent", entity.isSilent());
        entityInfo.addProperty("IsCustomNameVisible", entity.isCustomNameVisible());

        if (entity instanceof LivingEntity livingEntity) {
            entityInfo.addProperty("Health", livingEntity.getHealth());
            entityInfo.addProperty("MaxHealth", Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            entityInfo.addProperty("Armor", Objects.requireNonNull(livingEntity.getAttribute(Attribute.GENERIC_ARMOR)).getValue());
            entityInfo.addProperty("AbsorptionAmount", livingEntity.getAbsorptionAmount());
            entityInfo.addProperty("IsAIEnabled", livingEntity.hasAI());
            entityInfo.addProperty("CanPickupItems", livingEntity.getCanPickupItems());
            entityInfo.addProperty("ArrowsInBody", livingEntity.getArrowsInBody());
            entityInfo.add("PotionEffects", potionEffectsToJson(livingEntity.getActivePotionEffects()));
            entityInfo.addProperty("Killer", livingEntity.getKiller() != null ? livingEntity.getKiller().getName() : null);
        }

        return entityInfo;
    }

    private JsonObject locationToJson(Location location) {
        JsonObject loc = new JsonObject();
        loc.addProperty("World", location.getWorld().getName());
        loc.addProperty("X", location.getX());
        loc.addProperty("Y", location.getY());
        loc.addProperty("Z", location.getZ());
        loc.addProperty("Yaw", location.getYaw());
        loc.addProperty("Pitch", location.getPitch());
        return loc;
    }

    private JsonObject vectorToJson(@NotNull Vector vector) {
        JsonObject vec = new JsonObject();
        vec.addProperty("X", vector.getX());
        vec.addProperty("Y", vector.getY());
        vec.addProperty("Z", vector.getZ());
        return vec;
    }

    private JsonObject potionEffectsToJson(@NotNull Collection<PotionEffect> effects) {
        JsonObject potionEffects = new JsonObject();
        for (PotionEffect effect : effects) {
            JsonObject effectData = new JsonObject();
            effectData.addProperty("Amplifier", effect.getAmplifier());
            effectData.addProperty("Duration", effect.getDuration());
            effectData.addProperty("IsAmbient", effect.isAmbient());
            effectData.addProperty("HasParticles", effect.hasParticles());
            potionEffects.add(effect.getType().getKey().toString(), effectData);
        }
        return potionEffects;
    }

    public void replaceEntity(Entity oldEntity, EntityType newEntity) {
        oldEntity.getLocation().getWorld().spawnEntity(oldEntity.getLocation(), newEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        oldEntity.remove();
    }

}
