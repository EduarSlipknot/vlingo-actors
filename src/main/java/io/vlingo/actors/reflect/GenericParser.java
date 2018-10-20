package io.vlingo.actors.reflect;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GenericParser {
    private GenericParser() {}


    public static Stream<String> genericReferencesOf(Method method) {
        return Stream.concat(
                Stream.concat(
                    Stream.of(method.getGenericReturnType()),
                    Arrays.stream(method.getGenericParameterTypes())
                ),
                Stream.of(method.getClass())
        ).flatMap(GenericParser::genericReferencesOf);
    }

    public static Stream<String> dependenciesOf(Class<?> classRef) {
        return Arrays.stream(classRef.getMethods()).flatMap(GenericParser::dependenciesOf);
    }

    public static Stream<String> dependenciesOf(Method method) {
        Set<String> genericTypeAlias = genericReferencesOf(method).collect(Collectors.toSet());

        return Stream.concat(Arrays.stream(method.getGenericParameterTypes()), Stream.of(method.getGenericReturnType()))
                .flatMap(GenericParser::typeNameToTypeStream)
                .filter(type -> !genericTypeAlias.contains(type));
    }

    private static Stream<String> typeNameToTypeStream(Type type) {
        if (type instanceof TypeVariable) {
            return Arrays.stream(((TypeVariable) type).getBounds())
                    .flatMap(GenericParser::typeNameToTypeStream);
        }  else if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            return Stream.concat(
                    Arrays.stream(paramType.getActualTypeArguments()).flatMap(GenericParser::typeNameToTypeStream),
                    typeNameToTypeStream(paramType.getRawType())
            );
        }

        return Arrays.stream(type.getTypeName().replaceAll("[<>]", "==").split("=="));
    }

    private static Stream<String> genericReferencesOf(Type type) {
        if (type instanceof TypeVariable) {
            TypeVariable variable = (TypeVariable) type;
            return Stream.of(variable.getName());
        } else if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            return Arrays.stream(paramType.getActualTypeArguments())
                    .flatMap(GenericParser::genericReferencesOf);
        }

        return Stream.empty();
    }
}
