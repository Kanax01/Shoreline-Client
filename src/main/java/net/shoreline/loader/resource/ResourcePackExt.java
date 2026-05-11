package net.shoreline.loader.resource;

import net.fabricmc.fabric.api.resource.ModResourcePack;
import net.fabricmc.fabric.impl.resource.loader.ModNioResourcePack;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackInfo;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public final class ResourcePackExt implements ResourcePack, ModResourcePack {
    public static final Set<String> REGISTERED_SOUND_FILES = new HashSet<>();
    private final ModNioResourcePack parent;

    public ResourcePackExt(ModNioResourcePack parent) {
        this.parent = parent;
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> openRoot(String... segments) {
        return parent.openRoot(segments);
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        return parent.open(type, id);
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer consumer) {
        parent.findResources(type, namespace, prefix, consumer);
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return parent.getNamespaces(type);
    }

    @Nullable
    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return parent.parseMetadata(metaReader);
    }

    @Override
    public ResourcePackInfo getInfo() {
        return parent.getInfo();
    }

    @Override
    public ModMetadata getFabricModMetadata() {
        return parent.getFabricModMetadata();
    }

    @Override
    public ModResourcePack createOverlay(String overlay) {
        return parent.createOverlay(overlay);
    }

    @Override
    public void close() {
        parent.close();
    }
}
