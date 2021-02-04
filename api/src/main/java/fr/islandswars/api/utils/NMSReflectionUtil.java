package fr.islandswars.api.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;

/**
 * File <b>NMSReflectionUtil</b> located on fr.islandswars.api.utils
 * NMSReflectionUtil is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2021 Islands Wars.
 * <p>
 * islands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author SkyBeast https://github.com/SkyBeastMC/NMSProtocol/blob/master/src/main/java/me/skybeast/nmsprotocol/NMSReflection.java
 * Created the 04/02/2021 at 17:14
 * @since 0.1
 */
public class NMSReflectionUtil {

	/**
	 * A String that represents the CraftBukkit package.
	 */
	public static final String CB = Bukkit.getServer().getClass().getPackage().getName();

	/**
	 * A String that represents the NMS-Package version of the server.
	 */
	public static final String VERSION = CB.substring(23);

	/**
	 * A String that represents the NMS package.
	 */
	public static final String NMS = "net.minecraft.server." + VERSION;

	private static final Class[]  EMPTY_CLASS_ARRAY  = new Class[0];
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	private NMSReflectionUtil() {
	}

	/**
	 * Invoke a method in a non-static way.
	 *
	 * @param instance the instance
	 * @param method   the name of the method to call
	 * @param argsType the types of the arguments
	 * @param args     the arguments
	 * @param <T>      method call
	 * @return the result of dispatching the method or <code>null</code> if the return packet is void
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T callMethod(@Nonnull Object instance, @Nonnull String method, @Nonnull Class<?>[] argsType, @Nonnull Object[] args) {
		return methodCall(findMethod(instance.getClass(), method, argsType), instance, args);
	}

	/**
	 * Invoke a method in a static way.
	 *
	 * @param clazz    the class where the method is
	 * @param method   the name of the method to call
	 * @param argsType the types of the arguments
	 * @param args     the arguments
	 * @param <T>      method call
	 * @return the result of dispatching the method or <code>null</code> if the return packet is void
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T callStaticMethod(@Nonnull Class<?> clazz, @Nonnull String method, @Nonnull Class<?>[] argsType, @Nonnull Object[] args) {
		return methodCall(findMethod(clazz, method, argsType), null, args);
	}

	/**
	 * Invoke a method in a static way. This method resolves the class.
	 *
	 * @param clazz    a string representing the clazz
	 * @param method   the name of the method to call
	 * @param argsType the types of the arguments
	 * @param args     the arguments
	 * @param <T>      method call
	 * @return the result of dispatching the method or <code>null</code> if the return packet is void
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T callStaticMethod(@Nonnull String clazz, @Nonnull String method, @Nonnull Class<?>[] argsType, @Nonnull Object[] args) {
		return methodCall(findMethod(getClass(clazz), method, argsType), null, args);
	}

	/**
	 * <a href="http://stackoverflow.com/questions/195321/why-is-class-newinstance-evil">Evil</a>
	 *
	 * @param clazz the class to instanciate
	 * @param <T>   instance class
	 * @return the new instance
	 * @throws ReflectionException if ANY exception is thrown
	 */
	@SuppressWarnings({"ClassNewInstance", "OverlyBroadCatchBlock"})
	public static <T> T evilNewInstance(@Nonnull Class<T> clazz) {
		return classNewInstance(clazz);
	}

	/**
	 * <a href="http://stackoverflow.com/questions/195321/why-is-class-newinstance-evil">Evil</a> This method resolves
	 * the class.
	 *
	 * @param clazz a string representing the clazz
	 * @param <T>   instance class
	 * @return the new instance
	 * @throws ReflectionException if ANY exception is thrown
	 */
	public static <T> T evilNewInstance(@Nonnull String clazz) {
		return classNewInstance(getClass(clazz));
	}

