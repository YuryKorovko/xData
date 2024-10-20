package com.test.xdata.service

import com.test.xdata.entity.User
import com.test.xdata.model.dto.UserDto
import com.test.xdata.model.dto.toDto
import com.test.xdata.model.request.RegistrationRequest
import com.test.xdata.repository.UserRepository
import com.test.xdata.repository.WalletRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository
) {
    @Transactional
    suspend fun register(registrationRequest: RegistrationRequest): UserDto? {
        return userRepository.save(User.new(registrationRequest.email, registrationRequest.password))
            .flatMap { it ->
                mono {
                    UserDto(
                        it.id,
                        it.email,
                        wallets = walletRepository.findAllByUserId(it.id)
                            .map { it.toDto() }
                            .collectList()
                            .awaitSingle()
                            .toList()
                    )
                }
            }
            .awaitSingle()
    }
}