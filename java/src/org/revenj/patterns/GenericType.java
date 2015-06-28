package org.revenj.patterns;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class GenericType<T> {

	public final Type type;

	protected GenericType() {
		Type superclass = getClass().getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
	}

	public <T> T resolve(ServiceLocator locator) {
		Optional<Object> found = locator.tryResolve(type);
		if (!found.isPresent()) {
			throw new NoSuchElementException(type.getTypeName());
		}
		return (T)found.get();
	}
}