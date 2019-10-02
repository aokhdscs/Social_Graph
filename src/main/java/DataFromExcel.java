import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DataFromExcel {
    static SocialGraph AddData() throws IOException {
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Anastasia\\IdeaProjects\\SocialGraph\\DataSocialGraph.xls"));

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        SocialGraph graph = new SocialGraph();
        User[] users = new User[9999];
        HSSFRow row;
        String FistName, SecondName;
        Integer Id;
        Double Vulnerability;
        Integer i=0;

        while(true)
        {
            row = sheet.getRow(i);
            if (row.getCell(0) == null) {
                break;
            }
            Id = (int) row.getCell(0).getNumericCellValue();
            FistName = row.getCell(1).getStringCellValue();
            SecondName = row.getCell(2).getStringCellValue();
            Vulnerability = (double) Math.round((row.getCell(3).getNumericCellValue()) * 100) / 100;
            users[i] = new User(FistName, SecondName, Id);
            users[i].setVulnerability(Vulnerability);
            graph.addUser(users[i]);
            i=i+1;
        }

        sheet = workbook.getSheetAt(1);
        Integer Id1, Id2;
        Double Pos;
        i=0;
        while(true)
        {
            row = sheet.getRow(i);
            if ((int) row.getCell(0).getNumericCellValue() == 0) {
                break;
            }
            Id1 = (int) row.getCell(0).getNumericCellValue();
            Id2 = (int) row.getCell(1).getNumericCellValue();
            Pos = (double) Math.round(row.getCell(2).getNumericCellValue() * 100) / 100;
            graph.addToGraph(new Connection(graph.getUserById(Id1),graph.getUserById(Id2),Pos));
            i=i+1;
        }

        inputStream.close();
        return graph;
    }
}
