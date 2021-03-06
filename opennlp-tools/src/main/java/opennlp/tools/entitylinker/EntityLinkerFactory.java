/*
 * Copyright 2013 The Apache Software Foundation.
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
package opennlp.tools.entitylinker;

import opennlp.tools.util.ext.ExtensionLoader;

/**
 * Generates an EntityLinker implementation via properties file configuration
 *
 */
public class EntityLinkerFactory {

  /**
   *
   * @param <I>        A type that extends EntityLinkerProperties.
   * @param entityType The type of entity being linked to. This value is used to
   *                   retrieve the implementation of the entitylinker from the
   *                   entitylinker properties file.
   * @param properties An object that extends EntityLinkerProperties. This
   *                   object will be passed into the implemented EntityLinker
   *                   init(..) method, so it is an appropriate place to put additional resources.
   * @return an EntityLinker impl
   */
  public static synchronized EntityLinker<?> getLinker(String entityType, EntityLinkerProperties properties) throws Exception {
    if (entityType == null || properties == null) {
      throw new IllegalArgumentException("Null argument in entityLinkerFactory");
    }
    
    String linkerImplFullName = properties.getProperty("linker." + entityType, "");
    
    if (linkerImplFullName == null) {
      throw new IllegalArgumentException("linker." + entityType + "  property must be set!");
    }
    
    EntityLinker<?> linker = ExtensionLoader.instantiateExtension(EntityLinker.class, linkerImplFullName);
    linker.init(properties);
    return linker;
  }
}
