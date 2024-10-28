package com.masteringgraphql.accounts.entity


import com.masteringgraphql.accounts.domain.Currency
import jakarta.persistence.*

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Account", uniqueConstraints = [UniqueConstraint(columnNames = ["accountId"])])
class BankAccount {

    @Id
    @Column(name = "account_id")
    var id: Int? = null

    @Column
    var clientId: Long? = null

    @Column
    var currency: Currency? = null

    @Column
    var balance: Float? = null

    @Column
    var status: String? = null
}

