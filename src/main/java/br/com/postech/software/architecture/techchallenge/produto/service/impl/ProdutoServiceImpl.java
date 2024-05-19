package br.com.postech.software.architecture.techchallenge.produto.service.impl;

import br.com.postech.software.architecture.techchallenge.produto.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoRequestDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.produto.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.produto.model.Produto;
import br.com.postech.software.architecture.techchallenge.produto.model.ProdutoCategoria;
import br.com.postech.software.architecture.techchallenge.produto.repository.ProdutoCategoriaRepository;
import br.com.postech.software.architecture.techchallenge.produto.repository.ProdutoRepository;
import br.com.postech.software.architecture.techchallenge.produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
    private final ProdutoRepository produtoRepository;
    private final ProdutoCategoriaRepository produtoCategoriaRepository;

    @Override
    public List<ProdutoDTO> findAll(Long categoriaId) {
        if (Objects.nonNull(categoriaId)) {
            Optional<ProdutoCategoria> optCategoria = produtoCategoriaRepository.findById(categoriaId);
            if(optCategoria.isPresent()){
                return MAPPER.map(produtoRepository.findByCategoria(optCategoria.get()),
                        new TypeToken<List<ProdutoDTO>>() {
                        }.getType());
            }
        }

        return MAPPER.map(produtoRepository.findAll(),
                new TypeToken<List<ProdutoDTO>>() {
                }.getType());
    }

    @Override
    public ProdutoDTO getDTOById(Long id) {
        Produto produto = produtoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        return MAPPER.map(produto, ProdutoDTO.class);

    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
    }

    public Optional<Produto> findOptionalById(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        var produto = MAPPER.map(produtoDTO, Produto.class);

        setImagesToProduto(produto);
        produto = produtoRepository.save(produto);

        return MAPPER.map(produto, ProdutoDTO.class);
    }


    @Override
    public ProdutoDTO atualizar(ProdutoDTO produtoDTO) {
        var produto = MAPPER.map(produtoDTO, Produto.class);
        setImagesToProduto(produto);

        produtoRepository.findById(produtoDTO.getId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));

        produto = produtoRepository.save(produto);

        return MAPPER.map(produto, ProdutoDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    private void setImagesToProduto(Produto produto) {
        Optional.ofNullable(produto.getImagens())
                .orElseThrow(() -> new BusinessException("É obrigatório informar pelo menos uma imgem para o produto!"))
                .stream()
                .filter(img -> Objects.isNull(img.getProduto()))
                .forEach(img -> {
                    img.setProduto(produto);
                });
    }

    public ValidaProdutoResponseDTO validateProduto(ValidaProdutoRequestDTO validaProdutoRequestDTO) {

        if(CollectionUtils.isEmpty(validaProdutoRequestDTO.getProdutoDTOS())){
            return new ValidaProdutoResponseDTO().toBuilder()
                    .setIsValid(false)
                    .setErrorMessage("Nenhum Produto informado para Validação")
                    .build();
        }

        for (ProdutoDTO produtoDTO : validaProdutoRequestDTO.getProdutoDTOS()) {
            if(!findOptionalById(produtoDTO.getId()).isPresent()){
                return new ValidaProdutoResponseDTO().toBuilder()
                        .setProdutoDTOs(validaProdutoRequestDTO.getProdutoDTOS())
                        .setIsValid(false)
                        .setErrorMessage("Produto Inválido encontrado")
                        .build();
            }
        }

        return new ValidaProdutoResponseDTO().toBuilder()
                .setProdutoDTOs(validaProdutoRequestDTO.getProdutoDTOS())
                .setIsValid(true)
                .build();
    }
}