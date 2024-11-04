package com.masteringgraphql.accounts.service

import com.masteringgraphql.accounts.domain.Client
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors


@Service
class BankService {
    private var clients : List<Client> = Arrays.asList<Client>(
        Client("C100" , 100 , "John" , "T." , "Doe") ,
        Client("C101" , 101 , "Emma" , "B." , "Smith") ,
        Client("C102" , 102 , "James" , "R." , "Brown") ,
        Client("C103" , 103 , "Olivia" , "S." , "Johnson") ,
        Client("C104" , 100 , "William" , "K." , "Jones")
    )

    fun getClients(accountIds : Int?) : MutableList<Any>? {
        return clients.stream().filter { c : Client -> c.accountId.equals(accountIds) }
            .collect(Collectors.toList<Any>())
    }
}


