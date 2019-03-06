package com.elumea.shipsdemo.controller;

import com.elumea.shipsdemo.entity.ShipEntity;
import com.elumea.shipsdemo.repository.ShipRepository;
import com.elumea.shipsdemo.service.ServiceUtils;
import com.elumea.shipsdemo.storage.StorageFileNotFoundException;
import com.elumea.shipsdemo.storage.StorageService;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Qualifier
public class ApplicationController {

  private final StorageService storageService;

  @Autowired
  public ApplicationController(StorageService storageService) {
    this.storageService = storageService;
  }

  @Autowired private ShipRepository shipRepository;

  @GetMapping("/upload")
  public String listUploadedFiles(Model model) throws IOException {

    model.addAttribute(
        "files",
        storageService
            .loadAll()
            .map(
                path ->
                    MvcUriComponentsBuilder.fromMethodName(
                            ApplicationController.class, "serveFile", path.getFileName().toString())
                        .build()
                        .toString())
            .collect(Collectors.toList()));

    return "uploadForm";
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    Resource file = storageService.loadAsResource(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  @PostMapping("/upload")
  public String handleFileUpload(
      @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    try {
      storageService.store(file);

      shipRepository.saveAll(ServiceUtils.read(ShipEntity.class, file.getInputStream()));

    } catch (IOException e) {

      e.printStackTrace();
    }

    redirectAttributes.addFlashAttribute(
        "message", "You successfully uploaded " + file.getOriginalFilename() + "!");

    return "redirect:/upload";
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }
}
