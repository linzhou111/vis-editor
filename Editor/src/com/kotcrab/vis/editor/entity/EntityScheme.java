/*
 * Copyright 2014-2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotcrab.vis.editor.entity;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.data.ECSEntityData;
import com.kotcrab.vis.runtime.util.EntityEngine;
import com.kotcrab.vis.runtime.util.UsesProtoComponent;

/** @author Kotcrab */
public class EntityScheme {
	public int id;
	public ImmutableBag<Component> components;

	public EntityScheme (Entity entity) {
		Bag<Component> components = new Bag<>();
		entity.getComponents(components);
		this.components = components;
		this.id = entity.getId();
	}

	public Entity build (EntityEngine engine) {
		EntityBuilder builder = new EntityBuilder(engine);

		components.forEach(builder::with);

		Entity entity = builder.build();
		entity.id = id;
		return entity;
	}

	public ECSEntityData toData () {
		Array<Component> dataComponents = new Array<>();

		components.forEach(component -> {
			if (component instanceof UsesProtoComponent)
				dataComponents.add(((UsesProtoComponent) component).getProtoComponent());
			else
				dataComponents.add(component);
		});

		return new ECSEntityData(dataComponents);
	}
}