	/**
	 * Get a class from its name. Replace <code>{nms}</code> to {@link #NMS}, <code>{cb}</code> to {@link #CB} and
	 * <code>{version}</code> to {@link #VERSION}
	 *
	 * @param clazz the class to resolve
	 * @param <T>   field type
	 * @return a ConstructorAccessor holding the Constructor
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	@SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
	public static <T> Class<T> getClass(@Nonnull String clazz) {
		return findClass(clazz.replace("{nms}", NMS).replace("{cb}", CB).replace("{version}", VERSION));
	}

	/**
	 * Get a ConstructorAccessor by its class and parameters.
	 *
	 * @param clazz the class of the constructor
	 * @param args  the parameters of the constructor
	 * @param <T>   field type
	 * @return a ConstructorAccessor holding the Constructor
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> ConstructorAccessor<T> getConstructorAccessor(@Nonnull Class<T> clazz, @Nonnull Class<?>... args) {
		return getConstructorAccessor(findConstructor(clazz, args));
	}

	/**
	 * Get a ConstructorAccessor by its class and parameters. This method resolves the clazz.
	 *
	 * @param clazz a string representing the clazz
	 * @param args  the parameters of the constructor
	 * @param <T>   field type
	 * @return a ConstructorAccessor holding the Constructor
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	@SuppressWarnings("unchecked")
	public static <T> ConstructorAccessor<T> getConstructorAccessor(@Nonnull String clazz, @Nonnull Class<?>... args) {
		return (ConstructorAccessor<T>) getConstructorAccessor(findConstructor(getClass(clazz), args));
	}

	/**
	 * Get a ConstructorAccessor by its Constructor.
	 *
	 * @param constructor the constructor to hook to
	 * @param <T>         field type
	 * @return a ConstructorAccessor holding the Constructor
	 */
	public static <T> ConstructorAccessor<T> getConstructorAccessor(@Nonnull Constructor<T> constructor) {
		constructor.setAccessible(true); // Disable Accessible check -- Faster
		return () -> constructor;
	}

	/**
	 * Find a field accessor by its clazz, packet and place in the clazz.
	 *
	 * @param clazz the class of the field
	 * @param type  the packet of the field
	 * @param count the place of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getCountFieldOfTypeAccessor(@Nonnull Class<?> clazz, @Nonnull Class<?> type, int count) {
		return getFieldAccessor(countFieldOfType(clazz, type, count));
	}

	/**
	 * Find a field accessor by its clazz, packet and place in the clazz. This method resolves the packet.
	 *
	 * @param clazz the class of the field
	 * @param type  a string representing the packet of the field
	 * @param count the place of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getCountFieldOfTypeAccessor(@Nonnull Class<?> clazz, @Nonnull String type, int count) {
		return getCountFieldOfTypeAccessor(clazz, getClass(type), count);
	}

	/**
	 * Find a field accessor by its clazz, packet and place in the clazz. This method resolves the class.
	 *
	 * @param clazz a string representing the class
	 * @param type  the class of the field
	 * @param count the place of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getCountFieldOfTypeAccessor(@Nonnull String clazz, @Nonnull Class<?> type, int count) {
		return getCountFieldOfTypeAccessor(getClass(clazz), type, count);
	}

	/**
	 * Find a field accessor by its clazz, packet and place in the clazz. This method resolves the classes.
	 *
	 * @param clazz a string representing the class
	 * @param type  a string representing the packet of the field
	 * @param count the place of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getCountFieldOfTypeAccessor(@Nonnull String clazz, @Nonnull String type, int count) {
		return getCountFieldOfTypeAccessor(getClass(clazz), getClass(type), count);
	}

	/**
	 * Get the label of the xth field with packet <code>packet</code>.
	 *
	 * @param instance the instance
	 * @param type     a string representing the packet of the field
	 * @param count    x
	 * @param <T>      field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> T getCountValueOfType(@Nonnull Object instance, @Nonnull Class<?> type, int count) {
		return fieldGet(countFieldOfType(instance.getClass(), type, count), instance);
	}

	/**
	 * Get the label of the xth field with packet <code>packet</code>. This method resolves the class.
	 *
	 * @param instance the instance
	 * @param type     a string representing the packet of the field
	 * @param count    x
	 * @param <T>      field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> T getCountValueOfType(@Nonnull Object instance, @Nonnull String type, int count) {
		return fieldGet(countFieldOfType(instance.getClass(), getClass(type), count), instance);
	}

	/**
	 * Get a FieldAccessor by its class and name.
	 *
	 * @param clazz the class of the field
	 * @param name  the name of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> FieldAccessor<T> getFieldAccessor(@Nonnull Class<?> clazz, @Nonnull String name) {
		return getFieldAccessor(findField(clazz, name));
	}

	/**
	 * Get a FieldAccessor by its class and name. This method resolves the clazz.
	 *
	 * @param clazz a string representing the clazz
	 * @param name  the name of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> FieldAccessor<T> getFieldAccessor(@Nonnull String clazz, @Nonnull String name) {
		return getFieldAccessor(findField(getClass(clazz), name));
	}

	/**
	 * Get a FieldAccessor by its Field.
	 *
	 * @param field the field to hook to
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 */
	public static <T> FieldAccessor<T> getFieldAccessor(@Nonnull Field field) {
		field.setAccessible(true); // Disable Accessible check -- Faster
		return () -> field;
	}

