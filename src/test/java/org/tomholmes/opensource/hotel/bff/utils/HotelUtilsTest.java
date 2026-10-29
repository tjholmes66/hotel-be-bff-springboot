package org.tomholmes.opensource.hotel.bff.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HotelUtilsTest
{

    @Test
    public void testGenerateConfirmationCode()
    {
        int length = 12;
        String randomNumber = HotelUtils.generateConfirmationCode(length);
        assertNotNull(randomNumber);
        System.out.println("Reservation Number: " + randomNumber);
    }

}
