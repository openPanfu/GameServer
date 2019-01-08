package org.openpanfu.gameserver.plugin;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openpanfu.gameserver.util.Logger;

public class PluginLoader {
	private static final Pattern fileFilter = Pattern.compile("\\.jar$");

	public static void loadPluginsInDirectory(String directoryToSearch) {
		Logger.info(String.format("[PluginLoader] Loading plugins from ./%s..", directoryToSearch));
		final File directory = new File(directoryToSearch);
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				Matcher match = fileFilter.matcher(file.getName());
				if (match.find()) {
					// This is a proper jar file, time to load.
					try {
						JarFile jarFile = loadJar(file);
						if (jarFile.getEntry("plugin.properties") != null) {
							Properties pluginProperties = new Properties();
							pluginProperties.load(jarFile.getInputStream(jarFile.getEntry("plugin.properties")));
							PluginManager.loadPlugin(file, jarFile, pluginProperties);
						} else {
							Logger.error(String.format(
									"[PluginLoader] Failed to load plugin: %s. (plugin.properties is missing from the jar file)",
									file.getName()));
						}
					} catch (IOException e) {
						e.printStackTrace();
						Logger.error(String.format("[PluginLoader] Failed to load plugin: %s. (Check trace)",
								file.getName()));
					}
				}
			}
		} else {
			Logger.error("[PluginLoader] Failed to load plugins. (Plugin is not a directory)");
		}
	}

	public static JarFile loadJar(String jarFile) {
		try {
			return loadJar(new File(jarFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.error(String.format("[PluginLoader] Failed to load plugin %s..", jarFile));
		return null;
	}

	public static JarFile loadJar(File jarFile) throws IOException {
		return new JarFile(jarFile);
	}
}
