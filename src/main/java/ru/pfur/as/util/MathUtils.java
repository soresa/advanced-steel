package ru.pfur.as.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class MathUtils {

    public static double roundUp(double num) {
        return (Math.ceil(num / 5d) * 5);
    }

    public static double calculateH(double l) {
        log.debug("CALCULATION H ===> ");
        double max = Math.max(l * 12.0 / 12.0, l * 12 / 10);
        log.debug("MAX = " + max);
        double aver = (l * 12.0 / 12.0 + l * 12 / 10) / 2;
        log.debug("AVER = " + aver);
        double H = MathUtils.roundUp(aver);
        log.debug("H = " + H);
        if (H < max) H = max;
        log.debug("H = " + H);

        double Hresult = MathUtils.roundUp(H);
        log.debug("H result = " + Hresult);
        return Hresult;
    }

    public static int[] toFractionPos(BigDecimal x) {
        String[] parts = x.toString().split("\\.");
        BigDecimal den = BigDecimal.TEN.pow(parts[1].length()); // denominator
        BigDecimal num = (new BigDecimal(parts[0]).multiply(den)).add(new BigDecimal(parts[1])); // numerator
        return reduceFraction(num.intValue(), den.intValue());
    }

    public static int[] reduceFraction(int num, int den) {
        int gcd = BigInteger.valueOf(num).gcd(BigInteger.valueOf(den)).intValue();
        int[] rf = {num / gcd, den / gcd};
        return rf;
    }

    public static void main(String[] args) {
        double a = 73.0;
        double b = 69.5;

        System.out.println(Math.round(a / 10) * 10);

        System.out.println(roundUp(a));
    }


}
