package xyz.jeevan.finnops.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.jeevan.finnops.domain.Statistics;
import xyz.jeevan.finnops.domain.Transaction;
import xyz.jeevan.finnops.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

  private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

  @Autowired
  private TransactionService transactionService;

  @PostMapping(path = "/transactions")
  public ResponseEntity<Object> create(@RequestBody @Valid Transaction transaction) {
    log.info("Create new transaction.");
    transactionService.createNew(transaction);
    return new ResponseEntity<>(null, HttpStatus.CREATED);
  }

  @GetMapping(path = "/statistics")
  public ResponseEntity<Statistics> statistics() {
    log.info("Publish the statistics.");
    return new ResponseEntity<>(transactionService.getLastMinuteTransactionStats().join(),
        HttpStatus.OK);
  }
}
