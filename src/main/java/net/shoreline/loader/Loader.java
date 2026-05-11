package net.shoreline.loader;

import net.fabricmc.api.ClientModInitializer;
import net.shoreline.client.Shoreline;
import net.shoreline.loader.session.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import javax.swing.JOptionPane;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class Loader implements ClientModInitializer, IMixinConfigPlugin {
    private static final Logger LOGGER = LogManager.getLogger("Shoreline");

    public static final UserSession SESSION = UserSession.load();

    @Override
    public void onInitializeClient() {
        Shoreline.init();
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    public static void info(String message) {
        LOGGER.info("[Shoreline] {}", message);
    }

    public static void info(String message, Object... params) {
        LOGGER.info("[Shoreline] " + message, params);
    }

    public static void error(String message) {
        LOGGER.error("[Shoreline] {}", message);
    }

    public static void error(String message, Object... params) {
        LOGGER.error("[Shoreline] " + message, params);
    }

    public static Object showErrorWindow(Object message) {
        try {
            JOptionPane.showMessageDialog(null, String.valueOf(message), "Shoreline", JOptionPane.ERROR_MESSAGE);
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static InputStream getResource(String name) {
        return Loader.class.getClassLoader().getResourceAsStream(name);
    }
}
