package in.printspool.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// CONTROLLER PARA LA DESCARGA DE ARCHIVOS DESDE LA API
@RestController
@RequestMapping(value = "/download")
@Tag(name = "Download", description = "Download controller for .CSV print spools and uploaded files")
@CrossOrigin({"*"})
public class DownloadController {
	
	@Operation(summary = "Download a file :)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "File downloaded", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
    @GetMapping
    public ResponseEntity<Resource> download(@RequestParam("file") String link) throws IOException {
        File file = new File(link);
        String filename = (link.split("/"))[4];
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}