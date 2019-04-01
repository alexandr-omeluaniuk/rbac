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
package org.ss.rbac.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import org.ss.rbac.exception.ServiceNotFoundException;

/**
 * Service provider.
 * @author ss
 */
public class ServiceProvider {
    /**
     * Load service.
     * @param <T> service type.
     * @param clazz service class.
     * @return service singleton.
     */
    public static <T> T load(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        Iterator<T> itr = serviceLoader.iterator();
        while (itr.hasNext()) {
            return itr.next();
        }
        throw new ServiceNotFoundException(
                "Service implementation is not found for: " + clazz.getName());
    }
    /**
     * Get all service implementations.
     * @param <T> service type.
     * @param clazz service class.
     * @return all service implementations.
     */
    public static <T> List<T> loadAll(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        Iterator<T> itr = serviceLoader.iterator();
        while (itr.hasNext()) {
            result.add(itr.next());
        }
        return result;
    }
}
