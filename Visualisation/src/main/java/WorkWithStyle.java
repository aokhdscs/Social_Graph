import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WorkWithStyle {

    //Читаем наш файл и возвращаем в виде строки
    public static String read(String fileName) {
        //Создаем String builder - он отличается от String тем, что он расширяемый, то есть String нельзя увеличить или изменить или вставить в середину значение,
        //а в Builder   можно
        StringBuilder sb = new StringBuilder();
        try {
            //Получаем наш файл и читаем его построчно
            BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
                //Метод finally выполняется при в любом случае, то есть закрываем поток в любом случае, даже в случае ошибки
            } finally {
                in.close();
            }
        } catch(IOException e) {
            System.out.println("File does not exist: " + e);
            throw new RuntimeException(e);
        }
        //Возвращаем наш получившийся текст
        return sb.toString();
    }
}