package org.openpanfu.gameserver.plugin;

import org.openpanfu.gameserver.User;
import org.openpanfu.gameserver.util.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarFile;

public class PluginManager {
	private static List<PluginInstance> plugins = new ArrayList<PluginInstance>();

	public static void loadPlugins(String directory) {
		PluginLoader.loadPluginsInDirectory(directory);
	}

	public static void loadPlugin(File file, JarFile jarFile, Properties pluginProps) {
		PluginInstance pi = new PluginInstance(file, jarFile, pluginProps);
		plugins.add(pi);
	}

	// Functions to run on plugins

	// On functions (Run after doing something)

	public static void onUserJoinRoom(User user, int roomId, boolean isHome) {
		for (PluginInstance plugin : plugins) {
			if (plugin.listening)
				plugin.getPlugin().onUserJoinRoom(user, roomId, isHome);
		}
	}

	public static void onUserConnect(User user) {
		for (PluginInstance plugin : plugins) {
			if (plugin.listening)
				plugin.getPlugin().onUserConnect(user);
		}
	}

	public static void onUserChat(User user, String message) {
		for (PluginInstance plugin : plugins) {
			if (plugin.listening)
				plugin.getPlugin().onUserChat(user, message);
		}
	}

	// Handle functions (Run before doing something and can stop normal behaviour)
	public static boolean handleUserJoinRoom(User user, int roomId, boolean isHome) {
		for (PluginInstance plugin : plugins) {
			if (plugin.listening && !plugin.getPlugin().handleUserJoinRoom(user, roomId, isHome)) {
				Logger.debug("Exiting in handleUserJoinRoom due to " + plugin.getName());
				return false;
			}
		}
		return true;
	}

	public static boolean handleUserConnect(User user) {
		for (PluginInstance plugin : plugins) {
			if (plugin.listening && !plugin.getPlugin().handleUserConnect(user)) {
				return false;
			}
		}
		return true;
	}

	public static boolean handleUserChat(User user, String message) {
		for (PluginInstance plugin : plugins) {
			if (plugin.listening && !plugin.getPlugin().handleUserChat(user, message)) {
				Logger.debug("Exiting in handleUserChat due to " + plugin.getName());
				return false;
			}
		}
		return true;
	}

}
