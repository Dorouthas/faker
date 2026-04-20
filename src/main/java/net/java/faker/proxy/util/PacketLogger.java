/*
 * This file is part of faker - https://github.com/o1seth/faker
 * Copyright (C) 2024 o1seth
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.java.faker.proxy.util;

import net.java.faker.Proxy;
import net.java.faker.save.Config;
import net.java.faker.util.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PacketLogger {
    private static BufferedWriter writer;

    public static synchronized void start() {
        if (!Proxy.getConfig().logPackets.get()) {
            return;
        }
        if (writer != null) {
            stop();
        }
        try {
            writer = new BufferedWriter(new FileWriter(new File(Proxy.getFakerDirectory(), "packets.log")));
        } catch (Exception e) {
            Logger.error("Failed to start logging packets", e);
        }

    }

    public static synchronized void stop() {
        if (writer == null) {
            return;
        }
        try {
            writer.close();
            writer = null;
        } catch (Exception e) {
            Logger.error("Failed to stop logging packets", e);
        }

    }

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

    public static synchronized void logOut(String msg) {
        if (writer == null) {
            return;
        }
        try {
            writer.append(formatter.format(LocalTime.now()));
            writer.append(" OUT ");
            writer.append(msg);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void log(String msg) {
        if (writer == null) {
            return;
        }
        try {
            writer.append(formatter.format(LocalTime.now()));
            writer.append(msg);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void logIn(String msg) {
        if (writer == null) {
            return;
        }
        try {
            writer.append(formatter.format(LocalTime.now()));
            writer.append(" IN ");
            writer.append(msg);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
