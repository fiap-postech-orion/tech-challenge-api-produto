package br.com.postech.software.architecture.techchallenge.produto.controller;

import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoRequestDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvar_returnsCreatedResponseWithProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        when(produtoService.save(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.salvar(produtoDTO, UriComponentsBuilder.newInstance());

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(produtoDTO, response.getBody());
    }

    @Test
    public void deleteById_returnsNoContentResponse() {
        Long id = 1L;
        doNothing().when(produtoService).deleteById(id);

        ResponseEntity<?> response = produtoController.deleteById(id);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void atualizar_returnsOkResponseWithProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        when(produtoService.atualizar(any(ProdutoDTO.class))).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.atualizar(produtoDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produtoDTO, response.getBody());
    }

    @Test
    public void listarProdutosPorCategoria_returnsOkResponseWithProdutos() {
        List<ProdutoDTO> produtos = Arrays.asList(new ProdutoDTO());
        when(produtoService.findAll(null)).thenReturn(produtos);

        ResponseEntity<List<ProdutoDTO>> response = produtoController.listarProdutosPorCategoria(null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produtos, response.getBody());
    }

    @Test
    public void buscarProdutoPorId_returnsOkResponseWithProduto() {
        Long id = 1L;
        ProdutoDTO produtoDTO = new ProdutoDTO();
        when(produtoService.getDTOById(id)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoDTO> response = produtoController.buscarProdutoPorId(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(produtoDTO, response.getBody());
    }

    @Test
    public void validateProduto_returnsOkResponseWithValidation() {
        ValidaProdutoRequestDTO requestDTO = new ValidaProdutoRequestDTO();
        ValidaProdutoResponseDTO responseDTO = new ValidaProdutoResponseDTO();
        when(produtoService.validateProduto(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<ValidaProdutoResponseDTO> response = produtoController.validateProduto(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }
}
