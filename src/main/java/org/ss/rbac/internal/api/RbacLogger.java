/*
 * The MIT License
 *
 * Copyright 2019 ss.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.ss.rbac.internal.api;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Library logger implementation.
 * @author ss
 */
public class RbacLogger implements System.Logger {
    /** Logger name. */
    private final String loggerName;
    /**
     * Constructor.
     * @param name logger name.
     */
    public RbacLogger(String name) {
        loggerName = name;
    }
    @Override
    public String getName() {
        return "RBAC";
    }
    @Override
    public boolean isLoggable(Level level) {
        return true;
    }
    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        System.out.printf("[" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date())
                + "] [%s] [%s] %s - %s%n", level, loggerName, msg, thrown);
    }
    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        System.out.printf("[" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date())
                + "] [%s] [%s] %s%n", level, loggerName, MessageFormat.format(format, params));
    }
}
