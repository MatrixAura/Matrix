package me.matrixaura.matrix.module.modules.render;

import me.matrixaura.matrix.common.annotations.ModuleInfo;
import me.matrixaura.matrix.common.annotations.Parallel;
import me.matrixaura.matrix.module.Category;
import me.matrixaura.matrix.module.Module;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

@Parallel(runnable = true)
@ModuleInfo(name = "Brightness", category = Category.RENDER, description = "Always bright")
public class Brightness extends Module {

    @Override
    public void onTick(){
        mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 9600));
    }

}
