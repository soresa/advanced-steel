package ru.pfur.as.codes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AISCCalculator {

    static Set<AISCCode> codes = new HashSet<AISCCode>();

    static {
        String path = "/Users/user/";
        List<AISCCode> list = ExcelReader.readExcelData(path);
        codes.addAll(list);
    }

    public static Set<AISCCode> calculate(double greater, double lesser) {
        Set<AISCCode> result = new HashSet<AISCCode>();

        for (AISCCode code : codes) {
            if (code.getTw() > lesser && code.getTw() < greater)
                result.add(code);
        }
        return result;
    }


}
