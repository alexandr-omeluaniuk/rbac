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
package org.ss.rbac.constant;

/**
 * Access decision voter parameter.
 * @author ss
 */
public enum AccessDecisionVoterParam {
    /** Principal type. */
    PRINCIPAL_TYPE(PrincipalType.class, true),
    /** Principal ID. */
    PRINCIPAL_ID(Long.class, true),
    /** Entity class. */
    ENTITY_CLASS(Class.class, false);
    /** Parameter type. */
    private final Class type;
    /** Is required. */
    private final boolean required;
    /**
     * Constructor.
     * @param type parameter type.
     */
    private AccessDecisionVoterParam(Class type, boolean required) {
        this.type = type;
        this.required = required;
    }
    /**
     * Get parameter type.
     * @return parameter type.
     */
    public Class getType() {
        return type;
    }
    /**
     * Is required for voter?
     * @return true if required.
     */
    public boolean isRequired() {
        return required;
    }
}
