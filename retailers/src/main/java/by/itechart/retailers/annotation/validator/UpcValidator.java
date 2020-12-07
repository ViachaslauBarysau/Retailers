package by.itechart.retailers.annotation.validator;

import by.itechart.retailers.annotation.Upc;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpcValidator implements ConstraintValidator<Upc, Long> {
    @Override
    public boolean isValid(Long upc, ConstraintValidatorContext context) {
        final long LARGEST_POSSIBLE_UPC_CODE = 999999999999L;
        long codeValue = upc;
        if ((codeValue > 0) && (codeValue < LARGEST_POSSIBLE_UPC_CODE)) {
            // Get the individual digits of the UPC code, in reverse order
            int n12 = (int) (codeValue % 10);
            codeValue /= 10;
            int n11 = (int) (codeValue % 10);
            codeValue /= 10;
            int n10 = (int) (codeValue % 10);
            codeValue /= 10;
            int n9 = (int) (codeValue % 10);
            codeValue /= 10;
            int n8 = (int) (codeValue % 10);
            codeValue /= 10;
            int n7 = (int) (codeValue % 10);
            codeValue /= 10;
            int n6 = (int) (codeValue % 10);
            codeValue /= 10;
            int n5 = (int) (codeValue % 10);
            codeValue /= 10;
            int n4 = (int) (codeValue % 10);
            codeValue /= 10;
            int n3 = (int) (codeValue % 10);
            codeValue /= 10;
            int n2 = (int) (codeValue % 10);
            codeValue /= 10;
            int n1 = (int) (codeValue % 10);
           // codeValue /= 10;
            int oddSum = n1 + n3 + n5 + n7 + n9 + n11;
            int evenSum = n2 + n4 + n6 + n8 + n10;
            int calcCheckDigit = (10 - ((evenSum + 3 * oddSum) % 10)) % 10;//здесь на гите поправка была
            return calcCheckDigit == n12;
        } else {
            return false;
        }

    }
}
