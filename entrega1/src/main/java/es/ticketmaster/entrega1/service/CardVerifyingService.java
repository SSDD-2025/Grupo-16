package es.ticketmaster.entrega1.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

/* The function of this Service will be to check if all the fields filled out by the user corresponding to their credit card, 
are valid. */
@Service
public class CardVerifyingService {

    /* Declaration of constants values. */
    private static final int DIVISIBLE_BY_TEN = 10;
    private static final int LUHN_ALGORITHM_FACTOR = 2;
    private static final int LUHN_ALGORITHM_REDUCTION = 9;
    private static final String VISA_PREFIX = "4";
    private static final int NORMAL_CVV_LENGTH = 3;
    private static final int AMERICAN_EXPRESS_CVV_LENGTH = 4;

    /**
     * This method will verify if the cardHolder is a valid name. It will be a
     * valid name if: - The name begin with letters, could contain blank spaces
     * and the length of the name must be between 2 and 50 characters. This is
     * spciefied by the regular expression shown below. - The name cannot be a
     * blank space. - Can not be null neither, however, a NullPointerException
     * will be thrown automatically by the compiler.
     *
     * @param cardHolder is the name of the card owner.
     * @return true if de cardHolder name is valid, false if not.
     */
    public boolean verifyCardHolder(String cardHolder) {
        return (cardHolder.matches("^[A-Za-z' ']{2,50}$") && (!cardHolder.isBlank()));
    }

    /**
     * In order to verify the card number, the procedure to follow is applying
     * the Luhn`s Algorithm, which help us check the card`s validity. Note: This
     * algorithm was implemented without any modifications, comparing it to the
     * original.
     *
     * @param number is the credit card number.
     * @return true if it is a valid number (if the accumulate sum is divisible
     * by 10), false if not.
     */
    public boolean verifyCreditCardNumber(String number) {
        number = number.replaceAll("\\s", "");
        /* All the blank spaces are removed, in order to verify the number itself. */
 /* The length of the number must be between 14 and 16 digits. 
        This range is established based on the typical length of any card number, such as "Visa" and "Master Card".
        However, the "American Express" card is 15 digits long, 
        That's why the range is between 15 and 16 digits.*/
        if (!number.matches("\\d{15,16}")) {
            return false;
        } else {
            int accumulateSum = 0;
            /* Is the accumulate sum of all the digits in the card number,
                                   and by the end of all of this, this value must be divisible by 10.
                                   The reason why it must be divisble by 10 and not by any other number, 
                                   is due to the mathematical structure of the algorithm 
                                   and how the "checksum" is constructed to detect errors in the card numbers. */
            boolean alternate = false;
            /* Used to toggle between multiplying by 2 and not multiplying. 
                                       This is done because, in Luhn's algorithm, every second digit (starting from the end) 
                                       is multiplied by 2. */
            for (int i = number.length() - 1; i >= 0; i--) {
                int digit = Character.getNumericValue(number.charAt(i));
                /* Obtain the digit at a position i of the card number. */
                if (alternate) {
                    digit = digit * LUHN_ALGORITHM_FACTOR;
                    if (digit > LUHN_ALGORITHM_REDUCTION) {
                        digit = digit - LUHN_ALGORITHM_REDUCTION;
                    }
                }
                accumulateSum = accumulateSum + digit;
                alternate = !alternate;
            }
            return (accumulateSum % DIVISIBLE_BY_TEN == 0);
        }
    }

    /**
     * This method will verify if the card number introduced, matches with any
     * of the 3 types of cards that the user can choose (MasterCard, Visa or
     * American Express).
     *
     * @param number is the card number.
     * @return If the number match, it will return the type name of the user´s
     * card. If not, null.
     */
    public String getType(String number) {
        number = number.replaceAll("\\s", "");
        if (number.matches("5^[1-5].*")) {
            return "MasterCard";
        } else if (number.startsWith(VISA_PREFIX)) {
            return "Visa";
        } else if (number.matches("3^[47].*")) {
            return "American Express";
        } else {
            return null;
        }
    }

    /**
     * This method will verify if the expiration date introduced by the user is
     * realistic, that is, it is not a previous date compared to the date on
     * which the form is being made.
     *
     * @param date is the expiration date introduced by the user.
     * @return true if the expiration date is realistic, false (along with a
     * DateTimeParseException) if not.
     */
    public boolean verifyExpirationDate(String date) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM");
            /* Stablish the format that the date must have. */
            YearMonth expirationDateFormatted = YearMonth.parse(date, dateFormat);
            /* Transform the date into the correct format. 
            If it cannot be transform, a DateTimeParseException will be captured by the catch block. */
            return expirationDateFormatted.isAfter(YearMonth.now());
            /* If the expiration date is later than 
            the date the form is made, it will return true, otherwise it will be false. */
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * This method will verify if the card´s security number (CVV) is a valid
     * one.
     *
     * @param cvv is the security number of the user´s card.
     * @return true if is a valid format, otherwise it will be false.
     */
    public boolean verifyCVV(String cvv) {
        /* Both the "Visa" and "Master Card" have 3 digits for the cvv. However, the "American Express" one has 4 digits. */
        if ((cvv.length() == NORMAL_CVV_LENGTH) || (cvv.length() == AMERICAN_EXPRESS_CVV_LENGTH)) {
            int i = 0;
            while (i < cvv.length()) {
                if (!Character.isDigit(cvv.charAt(i))) {
                    /* If one of its number is not an integer (digit), it will return false and exit the loop. */
                    return false;
                } else {
                    /* Otherwise, it will continue verifying the cvv. */
                    i++;
                }
            }
            return true;
            /* If the loop checked all the cvv numbers, then the cvv is a valid one. */
        } else {
            return false;
        }
    }
}
