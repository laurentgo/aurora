/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.aurora.scheduler.storage.mem;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.twitter.common.inject.Bindings.KeyFactory;

import org.apache.aurora.scheduler.storage.CronJobStore;
import org.apache.aurora.scheduler.storage.TaskStore;

import static java.util.Objects.requireNonNull;

/**
 * Binding module for in-memory stores.
 * <p>
 * NOTE: These stores are being phased out in favor of database-backed stores.
 */
public final class InMemStoresModule extends AbstractModule {

  private final KeyFactory keyFactory;

  public InMemStoresModule(KeyFactory keyFactory) {
    this.keyFactory = requireNonNull(keyFactory);
  }

  private <T> void bindStore(Class<T> binding, Class<? extends T> impl) {
    bind(binding).to(impl);
    bind(impl).in(Singleton.class);
    Key<T> key = keyFactory.create(binding);
    bind(key).to(impl);
  }

  @Override
  protected void configure() {
    bindStore(CronJobStore.Mutable.class, MemJobStore.class);
    bindStore(TaskStore.Mutable.class, MemTaskStore.class);
  }
}
