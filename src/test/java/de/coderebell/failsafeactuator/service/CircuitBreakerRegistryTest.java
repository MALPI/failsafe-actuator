/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Malte Pickhan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.coderebell.failsafeactuator.service;

import net.jodah.failsafe.CircuitBreaker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CircuitBreakerRegistryTest {

    private static final String ID = "id";

    private CircuitBreakerRegistry registry;

    @Before
    public void init() {
        registry = new CircuitBreakerRegistry();
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullBreakerTest() {
        registry.registerCircuitBreaker(null, ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullStringTest() {
        registry.registerCircuitBreaker(new CircuitBreaker(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putEmptyStringTest() {
        registry.registerCircuitBreaker(new CircuitBreaker(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void avoidReplacingRegisteredCircuitBreakers() {
        registry.registerCircuitBreaker(new CircuitBreaker(), "ONE");
        registry.registerCircuitBreaker(new CircuitBreaker(), "TWO");
        registry.registerCircuitBreaker(new CircuitBreaker(), "ONE");
    }

    public void putBreakerOkTest() {
        final CircuitBreaker breakerUt = new CircuitBreaker();
        registry.registerCircuitBreaker(breakerUt, ID);

        assertTrue(registry.getConcurrentBreakerMap().containsKey(ID));
        final CircuitBreaker breaker = registry.getConcurrentBreakerMap().get(ID);
        assertNotNull(breaker);
        assertEquals(breakerUt, breaker);
    }
}