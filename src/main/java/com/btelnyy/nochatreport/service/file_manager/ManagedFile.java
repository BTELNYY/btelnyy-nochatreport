package com.btelnyy.nochatreport.service.file_manager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ManagedFile
{
    private File file;
    private FileConfiguration fileConfiguration;
    private Configuration configuration;

    ManagedFile()
    {
        fileConfiguration = new YamlConfiguration();
    }

    ManagedFile(File file)
    {
        setFile(file);
        updateFileConfiguration();
    }

    /**
     * Returns the file, can be null
     *
     * @return Existing file
     */
    public File getFile()
    {
        return file;
    }

    /**
     * Set the existing file, will require a updateFileConfiguration() to update the file configuration
     */
    public void setFile(File file)
    {
        this.file = file;
    }

    /**
     * Returns the file configuration as a Configuration class which is a safe & reporting class, can not be null though the file configuration in the Configuration can be
     *
     * @return File configuration
     */
    public Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Set the existing file configuration to the given value, is not recommended
     */
    public void setFileConfiguration(FileConfiguration fileConfiguration)
    {
        this.fileConfiguration = fileConfiguration;
    }

    /**
     * Update the file configuration based on the existing file
     */
    public void updateFileConfiguration()
    {
        loadFile(file);
        configuration = new Configuration(fileConfiguration);
    }

    /**
     * Save the file
     */
    public void save()
    {
        try
        {
            fileConfiguration.save(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Clears everything that the file contains
     * Essentialy making it an empty file
     */

    public void clear()
    {
        fileConfiguration = new YamlConfiguration();
        save();
    }

    /**
     * Deletes the file
     * Both from the hard drive and the RAM
     */
    public void delete()
    {
        file.delete();
        file = null;
        fileConfiguration = null;
    }

    /**
     * Load the given file to the existing file configuration
     */
    public void loadFile(File file)
    {
        fileConfiguration = new YamlConfiguration();
        try
        {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }
}
