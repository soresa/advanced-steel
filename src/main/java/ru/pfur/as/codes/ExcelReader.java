package ru.pfur.as.codes;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public static List<AISCCode> readExcelData(String fileName) {
        List<AISCCode> countriesList = new ArrayList<AISCCode>();

        try {
            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(fileName);

            //Create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();

            //loop through each of the sheets
            for (int i = 0; i < numberOfSheets; i++) {

                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);

                int i2 = 0;
                int r = 0;

                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {

                    //Get the row object
                    Row row = rowIterator.next();

                    if (r == 0) {
                        r++;
                        continue;
                    }

                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();

                    String name = "";
                    double tw = 0;
                    double bf = 0;
                    double tw2 = 0;

                    while (cellIterator.hasNext()) {
                        //Get the Cell object


                        Cell cell = cellIterator.next();


                        //
                        int column = cell.getColumnIndex();

                        if (column == 1)
                            name = cell.getStringCellValue().trim();
                        if (column == 10)
                            bf = cell.getNumericCellValue();
                        if (column == 14)
                            tw = cell.getNumericCellValue();
                        if (column == 15)
                            tw2 = cell.getNumericCellValue();


                        //check the cell type and process accordingly

                    } //end of cell iterator


                    AISCCode c = new AISCCode(name, tw, tw2, bf);
                    countriesList.add(c);

                } //end of rows iterator


            } //end of sheets for loop

            //close file input stream
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return countriesList;
    }

    public static void main(String args[]) {
        List<AISCCode> list = readExcelData("/Users/user/Desktop/1. RUSSIA/1РУДН/ACADEMIC/3rd Sem, 2016/aaa Prof/Stability of plane frames/Java/Prof/Stability of plane frames/AdvSteel/src/main/resources/ru/pfur/as/codes/codes_2.xlsx");
        System.out.println("AISCCode List\n");
        for (AISCCode code : list)
            System.out.println(code);


    }


}
