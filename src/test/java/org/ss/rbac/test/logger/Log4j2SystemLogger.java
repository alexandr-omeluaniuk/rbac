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
package org.ss.rbac.test.logger;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Log4j2 system logger.
 * @author ss
 */
public class Log4j2SystemLogger implements System.Logger {
    /** Logger name. */
    private Logger logger = null;
    /**
     * Constructor.
     * @param name logger name.
     */
    public Log4j2SystemLogger(String name) {
        logger = LogManager.getLogger(name);
    }
    @Override
    public String getName() {
        return logger.getName();
    }
    @Override
    public boolean isLoggable(Level level) {
        return true;
    }
    @Override
    public void log(Level level, String msg) {
        this.logger.log(convertLevel(level), msg);
    }
    @Override
    public void log(Level level, Object obj) {
        this.logger.log(convertLevel(level), obj);
    }
    @Override
    public void log(Level level, String format, Object... params) {
        this.logger.log(convertLevel(level), MessageFormat.format(format, params));
    }
    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        this.logger.log(convertLevel(level), msg, thrown);
    }
    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        this.logger.log(convertLevel(level), MessageFormat.format(format, params));
    }
    private org.apache.logging.log4j.Level convertLevel(Level level) {
        if (Level.ALL == level) {
            return org.apache.logging.log4j.Level.ALL;
        } else if (Level.DEBUG == level) {
            return org.apache.logging.log4j.Level.DEBUG;
        } else if (Level.ERROR == level) {
            return org.apache.logging.log4j.Level.ERROR;
        } else if (Level.INFO == level) {
            return org.apache.logging.log4j.Level.INFO;
        } else if (Level.OFF == level) {
            return org.apache.logging.log4j.Level.OFF;
        } else if (Level.TRACE == level) {
            return org.apache.logging.log4j.Level.TRACE;
        } else if (Level.WARNING == level) {
            return org.apache.logging.log4j.Level.WARN;
        } else {
            return null;
        }
    }
}
