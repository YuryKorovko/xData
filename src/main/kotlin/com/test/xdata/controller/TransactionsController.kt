package com.test.xdata.controller

import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionsController : ApiV1 {

  @PostMapping("/transfer")
  suspend fun depositMoney() = coroutineScope {

  }
}