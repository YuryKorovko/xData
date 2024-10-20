package com.test.xdata.validation

import com.test.xdata.entity.CurrencyEnum
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Constraint(validatedBy = [AllowedCurrencyValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class CryptoCurrency(
    val message: String
)

@Component
class AllowedCurrencyValidator: ConstraintValidator<CryptoCurrency, CurrencyEnum> {
    override fun isValid(value: CurrencyEnum?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return false
        return CurrencyEnum.CRYPTO_CURRENCIES.contains(value)
    }
}
