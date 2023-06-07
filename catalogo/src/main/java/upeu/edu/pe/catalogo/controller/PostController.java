package upeu.edu.pe.catalogo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

  @PostMapping()
  public ResponseEntity<String> guardar(@ModelAttribute Post post, @RequestParam("file") MultipartFile file) {

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
      Imagen imagen = new Imagen();
      imagen.setNombre(file.getOriginalFilename());
      imagen.setUrl(fileUrl);

      post.setImage(imagen);

      postService.guardar(post);

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
  public String eliminar(@PathVariable(required = true) Integer id) {
    postService.eliminarPorId(id);
    return "Eliminación Correcta";
  }
}