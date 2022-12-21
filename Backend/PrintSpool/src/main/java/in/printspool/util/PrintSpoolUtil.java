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

	/* Esta función genera el archivo en formato .JSON y .CSV con datos sobre el periodo
	 * para el cual se está generando y la fecha de creación del archivo */
	
	public List<String> csvGenerator (JsonNode res, String date) {
		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-YYYY-HH-mm-ss");
		LocalDateTime currentTime = LocalDateTime.now();
		String toFileTime = currentTime.format(formatter1);
		
		String toCreatedTime = currentTime.format(formatter2);;
		
		String json = date.replace('/', '-')+'_'+toCreatedTime+".json";
		
		String csv = date.replace('/', '-')+'_'+toCreatedTime+".csv";
		
		String downloadDir = "src/main/resources/download/";
		
		try {	
	
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(downloadDir+json), res);
			
			JsonNode jsonTree = new ObjectMapper().readTree(new File(downloadDir+json));
			CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader().withColumnSeparator('|');
			
			CsvMapper csvMapper = new CsvMapper();
			csvMapper.writerFor(JsonNode.class)
			  .with(csvSchema)
			  .writeValue(new File(downloadDir+csv), jsonTree);
		
			List<String> list = Arrays.asList(csv, toFileTime);
			
			return list;
		
		} catch (Exception e) {
			return null;
		}
	}
	
	/* Programa que cambia el formato de la respuesta de la petición a la base de datos para poder editar */
	
	public JsonNode jsonNodeGenerator (List<PrintSpool> printSpool) {
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
