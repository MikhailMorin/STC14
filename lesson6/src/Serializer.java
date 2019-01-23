import java.io.*;
import java.lang.reflect.*;

/**
 * Класс, содержащий статические методы для
 * сериализации и десериализации объектов.
 *
 * @author Михаил Морин
 */
public class Serializer {
    /**
     * Метод, осуществляющий сериализацию переданного
     * в качестве аргумента объекта с записью в указанный
     * файл в формате XML.
     *
     * @param o    - сериализуемый объект.
     * @param path - путь к файлу, куда будет сохранен сериализуемый объект
     */
    public static void serialize(Object o, String path) {
        StringBuffer sb = new StringBuffer();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            /*
            Заголовок. В качестве параметра тега - тип объекта.
             */
            sb.append("<Object name=\"" + o.getClass().getName() + "\">\n");

            /*
            Формирование строковых представлений параметров (полей класса).
            Состоят из имени поля, типа, значения.
             */
            for (Field f : o.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                sb.append("<field name=\"" + f.getName() + "\" type=\"" + f.getType().getName() + "\">");
                sb.append(f.get(o));
                sb.append("</field>\r\n");
            }

            sb.append("</Object>");
            bw.write(sb.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, осуществляющий десериализацию из указанного файла.
     *
     * @param path - путь к файлу, содерщащему сериализованный объект.
     * @return - возвращает десериализованный объект.
     */
    public static Object deSerialize(String path) {
        Object o = null;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuffer sb = new StringBuffer();

            /*
            Считывается первая строка, с целью определения типа хранящегося объекта.
            На основании полученных данных создаётся сам объект.
             */
            sb.append(br.readLine());
            String type = sb.substring(sb.toString().indexOf("\"") + 1, sb.lastIndexOf("\""));
            Class clazz = Class.forName(type);
            o = clazz.getConstructor().newInstance();


            /*
            Далее, строки считываются последовательно, с целью заполнения
            значениями полей созданного объекта.
             */
            while (br.ready()) {
                sb.replace(0, sb.length(), br.readLine());

                /*
                Определение места в строке, где хранится имя очередного поля.
                После определения места, определяется и само имя поля.
                 */
                int nameIndex = sb.indexOf("name=");
                if (nameIndex == -1)
                    break;
                nameIndex += 6;
                int endNameIndex = sb.indexOf("\"", nameIndex);
                String nameParam = sb.substring(nameIndex, endNameIndex);

                /*
                Определение места в строке, где хранится тип очередного поля.
                После определения места, определяется и сам тип поля.
                 */
                int typeIndex = sb.indexOf("type=") + 6;
                int endTypeIndex = sb.indexOf("\"", typeIndex);
                String typeParam = sb.substring(typeIndex, endTypeIndex);

                 /*
                Определение места в строке, где хранится значение очередного поля.
                После определения места, определяется и само значение поля.
                 */
                String value = sb.substring(sb.toString().indexOf(">") + 1, sb.lastIndexOf("<"));

                /*
                Далее, с помощью определенных выше данных,
                происходит заполнение полей объекта.
                 */
                Field f = o.getClass().getDeclaredField(nameParam);
                f.setAccessible(true);
                switch (typeParam) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return o;
    }
}
