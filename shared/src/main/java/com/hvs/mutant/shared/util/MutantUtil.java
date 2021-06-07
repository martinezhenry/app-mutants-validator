package com.hvs.mutant.shared.util;


import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

public class MutantUtil {

    public static void printDnaTable(char[][] table, Logger logger) {
        logger.debug("*** DNA Table [{} x {}] ***", table.length, table.length);

        StringBuilder line = new StringBuilder("| ");
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                line.append(table[row][col]).append(" ");
            }
            line.append("|");
            logger.debug("{}", line);
            line = new StringBuilder("| ");
        }
    }

    public static Response buildResponse(String message, int status, Specimen data, StackTraceElement[] stackTraceElements) {
        return buildResponse(message, status, data, stackTraceElements, false);
    }

    public static Response buildResponse(String message, int status, Specimen data, StackTraceElement[] stackTraceElements, boolean debug) {
        var response = new Response();
        response.setMessage(message);
        response.setSpecimen(data);
        response.setStatus(status);
        if (debug) {
            response.setStackTraceElements(stackTraceElements);
        }
        response.setDatetime(new Date());
        response.setRequestId(MDC.get("request-id"));
        return response;
    }


    public static double formatDouble(double amount, String pattern) {
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
        formatter.applyPattern(pattern);
        return Double.parseDouble(formatter.format(amount));
    }


}
