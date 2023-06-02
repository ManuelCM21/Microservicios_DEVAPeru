package upeu.edu.pe.catalogo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import upeu.edu.pe.catalogo.entity.Imagen;
import upeu.edu.pe.catalogo.entity.Post;
import upeu.edu.pe.catalogo.service.ImagenService;
import upeu.edu.pe.catalogo.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired
  private PostService postService;

  @Autowired
  private ImagenService imagenService;

  @GetMapping()
  public ResponseEntity<List<Post>> listar() {
    return ResponseEntity.ok().body(postService.listar());
  }

  @PostMapping()
  public ResponseEntity<Post> guardar(@RequestBody Post post, @RequestBody Imagen imagen) {
    imagenService.guardar(imagen);

    List<Imagen> images = new ArrayList<>();
    images.add(imagen);
    post.setImages(images);

    Post nuevoPost = postService.guardar(post);

    return ResponseEntity.ok(nuevoPost);
  }

  @PutMapping()
  public ResponseEntity<Post> actualizar(@RequestBody Post post) {
    return ResponseEntity.ok(postService.actualizar(post));
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