package in.printspool.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import in.printspool.model.PrintSpool;

public class PrintSpoolUtil {

	/*
	 * Esta función genera el archivo en formato .JSON y .CSV con datos sobre el
	 * periodo para el cual se está generando y la fecha de creación del archivo
	 */

	public List<String> csvGenerator(JsonNode res, String date) {

		// Formato para enviar al campo de DATE en la BD
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss");

		// Formato para agregar al nombre del archivo y enviar al campo FILENAME en la
		// BD
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss");

		LocalDateTime currentTime = LocalDateTime.now();

		String toFileTime = currentTime.format(formatter1);

		String toCreatedTime = currentTime.format(formatter2);
		;

		// Nombre del archivo en formato .JSON
		String json = date.replace('/', '-') + '_' + toCreatedTime + ".json";

		// Nombre del archivo en formato .CSV
		String csv = date.replace('/', '-') + '_' + toCreatedTime + ".csv";

		// Dirección de generación del Print Spool para su posterior descarga
		String downloadDir = "src/main/resources/download/";

		try {

			// Generación del archivo en formato .JSON
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(downloadDir + json), res);
			JsonNode jsonTree = new ObjectMapper().readTree(new File(downloadDir + json));

			// Builder con configuración para contruir el archivo .CSV con separador '|'
			CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {
				csvSchemaBuilder.addColumn(fieldName);
			});
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader().withColumnSeparator('|');

			// Generación del archivo en formato .CSV
			CsvMapper csvMapper = new CsvMapper();
			csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File(downloadDir + csv), jsonTree);

			// Lista que envía el nombre del archivo .CSV y la fecha para enviar a la BD
			List<String> list = Arrays.asList(csv, toFileTime);

			return list;

		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * Programa que cambia el formato de la respuesta de la petición a la base de
	 * datos para poder editar El formato inicial es String y el formato final es
	 * byte[]
	 */

	public JsonNode jsonNodeGenerator(List<PrintSpool> printSpool) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(printSpool);
			byte[] jsonByte = json.getBytes();
			JsonNode root = objectMapper.readTree(jsonByte);
			return root;
		} catch (Exception e) {
			return null;
		}
	}

}
