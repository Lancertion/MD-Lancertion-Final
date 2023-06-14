package com.app.lancertion.domain.use_case.auth_logout

import com.app.lancertion.common.Resource
import com.app.lancertion.domain.repository.LancertionAuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: LancertionAuthRepository
) {
    operator fun invoke() = flow<Resource<Unit>> {

    }
}