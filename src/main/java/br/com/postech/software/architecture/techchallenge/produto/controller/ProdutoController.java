package br.com.postech.software.architecture.techchallenge.produto.controller;

import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoRequestDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(
            @RequestBody @Valid ProdutoDTO produtoDTO,
            UriComponentsBuilder uriBuilder
    ) {
        final var produto = produtoService.save(produtoDTO);
        final var uri = uriBuilder.path("/v1/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ProdutoDTO> atualizar(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.atualizar(produtoDTO));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorCategoria(@RequestParam(required = false) Long categoriaId) {
        return new ResponseEntity<>(produtoService.findAll(categoriaId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(produtoService.getDTOById(id), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidaProdutoResponseDTO> validateProduto(@RequestBody ValidaProdutoRequestDTO validaProdutoRequestDTO) {
        return new ResponseEntity<>(produtoService.validateProduto(validaProdutoRequestDTO), HttpStatus.OK);
    }
}