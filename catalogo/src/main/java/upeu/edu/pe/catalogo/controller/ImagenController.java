package upeu.edu.pe.catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import upeu.edu.pe.catalogo.entity.Imagen;
import upeu.edu.pe.catalogo.service.ImagenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImagenController {
  private final String UPLOAD_DIR = "src/main/resources/public/imagenes";

  @Autowired
  private ImagenService imagenService;

  @GetMapping()
  public ResponseEntity<List<Imagen>> listar() {
      return ResponseEntity.ok().body(imagenService.listar());
  }

  @PostMapping()
  public ResponseEntity<String> guardar(@ModelAttribute Imagen imagen, @RequestParam("file") MultipartFile file) {

    if (file.isEmpty()) {
      System.err.println("No se ha proporcionado un archivo adjunto válido.");
      return ResponseEntity.badRequest().build();
    }

    try {
      String fileExtension = getFileExtension(file.getOriginalFilename());
      if (!isValidImageExtension(fileExtension)) {
        return ResponseEntity.badRequest().body("Solo se permiten archivos con extensiones .png, .jpg y .jpeg");
      }
      String filename = UUID.randomUUID().toString();
      String newFileName = filename + fileExtension;
      Path filePath = Path.of(UPLOAD_DIR, newFileName);

      String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      String fileUrl = baseUri + "/imagenes/" + newFileName;
      imagen.setUrl(fileUrl);

      imagenService.guardar(imagen);

      return ResponseEntity.ok(fileUrl);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private boolean isValidImageExtension(String fileExtension) {
    return fileExtension.equalsIgnoreCase(".png") ||
        fileExtension.equalsIgnoreCase(".jpg") ||
        fileExtension.equalsIgnoreCase(".jpeg");
  }

  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex);
    }
    return "";
  }

  @GetMapping("/{id}")
    public ResponseEntity<Imagen> buscarPorId(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(imagenService.listarPorId(id).get());
    }

  @PutMapping("/imagen/{id}")
  public ResponseEntity<String> actualizar(@PathVariable("id") Integer id,
      @RequestParam(value = "image_type", required = false) String image_type,
      @RequestParam(value = "file", required = false) MultipartFile file,
      @ModelAttribute Imagen imagen) {

    Optional<Imagen> imagenExistente = imagenService.listarPorId(id);
    if (!imagenExistente.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Imagen imagenActual = imagenExistente.get();

    try {
      if (image_type != null) {
        imagenActual.setUrl(image_type);
      }

      if (file != null && !file.isEmpty()) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isValidImageExtension(fileExtension)) {
          return ResponseEntity.badRequest()
              .body("Solo se permiten archivos con extensiones .png, .jpg y .jpeg");
        }

        String filename = UUID.randomUUID().toString();
        String newFileName = filename + fileExtension;
        Path filePath = Path.of(UPLOAD_DIR, newFileName);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Elimina el archivo de imagen existente solo si se ha copiado el archivo
        // adjunto con un nuevo nombre
        deleteImageFile(imagenActual.getUrl());

        String fileUrl = baseUri + "/imagenes/" + newFileName;
        imagenActual.setUrl(fileUrl);
      }

      if (imagen.getEstado() != null) {
        imagenActual.setEstado(imagen.getEstado());
      }
      
      imagenService.actualizar(imagenActual);

      return ResponseEntity.ok(imagenActual.getUrl());
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private void deleteImageFile(String imageUrl) {
    // Extrae el nombre de archivo de la URL
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

    // Construye la ruta completa del archivo
    String filePath = UPLOAD_DIR + "/" + fileName;

    // Elimina el archivo de imagen
    try {
      Files.deleteIfExists(Paths.get(filePath));
    } catch (IOException e) {
      // Maneja cualquier error de eliminación del archivo
      e.printStackTrace();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> eliminar(@PathVariable(required = true) Integer id) throws IOException {
    // Obtener la imagen por su ID
    Optional<Imagen> imagenOptional = imagenService.listarPorId(id);
    if (!imagenOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    // Obtener la imagen y su URL
    Imagen imagen = imagenOptional.get();
    String imageUrl = imagen.getUrl();

    // Verificar la existencia de la imagen en el sistema de archivos
    if (!imageExists(imageUrl)) {
      return ResponseEntity.notFound().build();
    }

    // Eliminar el archivo de la carpeta
    deleteImageFile(imageUrl);

    // Eliminar la imagen de la base de datos
    imagenService.eliminarPorId(id);

    return ResponseEntity.ok("Eliminación correcta");
  }

  private boolean imageExists(String imageUrl) {
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    String filePath = UPLOAD_DIR + "/" + fileName;
    return Files.exists(Paths.get(filePath));
  }
}