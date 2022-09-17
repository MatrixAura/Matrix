package me.matrixaura.matrix.mixin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Matrix")
@IFMLLoadingPlugin.SortingIndex(value = 1001)
public class MixinLoader implements IFMLLoadingPlugin {

    static {
        ((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("config")) {
                    apply();
                }
            }
        });

        apply();
    }

    @SuppressWarnings("unchecked")
    private static void apply() {
        try {
            Interpolator interpolator = (Interpolator) ((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).getConfiguration().getStrSubstitutor().getVariableResolver();
            if (interpolator == null) return;

            boolean removed = false;

            for (Field field : Interpolator.class.getDeclaredFields()) {
                if (Map.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    removed = ((Map<String, ?>) field.get(interpolator)).remove("jndi") != null;
                    if (removed) break;
                }
            }

            if (!removed) throw new RuntimeException("couldn't find jndi lookup entry");

            System.out.println("Removed JNDI lookup");
        } catch (Throwable t) {
            t.printStackTrace();
            Runtime.getRuntime().halt(1);
            throw new RuntimeException("application failed");
        }
    }

    public static final Logger log = LogManager.getLogger("MIXIN");

    public MixinLoader() {
        log.info("Matrix mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfigurations("mixins.matrix.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        log.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
