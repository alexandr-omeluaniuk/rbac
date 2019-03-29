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

module ss.rbac.test {
    requires ss.rbac;
    requires com.h2database;
    requires org.hibernate.orm.core;
    requires org.hibernate.validator;
    requires junit;
    requires com.sun.xml.fastinfoset;
    requires antlr;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires dom4j;
    requires hamcrest.core;
    requires org.hibernate.commons.annotations;
    requires com.sun.istack.runtime;
    requires jandex;
    requires javassist;
    requires java.activation;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires org.jboss.logging;
    requires java.transaction;
    requires org.jvnet.staxex;
    requires com.sun.xml.txw2;
    
    exports org.ss.rbac.test;
    
    opens org.ss.rbac.test.entity;
    
    uses org.ss.rbac.configuration.EntityManagerProvider;
    
    provides org.ss.rbac.configuration.UserProvider 
            with org.ss.rbac.test.api.impl.UserProviderImpl;
    provides org.ss.rbac.configuration.EntityManagerProvider 
            with org.ss.rbac.test.api.impl.EntityManagerProviderImpl;
}
