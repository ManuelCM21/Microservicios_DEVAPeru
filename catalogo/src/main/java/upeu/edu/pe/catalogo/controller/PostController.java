package upeu.edu.pe.catalogo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import upeu.edu.pe.catalogo.entity.Imagen;
import upeu.edu.pe.catalogo.entity.Post;
import upeu.edu.pe.catalogo.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired
  private PostService postService;

  private final String UPLOAD_DIR = "src/main/resources/public/imagenes";

  @GetMapping()
  public ResponseEntity<List<Post>> listar() {
    return ResponseEntity.ok().body(postService.listar());
  }

  private AtomicInteger imageableIdCounter = new AtomicInteger(0);

  private Integer generarImageableId(MultipartFile[] files) {
    // Incrementar el imageableId solo cuando se suben múltiples imágenes
    if (files.length > 1) {
      return imageableIdCounter.incrementAndGet();
    }
    return imageableIdCounter.get();
  }

  @PostMapping()
  public ResponseEntity<List<String>> guardar(
      @ModelAttribute Post post,
      @RequestParam("file") MultipartFile[] files) {

    List<String> fileUrls = new ArrayList<>();
    Integer imageableId = generarImageableId(files);

    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        System.err.println("No se ha proporcionado un archivo adjunto válido.");
        return ResponseEntity.badRequest().build();
      }

      try {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isValidImageExtension(fileExtension)) {
          List<String> errorResponse = new ArrayList<>();
          errorResponse.add("Solo se permiten archivos con extensiones .png, .jpg y .jpeg");
          return ResponseEntity.badRequest().body(errorResponse);
        }

        String filename = UUID.randomUUID().toString();
        String newFileName = filename + fileExtension;
        Path filePath = Path.of(UPLOAD_DIR, newFileName);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String fileUrl = baseUri + "/imagenes/" + newFileName;
        fileUrls.add(fileUrl);

        Imagen imagen = new Imagen();
        imagen.setNombre(file.getOriginalFilename());
        imagen.setUrl(fileUrl);
        imagen.setImageableId(imageableId);

        post.setImage(imagen);

        postService.guardar(post);

      } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
    return ResponseEntity.ok(fileUrls);
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

  @PutMapping("/imagen/{id}")
  public ResponseEntity<String> actualizar(@PathVariable("id") Integer id,
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "file", required = false) MultipartFile file,
      @ModelAttribute Post post) {

    Optional<Post> postExistente = postService.listarPorId(id);
    if (!postExistente.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Post postActual = postExistente.get();

    try {
      if (nombre != null) {
        postActual.setNombre(nombre);
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
        deleteImageFile(postActual.getImage().getUrl());

        String fileUrl = baseUri + "/imagenes/" + newFileName;

        Imagen imagen = postActual.getImage();
        imagen.setNombre(file.getOriginalFilename());
        imagen.setUrl(fileUrl);

        post.setImage(imagen);
      }

      if (post.getEstado() != null) {
        postActual.setEstado(post.getEstado());
      }

      postService.actualizar(postActual);

      return ResponseEntity.ok(postActual.getImage().getUrl());
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

  @GetMapping("/{id}")
  public ResponseEntity<Post> buscarPorId(@PathVariable(required = true) Integer id) {
    return ResponseEntity.ok(postService.listarPorId(id).get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> eliminar(@PathVariable(required = true) Integer id) throws IOException {
    // Obtener la imagen por su ID
    Optional<Post> imagenOptional = postService.listarPorId(id);
    if (!imagenOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    // Obtener la imagen y su URL
    Post imagen = imagenOptional.get();
    String imageUrl = imagen.getImage().getUrl();

    // Verificar la existencia de la imagen en el sistema de archivos
    if (!imageExists(imageUrl)) {
      return ResponseEntity.notFound().build();
    }

    // Eliminar el archivo de la carpeta
    deleteImageFile(imageUrl);

    // Eliminar la imagen de la base de datos
    postService.eliminarPorId(id);

    return ResponseEntity.ok("Eliminación correcta");
  }

  private boolean imageExists(String imageUrl) {
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    String filePath = UPLOAD_DIR + "/" + fileName;
    return Files.exists(Paths.get(filePath));
  }
}