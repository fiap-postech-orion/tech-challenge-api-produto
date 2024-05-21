package br.com.postech.software.architecture.techchallenge.produto.service.impl;

import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoCategoriaDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoRequestDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.produto.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.produto.model.Produto;
import br.com.postech.software.architecture.techchallenge.produto.model.ProdutoCategoria;
import br.com.postech.software.architecture.techchallenge.produto.repository.ProdutoCategoriaRepository;
import br.com.postech.software.architecture.techchallenge.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;

public class ProdutoServiceImplTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoCategoriaRepository produtoCategoriaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll_returnsAllProdutos() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> expectedProdutos = Arrays.asList(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(expectedProdutos);

        List<ProdutoDTO> actualProdutos = produtoService.findAll(null);

        assertEquals(expectedProdutos.size(), actualProdutos.size());
    }

    @Test
    public void getDTOById_returnsProdutoIfExists() {
        Long id = 1L;
        Produto expectedProduto = new Produto();

        when(produtoRepository.findById(id)).thenReturn(Optional.of(expectedProduto));

        ProdutoDTO actualProduto = produtoService.getDTOById(id);

        assertEquals(expectedProduto.getId(), actualProduto.getId());
    }

    @Test
    public void getDTOById_throwsNotFoundExceptionIfNotExists() {
        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> produtoService.getDTOById(id));
    }

    @Test
    public void atualizar_throwsNotFoundExceptionIfNotExists() {
        ProdutoDTO produtoDTO = new ProdutoDTO();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> produtoService.atualizar(produtoDTO));
    }

    @Test
    public void deleteById_doesNotThrowAnyException() {
        Long id = 1L;

        doNothing().when(produtoRepository).deleteById(id);

        produtoService.deleteById(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    public void validateProduto_returnsFalseIfProdutoNotExists() {
        ValidaProdutoRequestDTO requestDTO = new ValidaProdutoRequestDTO();
        requestDTO.setProdutoDTOS(Arrays.asList(new ProdutoDTO()));

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        ValidaProdutoResponseDTO responseDTO = produtoService.validateProduto(requestDTO);

        Assertions.assertFalse(responseDTO.isValid());
    }

    @Test
    public void findAll_returnsAllProdutosWhenNoCategoryId() {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> expectedProdutos = Arrays.asList(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(expectedProdutos);

        List<ProdutoDTO> actualProdutos = produtoService.findAll(null);

        assertEquals(expectedProdutos.size(), actualProdutos.size());
    }

    @Test
    public void findAll_returnsProdutosByCategoriaWhenCategoryIdProvided() {
        Long categoriaId = 1L;
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        List<Produto> expectedProdutos = Arrays.asList(produto1, produto2);
        ProdutoCategoria categoria = new ProdutoCategoria();

        when(produtoCategoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        when(produtoRepository.findByCategoria(categoria)).thenReturn(expectedProdutos);

        List<ProdutoDTO> actualProdutos = produtoService.findAll(categoriaId);

        assertEquals(expectedProdutos.size(), actualProdutos.size());
    }

    @Test
    public void findAll_returnsEmptyListWhenCategoryIdProvidedButNoMatch() {
        Long categoriaId = 1L;

        when(produtoCategoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        List<ProdutoDTO> actualProdutos = produtoService.findAll(categoriaId);

        Assertions.assertTrue(actualProdutos.isEmpty());
    }

    @Test
    public void findById_returnsProdutoIfExists() {
        Long id = 1L;
        Produto expectedProduto = new Produto();

        when(produtoRepository.findById(id)).thenReturn(Optional.of(expectedProduto));

        Produto actualProduto = produtoService.findById(id);

        assertEquals(expectedProduto, actualProduto);
    }

    @Test
    public void findById_throwsNotFoundExceptionIfNotExists() {
        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> produtoService.findById(id));
    }

    @Test
    public void save_throwsBusinessExceptionWhenImagesAreNotSet() {
        ProdutoDTO newProdutoDTO = new ProdutoDTO();

        assertThrows(BusinessException.class, () -> produtoService.save(newProdutoDTO));
    }

    @Test
    public void save_setsProdutoToImages() {
        ProdutoDTO newProdutoDTO = new ProdutoDTO();
        Produto newProduto = new Produto();
        newProduto.setImagens(Arrays.asList());

        when(produtoRepository.save(any(Produto.class))).thenReturn(newProduto);

        assertThrows(BusinessException.class, () -> produtoService.save(newProdutoDTO));
    }

    @Test
    public void validateProduto_returnsValidResponseWhenProdutoDTOSIsEmpty() {
        ValidaProdutoRequestDTO request = new ValidaProdutoRequestDTO();
        request.setProdutoDTOS(Collections.emptyList());

        ValidaProdutoResponseDTO response = produtoService.validateProduto(request);

        assertNotNull(response);
        Assertions.assertFalse(response.isValid());
        assertNull(response.getProdutoDTOs());
    }

    @Test
    public void validateProduto_returnsValidResponseWhenAllProdutosExist() {
        ProdutoDTO existingProduto = new ProdutoDTO();
        existingProduto.setId(1L);

        when(produtoService.findOptionalById(existingProduto.getId())).thenReturn(Optional.of(new Produto()));

        ValidaProdutoRequestDTO request = new ValidaProdutoRequestDTO();
        request.setProdutoDTOS(Collections.singletonList(existingProduto));

        ValidaProdutoResponseDTO response = produtoService.validateProduto(request);

        assertNotNull(response);
        Assertions.assertTrue(response.isValid());
        assertEquals(1, response.getProdutoDTOs().size());
        assertEquals(existingProduto, response.getProdutoDTOs().get(0));
    }

    @Test
    public void validateProduto_returnsInvalidResponseWhenAnyProdutoDoesNotExist() {
        ProdutoDTO nonExistingProduto = new ProdutoDTO();
        nonExistingProduto.setId(1L);

        when(produtoService.findOptionalById(nonExistingProduto.getId())).thenReturn(Optional.empty());

        ValidaProdutoRequestDTO request = new ValidaProdutoRequestDTO();
        request.setProdutoDTOS(Collections.singletonList(nonExistingProduto));

        ValidaProdutoResponseDTO response = produtoService.validateProduto(request);

        assertNotNull(response);
        Assertions.assertFalse(response.isValid());
        assertEquals(1, response.getProdutoDTOs().size());
        assertEquals(nonExistingProduto, response.getProdutoDTOs().get(0));
    }
}
