package com.merkator3.merkator3api.StatTools;

import java.util.List;

public class TotalCalculator {

    public static double calculateTotal(List<Double> stages) {
        return stages.stream().mapToDouble(Double::doubleValue).sum();
    }

    public static String formatTotal(Double total, String unitType){
        return total.toString() + " " + unitType;

    }

}
