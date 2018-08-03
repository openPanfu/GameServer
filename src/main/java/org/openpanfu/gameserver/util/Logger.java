/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver.util;

import org.openpanfu.gameserver.GameServer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void log(String Level, String Message, String colorCode)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if(Integer.valueOf(GameServer.getProperties().getProperty("gameserver.ansilogging")) != 0)
            System.out.println(String.format("%s[%s] %s > %s%s", colorCode, sdf.format(cal.getTime()).toString(), Level, Message, ANSI_RESET));
        else
            System.out.println(String.format("[%s] %s > %s", sdf.format(cal.getTime()).toString(), Level, Message));
    }
    public static void info(String Message)
    {
        log("INFO", Message, ANSI_CYAN);
    }
    public static void debug(String Message)
    {
        log("DEBUG", Message, ANSI_PURPLE);
    }
    public static void warning(String Message)
    {
        log("WARN", Message, ANSI_YELLOW);
    }
    public static void error(String Message)
    {
        log("ERROR", Message, ANSI_RED);
    }
}
