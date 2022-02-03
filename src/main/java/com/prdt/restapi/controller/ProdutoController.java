package com.prdt.restapi.controller;

import com.prdt.restapi.models.Produto;
import com.prdt.restapi.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "API REST de Produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @ApiOperation(value = "Lista todos produtos da tabela.")
    @GetMapping("/produtos")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> listarProdutos() {
        return this.produtoRepository.findAll();
    }

    @ApiOperation(value = "Retorna um produto especificado pelo id.")
    @GetMapping("/produto/{id}")
    public ResponseEntity<Produto> buscarProdutoId(@PathVariable(value = "id") long id) {
        Produto produto = produtoRepository.findById(id);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produto);
    }

    @ApiOperation(value = "Adiciona um produto na tabela.")
    @PostMapping("/produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto addProduto(@Valid @RequestBody Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @ApiOperation(value = "Atualiza os valores de um produto.")
    @PutMapping("/produto/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable long id, @Valid @RequestBody Produto produto) {
        Produto existente = produtoRepository.findById(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(produto, existente, "id");

        existente = produtoRepository.save(existente);

        return ResponseEntity.ok(existente);

    }

    @ApiOperation(value = "Deleta um produto especificado pelo id.")
    @DeleteMapping("/produto/{id}")
    public ResponseEntity<Void> deletarProdutoId(@PathVariable long id) {
        Produto produto = this.produtoRepository.findById(id);

        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        this.produtoRepository.delete(produto);

        return ResponseEntity.noContent().build();
    }
}
