package net.shoreline.loader.session;

import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public final class UserSession {
    private final String hardwareId;
    private final String username;
    private final String uid;
    private final String usertype;
    private final List<String> runningMods;

    public UserSession(String hardwareId, String username, String uid, String usertype, List<String> runningMods) {
        this.hardwareId = hardwareId;
        this.username = username;
        this.uid = uid;
        this.usertype = usertype;
        this.runningMods = runningMods;
    }

    public String getHardwareID() {
        return hardwareId;
    }

    public String getUsername() {
        return username;
    }

    public String getUID() {
        return uid;
    }

    public String getUserType() {
        return usertype;
    }

    public List<String> getRunningMods() {
        return runningMods;
    }

    public static UserSession load() {
        List<String> loadedMods = FabricLoader.getInstance().getAllMods().stream()
                .map(mod -> mod.getMetadata().getName())
                .filter(mod -> !mod.contains("Fabric"))
                .toList();

        return new UserSession(
                "offline",
                System.getProperty("user.name", "player"),
                "0",
                "release",
                loadedMods
        );
    }
}
