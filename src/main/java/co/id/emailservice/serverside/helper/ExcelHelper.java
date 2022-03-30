package co.id.emailservice.serverside.helper;

import co.id.emailservice.serverside.model.dto.ParticipantData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = { "name", "email", "emailListNameId" };
    static String SHEET = "Sheet1";
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<ParticipantData> excelToParticipants(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<ParticipantData> participantsData = new ArrayList<ParticipantData>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                ParticipantData participantData = new ParticipantData();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            participantData.setName(currentCell.getStringCellValue());
//                            participantData.setId((long) currentCell.getNumericCellValue());
//                            tutorial.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            participantData.setEmail(currentCell.getStringCellValue());
//                            tutorial.setTitle(currentCell.getStringCellValue());
                            break;
                        case 2:
                            participantData.setEmailListNameId((long) currentCell.getNumericCellValue());
//                            tutorial.setDescription(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                participantsData.add(participantData);
//                tutorials.add(tutorial);
            }
            workbook.close();
            return participantsData;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
