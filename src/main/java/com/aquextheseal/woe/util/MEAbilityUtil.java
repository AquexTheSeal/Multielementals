package com.aquextheseal.woe.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MEAbilityUtil {

    @Nullable
    public static Entity getRayTracedEntity(Entity caster, double range) {
        double distance = range * range;
        Vec3 vec = caster.getEyePosition(1);
        Vec3 vec1 = caster.getViewVector(1);
        Vec3 targetVec = vec.add(vec1.x * range, vec1.y * range, vec1.z * range);
        AABB aabb = caster.getBoundingBox().expandTowards(vec1.scale(range)).inflate(4.0D, 4.0D, 4.0D);
        EntityHitResult result = ProjectileUtil.getEntityHitResult(caster, vec, targetVec, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR, distance);
        if (result != null) {
            return result.getEntity();
        } else {
            return null;
        }
    }
}
