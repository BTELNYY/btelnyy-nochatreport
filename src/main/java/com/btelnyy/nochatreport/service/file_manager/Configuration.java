package com.btelnyy.nochatreport.service.file_manager;

import com.btelnyy.nochatreport.NoChatReport;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Configuration
{
    private static final Plugin instance = NoChatReport.getInstance();
    private final ConfigurationSection config;
    private boolean log = true;

    public Configuration(ConfigurationSection config)
    {
        this.config = config;
    }

    public Object get(String path)
    {
        return get(path, null);
    }

    public Object get(String path, Object defaultValue)
    {
        if (config.contains(path))
        {
            return config.get(path);
        }
        invalid(path);
        return defaultValue;
    }

    public long getLong(String path)
    {
        return getLong(path, 0);
    }

    public long getLong(String path, long defaultValue)
    {
        if (config.contains(path))
        {
            return config.getLong(path);
        }
        invalid(path);
        return defaultValue;
    }

    public double getDouble(String path)
    {
        return getDouble(path, 0);
    }

    public double getDouble(String path, double defaultValue)
    {
        if (config.contains(path))
        {
            return config.getDouble(path);
        }
        invalid(path);
        return defaultValue;
    }

    public int getInt(String path)
    {
        return getInteger(path);
    }

    public int getInt(String path, int defaultValue)
    {
        return getInteger(path, defaultValue);
    }

    public int getInteger(String path)
    {
        return getInteger(path, 0);
    }

    public int getInteger(String path, int defaultValue)
    {
        if (config.contains(path))
        {
            return config.getInt(path);
        }
        invalid(path);
        return defaultValue;
    }

    public String getString(String path)
    {
        return getString(path, "Error");
    }

    public String getString(String path, String defaultValue)
    {
        if (config.contains(path))
        {
            return config.getString(path);
        }
        invalid(path);
        return defaultValue;
    }

    public boolean getBoolean(String path)
    {
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean defaultValue)
    {
        if (config.contains(path))
        {
            return config.getBoolean(path);
        }
        invalid(path);
        return defaultValue;
    }

    public List<String> getStringList(String path)
    {
        return getStringList(path, new ArrayList<>());
    }

    public List<String> getStringList(String path, List<String> defaultValue)
    {
        if (config.contains(path))
        {
            return config.getStringList(path);
        }
        invalid(path);
        return defaultValue;
    }

    public Configuration getConfiguration(String path)
    {
        if (config.contains(path) && config.isConfigurationSection(path))
        {
            return new Configuration(config.getConfigurationSection(path));
        }
        invalid(path);
        return new Configuration(config.createSection(path));
    }

    public ItemStack getItemStack(String path)
    {
        if (config.contains(path))
        {
            return config.getItemStack(path);
        }
        invalid(path);
        return new ItemStack(Material.STONE);
    }

    public Configuration set(String path, Object o)
    {
        config.set(path, o);
        return this;
    }

    public ConfigurationSection self()
    {
        return config;
    }

    public void setLog(boolean log)
    {
        this.log = log;
    }

    public Set<String> getKeys()
    {
        return config.getKeys(false);
    }

    private void invalid(String path)
    {
        if (!log)
        {
            return;
        }
        String message = String.format("There is a missing value (%s > %s)", Objects.requireNonNull(self().getCurrentPath()).replace(".", " > "), path.replace(".", " > "));
        instance.getLogger().warning(message);
    }
}