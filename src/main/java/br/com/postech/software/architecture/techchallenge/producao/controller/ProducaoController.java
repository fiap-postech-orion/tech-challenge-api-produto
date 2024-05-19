package br.com.postech.software.architecture.techchallenge.producao.controller;

import br.com.postech.software.architecture.techchallenge.producao.model.PedidoProducao;
import br.com.postech.software.architecture.techchallenge.producao.service.ProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1/producao")
public class ProducaoController {

    @Autowired
    private ProducaoService producaoService;

    //PRODUCAO
    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<PedidoProducao>> listarTodosPedidosAtivos(@RequestParam String status) throws Exception {
        return new ResponseEntity<>(producaoService.findAllByStatusPedido(status), HttpStatus.OK);
    }

    //PRODUCAO
    @GetMapping(path = "/{idPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoProducao> buscarPedido(@PathVariable Long idPedido) throws Exception {
        return new ResponseEntity<>(producaoService.findByNumeroPedido(idPedido), HttpStatus.OK);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoProducao> salvaPedido(@RequestBody PedidoProducao pedidoProducao) throws Exception {
        return new ResponseEntity<>(producaoService.salvaPedido(pedidoProducao), HttpStatus.OK);
    }
}