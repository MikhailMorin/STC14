package stc.lesson6;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класс, содержащий статические методы для
 * сериализации и десериализации объектов.
 *
 * @author Михаил Морин
 */
class Serializer {
    private static final int NAME_TAG_LENGTH = 6;
    private static final int TYPE_TAG_LENGTH = 6;

    /**
     * Метод, осуществляющий сериализацию переданного в качестве
     * аргумента объекта с записью в указанный файл в формате XML.
     *
     * @param o    - сериализуемый объект.
     * @param path - путь к файлу, куда будет сохранен сериализуемый объект
     */
    static void serialize(Object o, String path) throws IllegalAccessException, IOException {

        Files.createDirectories(Paths.get(path));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + o.getClass().getSimpleName() + ".xml"))) {
            String XMLObject = makeXMLFormat(o);
            bw.write(XMLObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException("Ошибка в работе с Reflection при сериализации");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Ошибка ввода/вывода при сериализации");
        }
    }

    /**
     * Метод, осуществляющий десериализацию из указанного файла.
     *
     * @param path - путь к файлу, содерщащему сериализованный объект.
     * @return - возвращает десериализованный объект.
     */
    static Object deSerialize(String path) throws IOException, ReflectiveOperationException {
        Object o = null;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();

            /*
            Парсинг типа объекта
            */
            sb.append(br.readLine());
            String type = sb.substring(sb.toString().indexOf("\"") + 1, sb.lastIndexOf("\""));
            Class clazz = Class.forName(type);
            o = clazz.getConstructor().newInstance();


            /*
            Парсинг полей объекта.
             */
            while (br.ready()) {
                sb.replace(0, sb.length(), "");

                String fieldXML = br.readLine();
                String nameParam = parseFieldName(fieldXML);
                if ("".equals(nameParam)) break;

                String typeParam = parseFieldType(fieldXML);
                String value = parseFieldValue(fieldXML);

                setValue(o, typeParam, nameParam, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Ошибка ввода/вывода при десериализации", e);
        } catch (ReflectiveOperationException e){
            e.printStackTrace();
            throw new ReflectiveOperationException("Ошибка в работе с Reflection при десериализации" ,e);
        }

        return o;
    }

    /**
     * Формирование XML представления объекта.
     * Заголовок: в качестве параметра тега - тип объекта.
     * Поля: Формирование строковых представлений параметров (имя, тип, значение).
     *
     * @param o - сериализуемый объект.
     * @return - представление объекта в XML формате.
     * @throws IllegalAccessException
     */
    private static String makeXMLFormat(Object o) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb.append("<Object name=\"").
                append(o.getClass().getName()).
                append("\">\n");

        for (Field f : o.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            sb.append("<field name=\"").
                    append(f.getName()).
                    append("\" type=\"").
                    append(f.getType().getName()).
                    append("\">");
            sb.append(f.get(o));
            sb.append("</field>\r\n");
        }

        sb.append("</Object>");
        return sb.toString();
    }

    /**
     * Заполнение поля объекта указанным параметром.
     *
     * @param o     - заполняемый объект.
     * @param type  - тип поля.
     * @param name  - имя поля.
     * @param value - значение поля.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void setValue(Object o, String type, String name, String value) throws NoSuchFieldException, IllegalAccessException {
        Field f = o.getClass().getDeclaredField(name);
        f.setAccessible(true);
        switch (type) {
            case "byte": {
                f.setByte(o, Byte.parseByte(value));
                break;
            }
            case "short": {
                f.setShort(o, Short.parseShort(value));
                break;
            }
            case "int": {
                f.setInt(o, Integer.parseInt(value));
                break;
            }
            case "long": {
                f.setLong(o, Long.parseLong(value));
                break;
            }
            case "float": {
                f.setFloat(o, Float.parseFloat(value));
                break;
            }
            case "double": {
                f.setDouble(o, Double.parseDouble(value));
                break;
            }
            case "boolean": {
                f.setBoolean(o, Boolean.parseBoolean(value));
                break;
            }
            case "char": {
                f.setChar(o, value.charAt(0));
                break;
            }
            case "java.lang.String": {
                f.set(o, value);
                break;
            }
        }
    }

    /**
     * Определение имени поля.
     *
     * @param s - строка, содержащая параметры поля формате XML.
     * @return - имя поля в виде строки.
     */
    private static String parseFieldName(String s) {
        int nameIndex = s.indexOf("name=");
        if (nameIndex == -1) return "";

        nameIndex += NAME_TAG_LENGTH;
        int endNameIndex = s.indexOf("\"", nameIndex);
        return s.substring(nameIndex, endNameIndex);
    }

    /**
     * Определение типа поля.
     *
     * @param s - строка, содержащая параметры поля формате XML.
     * @return - тип поля в виде строки.
     */
    private static String parseFieldType(String s) {
        int typeIndex = s.indexOf("type=") + TYPE_TAG_LENGTH;
        int endTypeIndex = s.indexOf("\"", typeIndex);
        return s.substring(typeIndex, endTypeIndex);
    }

    /**
     * Определение значения поля.
     *
     * @param s - строка, содержащая параметры поля формате XML.
     * @return - значение поля в виде строки.
     */
    private static String parseFieldValue(String s) {
        return s.substring(s.indexOf(">") + 1, s.lastIndexOf("<"));
    }
}
