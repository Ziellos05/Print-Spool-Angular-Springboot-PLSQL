package in.printspool.util;

import java.io.File;
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
		
		String currentDate = java.time.LocalDateTime.now().toString().replace(':', '_').replace('.', '_');
		
		String json = "src/main/resources/download/"+date.replace('/', '-')+'_'+currentDate+".json";
		
		String csv = "src/main/resources/download/"+date.replace('/', '-')+'_'+currentDate+".csv";
		
		try {	
	
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(json), res);
			
			JsonNode jsonTree = new ObjectMapper().readTree(new File(json));
			CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
			JsonNode firstObject = jsonTree.elements().next();
			firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
			CsvSchema csvSchema = csvSchemaBuilder.build().withHeader().withColumnSeparator('|');
			
			CsvMapper csvMapper = new CsvMapper();
			csvMapper.writerFor(JsonNode.class)
			  .with(csvSchema)
			  .writeValue(new File(csv), jsonTree);
		
			List<String> list = Arrays.asList(csv, currentDate);
			
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
