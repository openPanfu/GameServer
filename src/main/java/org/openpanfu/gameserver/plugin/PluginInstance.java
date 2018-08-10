package org.openpanfu.gameserver.plugin;

import org.openpanfu.gameserver.util.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarFile;

public class PluginInstance {
    private String name;
    private String description;
    private Properties pluginProperties;
    private IPlugin mainClass;
    private String mainClassName;
    private JarFile jarFile;
    public Boolean listening = false;
    public PluginInstance(File file, JarFile jarFile, Properties pluginProperties) {
        this.jarFile = jarFile;
        this.pluginProperties = pluginProperties;

        String name = this.pluginProperties.getProperty("name");
        if(name != null && !name.equals("")) {
            this.name = name;
        }

        String mainClassName = this.pluginProperties.getProperty("mainclass");
        if(mainClassName != null && !mainClassName.equals("")) {
            this.mainClassName = mainClassName;
        } else {
            Logger.error(String.format("[PluginInstance] Failed to load plugin: %s. (plugin.properties->mainclass is missing)", file.getName()));
            return;
        }

        String description = this.pluginProperties.getProperty("description");
        if(description != null && !description.equals("")) {
            this.description = description;
        }
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLClassLoader cl = new URLClassLoader(new java.net.URL[] { url });
        try {
            Class<?> mainClass = cl.loadClass(this.mainClassName);
            this.mainClass = (IPlugin) mainClass.newInstance();
        } catch (ClassNotFoundException e) {
            Logger.error(String.format("[PluginInstance] Failed to load plugin: %s. (The class specified in plugin.properties->mainclass is missing)", file.getName()));
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.error(String.format("[PluginInstance] Failed to load plugin: %s. (Check trace error)", file.getName()));
            return;
        } catch (InstantiationException e) {
            e.printStackTrace();
            Logger.error(String.format("[PluginInstance] Failed to load plugin: %s. (Check trace error)", file.getName()));
            return;
        }
        this.listening = this.mainClass.onStartup();
    }

    public String getName()
    {
        return this.name;
    }

    public IPlugin getPlugin()
    {
        return this.mainClass;
    }
}