	/**
	 * Find a field accessor by its clazz and packet. Return the first found.
	 *
	 * @param clazz the class of the field
	 * @param type  the packet of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getFirstFieldOfTypeAccessor(@Nonnull Class<?> clazz, @Nonnull Class<?> type) {
		return getCountFieldOfTypeAccessor(clazz, type, 0);
	}

	/**
	 * Find a field accessor by its clazz and packet. Return the first found. This method resolves the packet.
	 *
	 * @param clazz the class of the field
	 * @param type  a string representing the packet of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getFirstFieldOfTypeAccessor(@Nonnull Class<?> clazz, @Nonnull String type) {
		return getCountFieldOfTypeAccessor(clazz, getClass(type), 0);
	}

	/**
	 * Find a field accessor by its clazz and packet. Return the first found. This method resolves the class.
	 *
	 * @param clazz a string representing the class
	 * @param type  the class of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getFirstFieldOfTypeAccessor(@Nonnull String clazz, @Nonnull Class<?> type) {
		return getCountFieldOfTypeAccessor(getClass(clazz), type, 0);
	}

	/**
	 * Find a field accessor by its clazz and packet. Return the first found. This method resolves the classes.
	 *
	 * @param clazz a string representing the class
	 * @param type  a string representing the packet of the field
	 * @param <T>   field type
	 * @return a FieldAccessor holding the Field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> FieldAccessor<T> getFirstFieldOfTypeAccessor(@Nonnull String clazz, @Nonnull String type) {
		return getCountFieldOfTypeAccessor(getClass(clazz), getClass(type), 0);
	}

	/**
	 * Get the label of a field by its packet. Return the first found.
	 *
	 * @param instance the instance
	 * @param type     the packet of the field
	 * @param <T>      field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> T getFirstValueOfType(@Nonnull Object instance, @Nonnull Class<?> type) {
		return fieldGet(countFieldOfType(instance.getClass(), type, 0), instance);
	}

	/**
	 * Get the label of a field by its packet. Return the first found. This method resolves the class.
	 *
	 * @param instance the instance
	 * @param type     a string representing the packet of the field
	 * @param <T>      field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static <T> T getFirstValueOfType(@Nonnull Object instance, @Nonnull String type) {
		return fieldGet(countFieldOfType(instance.getClass(), getClass(type), 0), instance);
	}

	/**
	 * Get a MethodAccessor by its class, name and parameters.
	 *
	 * @param clazz the class of the method
	 * @param name  the name of the method
	 * @param args  the parameters of the method
	 * @param <T>   field type
	 * @return a MethodAccessor holding the Method
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> MethodAccessor<T> getMethodAccessor(@Nonnull Class<?> clazz, @Nonnull String name, @Nonnull Class<?>... args) {
		return getMethodAccessor(findMethod(clazz, name, args));
	}

	/**
	 * Get a MethodAccessor by its class, name and parameters. This method resolves the clazz.
	 *
	 * @param clazz a string representing the clazz
	 * @param name  the name of the method
	 * @param args  the parameters of the method
	 * @param <T>   field type
	 * @return a MethodAccessor holding the Method
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> MethodAccessor<T> getMethodAccessor(@Nonnull String clazz, @Nonnull String name, @Nonnull Class<?>... args) {
		return getMethodAccessor(findMethod(getClass(clazz), name, args));
	}

	/**
	 * Get a MethodAccessor by its Method.
	 *
	 * @param method the method to hook to
	 * @param <T>    field type
	 * @return a MethodAccessor holding the Method
	 */
	public static <T> MethodAccessor<T> getMethodAccessor(@Nonnull Method method) {
		method.setAccessible(true); // Disable Accessible check -- Faster
		return () -> method;
	}

