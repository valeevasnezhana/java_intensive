package app;

import java.io.File;
import java.lang.reflect.*;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String packageName = "classes";
                List<String> classNames = getClassesInPackage(packageName);

                System.out.println("Classes:");
                for (String className : classNames) {
                    System.out.println("  - " + className);
                }

                System.out.println("---------------------");
                System.out.println("Enter class name:");
                String className = "classes." + scanner.nextLine();
                Class<?> selectedClass = Class.forName(className);

                printClassDetails(selectedClass);
                Object object = createObject(selectedClass, scanner);
                System.out.println("Object created: " + object);

                System.out.println("---------------------");
                System.out.println("Enter name of the field for changing:");
                String fieldName = scanner.nextLine();
                System.out.println("Enter String value:");
                String updatedValue = scanner.nextLine();
                setField(object, fieldName, updatedValue);
                System.out.println("Object updated: " + object);

                System.out.println("---------------------");
                System.out.println("Enter the method call:");
                String methodCall = scanner.nextLine();
                invokeMethod(object, methodCall, scanner);

                break;
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + e.getMessage());
            } catch (ReflectiveOperationException |
                     IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            } catch (InputMismatchException e) {
                System.out.println("Input error: Invalid data.");
                System.out.println("Please try again.");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
                e.printStackTrace();
                break;
            }
        }
    }

    public static void printClassDetails(Class<?> selectedClass) {
        System.out.println("---------------------");
        System.out.println("fields:");
        Field[] fields = selectedClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(
                    "  " + field.getType().getSimpleName() + " " +
                            field.getName()
            );
        }

        System.out.println("methods:");
        Method[] methods = selectedClass.getDeclaredMethods();
        for (Method method : methods) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Class<?> parameterType : method.getParameterTypes()) {
                joiner.add(parameterType.getSimpleName());
            }

            if (joiner.toString().isEmpty() && method.getName().equals(
                    "toString")) {
                continue;
            }

            System.out.println(
                    "  " + method.getReturnType().getSimpleName() + " " +
                            method.getName() + "(" + joiner + ")"
            );
        }

        System.out.println("---------------------");
    }

    public static Object createObject(Class<?> selectedClass, Scanner scanner)
            throws ReflectiveOperationException {
        Constructor<?> emptyConstructor = selectedClass.getConstructor();
        Object object = emptyConstructor.newInstance();
        System.out.println("Let's create an object.");
        setFieldsForObject(scanner, object);
        return object;
    }

    public static void setFieldsForObject(Scanner scanner, Object object)
            throws ReflectiveOperationException {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + ":");
            String value = scanner.nextLine();
            setField(object, field.getName(), value);
        }
    }

    public static void invokeMethod(Object object, String methodCall,
                                    Scanner scanner)
            throws ReflectiveOperationException {
        String[] parts = methodCall.split("\\(");
        if (parts.length == 2) {
            String methodName = parts[0].trim();
            String parameters = parts[1].replace(")", "").trim();

            if (parameters.isEmpty()) {
                callMethod(object, methodName, null, null);
            } else {
                String[] argumentTypes = parameters.split(",");
                String[] argumentValues = new String[argumentTypes.length];

                for (int i = 0; i < argumentTypes.length; i++) {
                    String parameter = argumentTypes[i].trim();
                    System.out.println(
                            "Enter " + parameter + " value:"
                    );
                    argumentValues[i] = scanner.nextLine();
                }

                callMethod(object, methodName, argumentTypes,
                        argumentValues);
            }
        } else {
            throw new NoSuchMethodException("Input method is incorrect");
        }

    }

    public static void callMethod(Object object, String methodName,
                                  String[] argumentTypes,
                                  String[] argumentValues)
            throws ReflectiveOperationException, IllegalArgumentException {
        Object result;
        Method method;
        if (argumentTypes == null) {
            method = object.getClass().getDeclaredMethod(methodName);
            result = method.invoke(object);
        } else {

            Class<?>[] parameterTypes = getParameterTypes(argumentTypes);
            Object[] parsedArguments =
                    parseArguments(argumentTypes, argumentValues);

            method =
                    object.getClass()
                            .getDeclaredMethod(methodName, parameterTypes);
            result = method.invoke(object, parsedArguments);
        }
        if (method.getReturnType() != void.class) {
            System.out.println("Method returned: " + result);
        }

    }

    public static Class<?>[] getParameterTypes(String[] argumentTypes) {
        Class<?>[] parameterTypes = new Class<?>[argumentTypes.length];
        for (int i = 0; i < argumentTypes.length; i++) {
            parameterTypes[i] = getParameterType(argumentTypes[i]);
        }

        return parameterTypes;
    }

    public static Object[] parseArguments(String[] argumentTypes,
                                          String[] argumentValues) {
        Object[] parsedArguments = new Object[argumentTypes.length];
        for (int i = 0; i < argumentTypes.length; i++) {
            parsedArguments[i] =
                    parseArgument(argumentTypes[i], argumentValues[i]);
        }

        return parsedArguments;
    }

    public static Class<?> getParameterType(String argumentType) {
        switch (argumentType) {
            case "String":
                return String.class;
            case "Integer":
                return Integer.class;
            case "Double":
                return Double.class;
            case "Boolean":
                return Boolean.class;
            case "Long":
                return Long.class;
            case "int":
                return int.class;
            case "double":
                return double.class;
            case "boolean":
                return boolean.class;
            case "long":
                return long.class;
            default:
                throw new IllegalArgumentException(
                        "Invalid argument type: " + argumentType);
        }
    }

    public static Object parseArgument(String argumentType,
                                       String argumentValue) {
        switch (argumentType) {
            case "String":
                return argumentValue;
            case "Integer":
            case "int":
                return Integer.parseInt(argumentValue);
            case "Double":
            case "double":
                return Double.parseDouble(argumentValue);
            case "Boolean":
            case "boolean":
                return Boolean.parseBoolean(argumentValue);
            case "Long":
            case "long":
                return Long.parseLong(argumentValue);
            default:
                throw new IllegalArgumentException(
                        "Invalid argument type: " + argumentType);
        }
    }

    public static List<String> getClassesInPackage(String packageName) {
        List<String> classNames = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

        File packageDirectory = new File(
                Objects.requireNonNull(classLoader.getResource(packagePath))
                        .getPath());
        File[] classFiles =
                packageDirectory.listFiles(
                        (dir, name) -> name.endsWith(".class"));
        assert classFiles != null;
        for (File classFile : classFiles) {
            String className = classFile.getName().replace(".class", "");
            classNames.add(className);
        }

        return classNames;
    }

    public static void setField(Object object, String fieldName, String value)
            throws ReflectiveOperationException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            field.set(object, value);
        } else if (fieldType == int.class || fieldType == Integer.class) {
            field.set(object, Integer.parseInt(value));
        } else if (fieldType == double.class || fieldType == Double.class) {
            field.set(object, Double.parseDouble(value));
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            field.set(object, Boolean.parseBoolean(value));
        } else if (fieldType == long.class || fieldType == Long.class) {
            field.set(object, Long.parseLong(value));
        } else {
            throw new IllegalArgumentException(
                    "Unsupported field type: " + fieldType.getSimpleName());
        }
    }
}