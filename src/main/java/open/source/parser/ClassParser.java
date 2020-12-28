package open.source.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.ProtectionDomain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClassParser {

	public <T> Object parse(Class<T> clazz) {

		// TODO : load from in memory, sync with db complete parsed object, on first call of every application startup
		// Note : do not persist within another entity / model the complete parsed object
		if (null != clazz) {
			boolean desiredAssertionStatus = clazz.desiredAssertionStatus();
			AnnotatedType[] annotatedInterfaces = clazz.getAnnotatedInterfaces();
			AnnotatedType annotatedSuperclass = clazz.getAnnotatedSuperclass();
			Annotation[] annotations = clazz.getAnnotations();
			String canonicalName = clazz.getCanonicalName();
			Class<?>[] classes = clazz.getClasses();
			Class<?> componentType = clazz.getComponentType();
			Constructor<?>[] constructors = clazz.getConstructors();
			Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
			Class<?>[] declaredClasses = clazz.getDeclaredClasses();
			Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
			Field[] declaredFields = clazz.getDeclaredFields();
			Method[] declaredMethods = clazz.getDeclaredMethods();
			Class<?> declaringClass = clazz.getDeclaringClass();
			Class<?> enclosingClass = clazz.getEnclosingClass();
			Constructor<?> enclosingConstructor = clazz.getEnclosingConstructor();
			Method enclosingMethod = clazz.getEnclosingMethod();
			T[] enumConstants = clazz.getEnumConstants();
			Field[] fields = clazz.getFields();
			Type[] genericInterfaces = clazz.getGenericInterfaces();
			Type genericSuperclass = clazz.getGenericSuperclass();
			Class<?>[] interfaces = clazz.getInterfaces();
			Method[] methods = clazz.getMethods();
			int modifiers = clazz.getModifiers();
			String name = clazz.getName();
			Package packageObj = clazz.getPackage();
			ProtectionDomain protectionDomain = clazz.getProtectionDomain();
			Object[] signers = clazz.getSigners();
			String simpleName = clazz.getSimpleName();
			Class<? super T> superclass = clazz.getSuperclass();
			String typeName = clazz.getTypeName();
			TypeVariable<Class<T>>[] typeParameters = clazz.getTypeParameters();
			int hashCode = clazz.hashCode();
			boolean annotationFlag = clazz.isAnnotation();
			boolean anonymousClassFlag = clazz.isAnonymousClass();
			boolean arrayFlag = clazz.isArray();
			boolean enumFlag = clazz.isEnum();
			boolean interfaceFlag = clazz.isInterface();
			boolean localClassFlag = clazz.isLocalClass();
			boolean memberClassFlag = clazz.isMemberClass();
			boolean primitiveFlag = clazz.isPrimitive();
			boolean syntheticFlag = clazz.isSynthetic();
			String genericString = clazz.toGenericString();
			String toString = clazz.toString();
			// TODO : parse and build object
			// TODO : cache
		}
		return null;
	}

}
