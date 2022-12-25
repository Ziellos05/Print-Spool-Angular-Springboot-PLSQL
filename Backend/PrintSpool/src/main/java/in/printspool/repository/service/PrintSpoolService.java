package in.printspool.repository.service;

// Interface para implementar el servicio de la generación del print spool
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import in.printspool.model.PrintSpool;
import in.printspool.model.PrintSpoolCsv;
import in.printspool.model.SpoolConfig;
import in.printspool.repository.PrintSpoolRepository;
import in.printspool.util.PrintSpoolUtil;
@Service
public class PrintSpoolService {
	
	@Autowired
	PrintSpoolRepository printSpoolRepository;

	/*
	 * S: Stratum, A: Average (Promedio) y L: Last (Últimos), en referencia a la
	 * información que se solicitará dinámicamente, todas las funciones obtienen el
	 * print spool con específicamente los campos requeridos
	 */

	public PrintSpoolCsv getPrintSpoolSAL(SpoolConfig spoolConfig){
		
		
		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSAL(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "S-A-L-" + spoolConfig.getNConsumptions();
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
		
	};

	public PrintSpoolCsv getPrintSpoolSA(SpoolConfig spoolConfig){
		
		
		/* Llamado a la base de datos para obtener el print spool */
		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSA(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();

		/*
		 * Transformación de la data al formato JsonNode para poder editar y quitar los
		 * campos que no se requieran en la respuesta
		 */
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("last");
			}
		}

		/*
		 * Generación del .JSON, .CSV y agregado de la información a la tabla que hace
		 * seguimiento a los archivos creados
		 */

		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		
		

		/* Retorna objeto con la info para acceder al archivo y poder "descargarlo" */

		String code = "S-A-" + spoolConfig.getNConsumptions();
		
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
	};

	public PrintSpoolCsv getPrintSpoolSL(SpoolConfig spoolConfig){

		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolSL(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("avgConsumption");
			}
		}
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "S-L-" + spoolConfig.getNConsumptions();
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
	};

	public PrintSpoolCsv getPrintSpoolAL(SpoolConfig spoolConfig){

		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolAL(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("stratum");
			}
		}
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "A-L-" + spoolConfig.getNConsumptions();
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
	};

	public PrintSpoolCsv getPrintSpoolS(SpoolConfig spoolConfig){
		
		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolS(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("avgConsumption");
				edit.remove("last");
			}
		}
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "S";
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
	};

	public PrintSpoolCsv getPrintSpoolA(SpoolConfig spoolConfig){

		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolA(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("stratum");
				edit.remove("last");
			}
		}
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "A-" + spoolConfig.getNConsumptions();
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
	};

	public PrintSpoolCsv getPrintSpoolL(SpoolConfig spoolConfig){

		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpoolL(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("avgConsumption");
				edit.remove("stratum");
			}
		}
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "L-" + spoolConfig.getNConsumptions();
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
	};

	public PrintSpoolCsv getPrintSpool(SpoolConfig spoolConfig){

		List<PrintSpool> printSpool = printSpoolRepository.getPrintSpool(spoolConfig);
		PrintSpoolUtil util = new PrintSpoolUtil();
		JsonNode root = util.jsonNodeGenerator(printSpool);
		for (JsonNode jsonNode : root) {
			if (jsonNode instanceof ObjectNode) {
				ObjectNode edit = (ObjectNode) jsonNode;
				edit.remove("stratum");
				edit.remove("avgConsumption");
				edit.remove("last");
			}
		}
		List<String> csvList = util.csvGenerator(root, spoolConfig.getDate());
		String code = "NOT";
		return printSpoolRepository.printSpoolCsv(csvList.get(0), csvList.get(1), spoolConfig.getDate(), code).get(0);
		
	};

	/*
	 * Esta función inserta la información sobre el CSV generado en una tabla para
	 * poder hacer seguimiento del archivo
	 */

	public List<PrintSpoolCsv> printSpoolCsv(String filename, String dateCreation, String period, String code){
		return printSpoolRepository.printSpoolCsv(filename, dateCreation, period, code);
	};

	/* Get para obtener la lista de todos los archivos creados en formato .CSV */

	public List<PrintSpoolCsv> getPrintSpoolCsv(){
		return printSpoolRepository.getPrintSpoolCsv();
	};

}