	/**
	 * Get the name of the package of a class.
	 *
	 * @param clazz the class
	 * @return the name of the package
	 */
	public static String getPackage(@Nonnull String clazz) {
		int index = clazz.lastIndexOf('.');
		return index > 0 ? clazz.substring(0, index) : ""; //Empty string if default package
	}

	/**
	 * Get the label of a field in a static way.
	 *
	 * @param clazz the class where the field is
	 * @param field the name of the field
	 * @param <T>   field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T getStaticValue(@Nonnull Class<?> clazz, @Nonnull String field) {
		return fieldGet(findField(clazz, field), null);
	}

	/**
	 * Get the label of a field in a static way. This method resolves the class.
	 *
	 * @param clazz a string representing the clazz
	 * @param field the name of the field
	 * @param <T>   field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T getStaticValue(@Nonnull String clazz, @Nonnull String field) {
		return fieldGet(findField(getClass(clazz), field), null);
	}

	/**
	 * Get the label of a field in a non-static way.
	 *
	 * @param instance the instance
	 * @param field    the name of the field
	 * @param <T>      field label
	 * @return the label of the field
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T getValue(@Nonnull Object instance, @Nonnull String field) {
		return fieldGet(findField(instance.getClass(), field), instance);
	}

	/**
	 * Call a constructor.
	 *
	 * @param clazz    the class where the constructor is
	 * @param argsType the args packet of the constructor
	 * @param args     the args of the constructor
	 * @param <T>      instance class
	 * @return the new instance
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T newInstance(@Nonnull Class<T> clazz, @Nonnull Class<?>[] argsType, @Nonnull Object[] args) {
		return constructorNewInstance(findConstructor(clazz, argsType), args);
	}

	/**
	 * Call a constructor. This method resolves the class.
	 *
	 * @param clazz    a string representing the clazz
	 * @param argsType the args packet of the constructor
	 * @param args     the args of the constructor
	 * @param <T>      instance class
	 * @return the new instance
	 * @throws ReflectionException if any exception is thrown
	 */
	public static <T> T newInstance(@Nonnull String clazz, @Nonnull Class<?>[] argsType, @Nonnull Object[] args) {
		return constructorNewInstance(findConstructor(getClass(clazz), argsType), args);
	}

