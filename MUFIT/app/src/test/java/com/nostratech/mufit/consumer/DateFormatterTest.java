package com.nostratech.mufit.consumer;

import com.nostratech.mufit.consumer.utils.date.DateFormatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateFormatterTest {

    private DateFormatter dateFormatter;

    @Before
    public void setup(){
        dateFormatter = new DateFormatter();
    }

    @Test
    public void beautifyTime_isCorrect(){
        String result = dateFormatter.beautifyTime("01:00:00", ":");
        Assert.assertEquals("01.00", result);
    }

}
