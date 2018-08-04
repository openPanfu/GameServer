/**
 * This file is part of openPanfu, a project that imitates the Flex remoting
 * and gameservers of Panfu.
 *
 * @author Altro50 <altro50@msn.com>
 */

package org.openpanfu.gameserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanfuPacket
{
    private int header;
    private List<String> parameters = new ArrayList<String>();
    private int pos = 0;
    private User sender;
    private static String PAR_DELIMETER = ";";
    private static String SRV_CMD_DELIMETER = "|";

    public PanfuPacket(int Header)
    {
        this.header = Header;
    }

    public void passParameters(String[] parameters)
    {
        this.parameters.addAll(Arrays.asList(parameters));
    }

    public int getHeader()
    {
        return header;
    }

    public int getPos()
    {
        return pos;
    }

    public void setPos(int pos)
    {
        this.pos = pos;
    }

    public User getSender()
    {
        return sender;
    }

    public void setSender(User sender)
    {
        sender = sender;
    }

    public int readInt()
    {
        try {
            if(parameters.get(pos).contains(".")) {
                return Integer.valueOf(parameters.get(pos).split("\\.")[0]);
            }
            return Integer.valueOf(parameters.get(pos));
        } catch (Exception e) {
            return -1;
        } finally {
            pos++;
        }
    }

    public String readString()
    {
        try {
            return String.valueOf(parameters.get(pos));
        } catch (Exception e) {
            return "";
        } finally {
            pos++;
        }
    }

    public void writeInt(int Value)
    {
        parameters.add(String.valueOf(Value));
    }

    public void writeString(String Value)
    {
        parameters.add(Value);
    }

    public String toString()
    {
        String Packet = "";
        Packet += header;
        for (String part : parameters) {
            Packet += PAR_DELIMETER + part;
        }
        Packet += SRV_CMD_DELIMETER;
        return Packet;
    }
}