	/**
	 * Find a constructor with no arg and call it.
	 *
	 * @param clazz the class where the constructor is
	 * @param <T>   instance class
	 * @return the new instance
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T newInstance(@Nonnull Class<T> clazz) {
		return constructorNewInstance(findConstructor(clazz, EMPTY_CLASS_ARRAY), EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Find a constructor with no arg and call it. This method resolves the class.
	 *
	 * @param clazz a string representing the clazz
	 * @param <T>   instance class
	 * @return the new instance
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static <T> T newInstance(@Nonnull String clazz) {
		return constructorNewInstance(findConstructor(getClass(clazz), EMPTY_CLASS_ARRAY), EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Set the label of a field in a non-static way.
	 *
	 * @param instance the instance
	 * @param field    the name of the field
	 * @param value    the label to set
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static void setField(@Nonnull Object instance, @Nonnull String field, @Nonnull Object value) {
		fieldSet(findField(instance.getClass(), field), instance, value);
	}

	/**
	 * Get the label of a field by its packet. Return the first found.
	 *
	 * @param instance the instance
	 * @param type     the packet of the field
	 * @param value    the label to set
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static void setFirstValueOfType(@Nonnull Object instance, @Nonnull Class<?> type, @Nonnull Object value) {
		fieldSet(countFieldOfType(instance.getClass(), type, 0), instance, value);
	}

	/**
	 * Get the label of a field by its packet. Return the first found. This method resolves the class.
	 *
	 * @param instance the instance
	 * @param type     a string representing the packet of the field
	 * @param value    the label to set
	 * @throws ReflectionException if any non-runtime exception is thrown or if the field was not found
	 */
	public static void setFirstValueOfType(@Nonnull Object instance, @Nonnull String type, @Nonnull Object value) {
		fieldSet(countFieldOfType(instance.getClass(), getClass(type), 0), instance, value);
	}

	/**
	 * Set the label of a field in a static way.
	 *
	 * @param clazz the class where the field is
	 * @param field the name of the field
	 * @param value the label to set
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static void setStaticField(@Nonnull Class<?> clazz, @Nonnull String field, @Nonnull Object value) {
		fieldSet(findField(clazz, field), null, value);
	}

	/**
	 * Set the label of a field in a static way. This method resolves the class.
	 *
	 * @param clazz a string representing the clazz
	 * @param field the name of the field
	 * @param value the label to set
	 * @throws ReflectionException if any non-runtime exception is thrown
	 */
	public static void setStaticField(@Nonnull String clazz, @Nonnull String field, @Nonnull Object value) {
		fieldSet(findField(getClass(clazz), field), null, value);
	}

