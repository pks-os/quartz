/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 * Copyright Super iPaaS Integration LLC, an IBM Company 2024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.quartz.utils;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for PropertiesParser.
 */
public class PropertiesParserTest  {

    /**
     * Unit test for full getPropertyGroup() method.
     */
    @Test
    void testGetPropertyGroupStringBooleanStringArray() {
        // Test that an empty property does not cause an exception
        Properties props = new Properties();
        props.put("x.y.z", "");
        
        PropertiesParser propertiesParser = new PropertiesParser(props);
        Properties propGroup = propertiesParser.getPropertyGroup("x.y", true, new String[] {});
        assertEquals("", propGroup.getProperty("z"));
    }
}
