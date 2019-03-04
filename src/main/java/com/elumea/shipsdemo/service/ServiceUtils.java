package com.elumea.shipsdemo.service;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceUtils {

  Logger logger = LoggerFactory.getLogger(ServiceUtils.class);

  public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
    CsvMapper mapper = new CsvMapper();
    mapper.enable(CsvParser.Feature.TRIM_SPACES);
    CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
    ObjectReader reader = mapper.readerFor(clazz).with(schema);
    return reader.<T>readValues(stream).readAll();
  }

  private static ServiceUtils singleInstance = null;

  public static ServiceUtils getInstance() {
    if (singleInstance == null) singleInstance = new ServiceUtils();
    return singleInstance;
  }

  public String formatToDate(String inputString) {

    inputString = inputString.replace(".", "-").replace("/", "-");

    Timestamp timestamp = new Timestamp(Objects.requireNonNull(this.tryParse(inputString)));

    String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(timestamp);

    logger.info("Date Format:: {}", dateFormat);

    return dateFormat;
  }

  private Long tryParse(String dateString) {

    List<String> formatStrings = Arrays.asList("dd-MM-yyyy HH:mm", "yyyy-MM-dd HH:mm");

    for (String formatString : formatStrings) {

      try {
        SimpleDateFormat sdfrmt = new SimpleDateFormat(formatString);
        sdfrmt.setLenient(false);

        return sdfrmt.parse(dateString).getTime();

      } catch (ParseException e) {

      }
    }
    return null;
  }
}