	/**
	 * Instanciate new class
	 *
	 * @param clazz a class to instanciate
	 * @param <T>   class type
	 * @return a new class instance
	 */
	@SuppressWarnings("OverlyBroadCatchBlock")
	private static <T> T classNewInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * Call a constructor if existing
	 *
	 * @param method constructor method
	 * @param args   constructor args
	 * @param <T>    generic class
	 * @return the new generic class
	 */
	private static <T> T constructorNewInstance(Constructor<T> method, Object[] args) {
		try {
			method.setAccessible(true);
			return method.newInstance(args);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}

	/*
	 * Used to search in parent classes.
	 */
	private static Field countFieldOfType(Class<?> clazz, Class<?> type, int count) {
		return countFieldOfType(clazz, type, count, clazz);
	}

	/*
	 * Recursive.
	 */
	private static Field countFieldOfType(Class<?> clazz, Class<?> type, int count, Class<?> search) {
		var index = 0;
		for (var field : search.getDeclaredFields()) {
			if (field.getType() == type) {
				if (index == count) {
					field.setAccessible(true);
					return field;
				}
				index++;
			}
		}

		Class<?> superClass = search.getSuperclass();
		if (superClass != null)
			return countFieldOfType(clazz, type, count, superClass);
		throw new ReflectionException("Cannot find a field with packet " + type + " in " + clazz + '.');
	}

	/**
	 * Access to a field
	 *
	 * @param field a field to get
	 * @param inst  instance to get from
	 * @param <T>   generic class
	 * @return class
	 */
	@SuppressWarnings("unchecked")
	private static <T> T fieldGet(Field field, Object inst) {
		try {
			field.setAccessible(true);
			return (T) field.get(inst);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * Set a field label in the given instance
	 *
	 * @param field a field to set label
	 * @param inst  class instance to set from
	 * @param value a label to set
	 */
	private static void fieldSet(Field field, Object inst, Object value) {
		try {
			field.setAccessible(true);
			field.set(inst, value);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * Find class by name
	 *
	 * @param name a class name
	 * @param <T>  class type
	 * @return the class if found
	 */
	@SuppressWarnings("unchecked")
	private static <T> Class<T> findClass(String name) {
		try {
			return (Class<T>) Class.forName(name);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * Get constructor only in class and not super class
	 *
	 * @param clazz    a class to get constructor from
	 * @param argsType constructor args
	 * @param <T>      class type
	 * @return a new constructor if found
	 */
	private static <T> Constructor<T> findConstructor(Class<T> clazz, Class<?>[] argsType) {
		try {
			return clazz.getDeclaredConstructor(argsType);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * Search field in parent classes.
	 *
	 * @param clazz parent class
	 * @param name  field name
	 * @return a field if found
	 */
	private static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, clazz);
	}

	/**
	 * Recursive search field in parent classes
	 *
	 * @param clazz  parent class
	 * @param name   field name
	 * @param search recursive search
	 * @return a field if found
	 */
	private static Field findField(Class<?> clazz, String name, Class<?> search) {
		Field[] fields = search.getDeclaredFields();
		for (var field : fields)
			if (field.getName().equals(name))
				return field;

		Class<?> superClass = search.getSuperclass();

		if (superClass != null)
			return findField(clazz, name, superClass);

		throw new ReflectionException("Cannot find field " + name + " in " + clazz);
	}

	/**
	 * Search implemented method in parent classes, and default implementations in interfaces.
	 *
	 * @param clazz    parent class
	 * @param name     method name
	 * @param argsType method args
	 * @return a method if found
	 */
	private static Method findMethod(Class<?> clazz, String name, Class<?>[] argsType) {
		return findMethod(clazz, name, argsType, clazz);
	}

	/**
	 * Recursive search implemented method in parent classes, and default implementations in interfaces.
	 *
	 * @param clazz    parent class
	 * @param name     method name
	 * @param argsType method args
	 * @param search   recursive class
	 * @return a method if found
	 */
	private static Method findMethod(Class<?> clazz, String name, Class<?>[] argsType, Class<?> search) {
		Method[] methods = search.getDeclaredMethods();
		for (var method : methods)
			if (method.getName().equals(name)
					&& Arrays.equals(argsType, method.getParameterTypes())
					&& (!search.isInterface() || method.isDefault())) //Default methods in interfaces are fine
				return method;

		var     superClass = search.getSuperclass();
		Class[] interfaces = search.getInterfaces();

		for (var interf : interfaces)
			findMethod(interf, name, argsType); //Find default methods in interfaces

		if (superClass != null)
			return findMethod(clazz, name, argsType, superClass);

		throw new ReflectionException("Cannot find field " + name + " in " + clazz);
	}

	/**
	 * Call a new method
	 *
	 * @param method a method to call
	 * @param inst   class instance to call from
	 * @param args   method args
	 * @param <T>    generic class
	 * @return class
	 */
	@SuppressWarnings("unchecked")
	private static <T> T methodCall(Method method, Object inst, Object[] args) {
		try {
			method.setAccessible(true);
			return (T) method.invoke(inst, args);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * A Functional Interface holding a Field.
	 */
	@FunctionalInterface
	public interface FieldAccessor<T> extends Supplier<Field> {

		/**
		 * Get the label of the field.
		 *
		 * @param instance the instance -- null for static access
		 * @return the current label
		 */
		@SuppressWarnings("unchecked")
		default T get(Object instance) {
			try {
				return (T) get().get(instance);
			} catch (ReflectiveOperationException e) {
				throw new ReflectionException(e);
			}
		}

		/**
		 * Get the declaring class of the field.
		 *
		 * @return the declaring class
		 */
		default Class<?> getDeclaringClass() {
			return get().getDeclaringClass();
		}

		/**
		 * Get the packet of the field.
		 *
		 * @return the packet of the field
		 */
		@SuppressWarnings("unchecked")
		default Class<T> getType() {
			return (Class<T>) get().getType();
		}

		/**
		 * Check whether the target has the exact same field.
		 *
		 * @param target the target
		 * @return <code>true</code> if the target is a sub class of the declaring class, <code>false</code> otherwise
		 */
		default boolean has(Class<?> target) {
			return get().getDeclaringClass().isAssignableFrom(target);
		}

		/**
		 * Set the label of the field.
		 *
		 * @param instance the instance -- null for static access
		 * @param value    the new label
		 */
		default void set(Object instance, T value) {
			try {
				get().set(instance, value);
			} catch (ReflectiveOperationException e) {
				throw new ReflectionException(e);
			}
		}
	}

	/**
	 * A Functional Interface holding a Method.
	 */
	@FunctionalInterface
	public interface MethodAccessor<T> extends Supplier<Method> {

		/**
		 * Get the declaring class of the field.
		 *
		 * @return the declaring class
		 */
		default Class<?> getDeclaringClass() {
			return get().getDeclaringClass();
		}

		/**
		 * Get the exception types of the method.
		 *
		 * @return the exception types
		 */
		default Class<?>[] getExceptionTypes() {
			return get().getExceptionTypes();
		}

		/**
		 * Get the parameter types of the method.
		 *
		 * @return the parameter types
		 */
		default Class<?>[] getParameterTypes() {
			return get().getParameterTypes();
		}

		/**
		 * Get the return packet of the method.
		 *
		 * @return the return packet of the method
		 */
		@SuppressWarnings("unchecked")
		default Class<T> getReturnType() {
			return (Class<T>) get().getReturnType();
		}

		/**
		 * Check whether the target has the exact same method.
		 *
		 * @param target the target
		 * @return <code>true</code> if the target is a sub class of the declaring class, <code>false</code> otherwise
		 */
		default boolean has(@Nonnull Class<?> target) {
			return get().getDeclaringClass().isAssignableFrom(target);
		}

		/**
		 * Invoke the method.
		 *
		 * @param instance the instance -- null for static access
		 * @param args     the args of the method
		 * @return the result of dispatching the method or <code>null</code> if the return packet is void
		 */
		@SuppressWarnings("unchecked")
		default T invoke(Object instance, Object... args) {
			try {
				return (T) get().invoke(instance, args);
			} catch (ReflectiveOperationException e) {
				throw new ReflectionException(e);
			}
		}
	}

	/**
	 * A Functional Interface holding a Constructor.
	 */
	@FunctionalInterface
	public interface ConstructorAccessor<T> extends Supplier<Constructor<T>> {

		/**
		 * Get the declaring class of the field.
		 *
		 * @return the declaring class
		 */
		default Class<?> getDeclaringClass() {
			return get().getDeclaringClass();
		}

		/**
		 * Get the exception types of the constructor.
		 *
		 * @return the exception types
		 */
		default Class<?>[] getExceptionTypes() {
			return get().getExceptionTypes();
		}

		/**
		 * Get the parameter types of the constructor.
		 *
		 * @return the parameter types
		 */
		default Class<?>[] getParameterTypes() {
			return get().getParameterTypes();
		}

		/**
		 * Check whether the target has the exact same constructor.
		 *
		 * @param target the target
		 * @return <code>true</code> if the target is a sub class of the declaring class, <code>false</code> otherwise
		 */
		default boolean has(@Nonnull Class<?> target) {
			return get().getDeclaringClass().isAssignableFrom(target);
		}

		/**
		 * Invoke the constructor.
		 *
		 * @param args the args of the method
		 * @return the new instance
		 */
		default T newInstance(Object... args) {
			try {
				return get().newInstance(args);
			} catch (ReflectiveOperationException e) {
				throw new ReflectionException(e);
			}
		}
	}

	/**
	 * A runtime exception to rethrow any non-runtime exception related to Reflection.
	 */
	public static final class ReflectionException extends RuntimeException {

		private ReflectionException() {
		}

		private ReflectionException(String arg0) {
			super(arg0);
		}

		private ReflectionException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public ReflectionException(Throwable arg0) {
			super(arg0);
		}
	}
}
