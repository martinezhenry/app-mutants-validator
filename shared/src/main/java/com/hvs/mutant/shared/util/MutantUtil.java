package com.hvs.mutant.shared.util;


import com.hvs.mutant.model.Response;
import com.hvs.mutant.model.Specimen;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class MutantUtil {


    private MutantUtil() {
    }

    /**
     * Print in logger the table char received
     * @param table Table to print
     * @param logger Logger instance
     */
    public static void printDnaTable(char[][] table, Logger logger) {
        logger.debug("*** DNA Table [{} x {}] ***", table.length, table.length);

        var line = new StringBuilder("| ");
        for (var row = 0; row < table.length; row++) {
            for (var col = 0; col < table[row].length; col++) {
                line.append(table[row][col]).append(" ");
            }
            line.append("|");
            logger.debug("{}", line);
            line = new StringBuilder("| ");
        }
    }

    /**
     * Util to build a Response instance
     * @param message Message in the response
     * @param status Status in the response
     * @param specimen Specimen to response in body
     * @param stackTraceElements StackTrace to set, only if app is un debug mode
     * @return Response instance
     */
    public static Response buildResponse(String message, int status, Specimen specimen, StackTraceElement[] stackTraceElements) {
        return buildResponse(message, status, specimen, stackTraceElements, false);
    }

    /**
     * Util to build a Response instance
     * @param message Message in the response
     * @param status Status in the response
     * @param specimen Specimen to response in body
     * @param stackTraceElements StackTrace to set, only if app is un debug mode
     * @param debug is debug mode application active indicator
     * @return Response instance
     */
    public static Response buildResponse(String message, int status, Specimen specimen, StackTraceElement[] stackTraceElements, boolean debug) {
        var response = new Response();
        response.setMessage(message);
        response.setSpecimen(specimen);
        response.setStatus(status);
        if (debug) {
            response.setStackTraceElements(stackTraceElements);
        }
        response.setDatetime(new Date());
        response.setRequestId(MDC.get("request-id"));
        return response;
    }


    /**
     * Format a doble value to pattern received in param
     * @param amount Value to format
     * @param pattern Pattern to aplicate
     * @return double value formatted
     */
    public static double formatDouble(double amount, String pattern) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern(pattern);
        return Double.parseDouble(formatter.format(amount));
    }


}
