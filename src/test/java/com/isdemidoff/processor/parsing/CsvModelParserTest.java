package com.isdemidoff.processor.parsing;

import com.isdemidoff.processor.conf.RootTest;
import com.isdemidoff.processor.model.Currency;
import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class CsvModelParserTest extends RootTest {

    // It was @Component, so we deal with it as usual
    private final ModelParser csvModelParser = new CsvModelParser();

    //-------------------
    // Correctness tests
    //-------------------

    @Test
    public void correctCsv() throws ParseException {
        String csv = "1,100,USD,оплата заказа";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void orderIdIsNumberString() throws ParseException {
        String csv = "\"1\",100,USD,оплата заказа";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void amountIsNumberString() throws ParseException {
        String csv = "1,\"100\",USD,оплата заказа";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void currencyIsLowercase() throws ParseException {
        String csv = "1,100,usd,оплата заказа";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void commentIsNull() throws ParseException {
        String csv = "1,100,USD,";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("", model.getComment());
    }

    @Test
    public void commentIsEmpty() throws ParseException {
        String csv = "1,100,USD,\"\"";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("", model.getComment());
    }

    @Test
    public void commentIsEscaped() throws ParseException {
        String csv = "1,100,USD,\"Hello, world!\"";
        InputModel model = csvModelParser.parseString(csv);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("Hello, world!", model.getComment());
    }

    //------------------------------
    // Incorrect dto format section
    //------------------------------

    @Test
    public void tooFewColumns() throws ParseException {
        String csv = "1,100,USD";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should say about columns
            Assert.assertTrue(e.getMessage().contains("Incorrect number of columns"));
        }
    }

    @Test
    public void tooMuchColumns() throws ParseException {
        String csv = "1,100,USD,hello,world";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should say about columns
            Assert.assertTrue(e.getMessage().contains("Incorrect number of columns"));
        }
    }

    @Test
    public void unclosedEscape() throws ParseException {
        String csv = "1,100,USD,\"hello world!";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should say about csv format
            Assert.assertTrue(e.getMessage().contains("Incorrect comma-separated format"));
        }
    }

    @Test
    public void unopenedEscape() throws ParseException {
        String csv = "1,100,USD,hello people!\"";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should say about csv format
            Assert.assertTrue(e.getMessage().contains("Incorrect comma-separated format"));
        }
    }

    //---------------------------
    // Incorrect orderId section
    //---------------------------

    @Test
    public void orderIdIsNull() {
        String csv = ",100,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect orderId"));
        }
    }

    @Test
    public void orderIdIsOverflowed() {
        String csv = "1000000000000000000000000000,100,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect orderId"));
        }
    }

    @Test
    public void orderIdIsString() {
        String csv = "hello,100,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect orderId"));
        }
    }

    @Test
    public void orderIdIsBoolean() {
        String csv = "false,100,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect orderId"));
        }
    }

    @Test
    public void orderIdIsDouble() {
        String csv = "1.2,100,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect orderId"));
        }
    }

    @Test
    public void orderIdIsDoubleAsString() {
        String csv = "\"1.43\",100,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect orderId"));
        }
    }

    //--------------------------
    // Incorrect amount section
    //--------------------------

    @Test
    public void amountIsNull() {
        String csv = "1,,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect amount"));
        }
    }

    @Test
    public void amountIsOverflowed() {
        String csv = "1,1000000000000000000000000000,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect amount"));
        }
    }

    @Test
    public void amountIsString() {
        String csv = "1,hi!,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect amount"));
        }
    }

    @Test
    public void amountIsBoolean() {
        String csv = "1,true,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect amount"));
        }
    }

    @Test
    public void amountIsDouble() {
        String csv = "1,123.12,USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect amount"));
        }
    }

    @Test
    public void amountIsDoubleAsString() {
        String csv = "1,\"100.8632\",USD,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect amount"));
        }
    }

    //----------------------------
    // Incorrect currency section
    //----------------------------

    @Test
    public void currencyIsNumber() {
        String csv = "1,100,1,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect currency"));
        }
    }

    @Test
    public void currencyIsDouble() {
        String csv = "1,100,123.2,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect currency"));
        }
    }

    @Test
    public void currencyIsNotInEnum() {
        String csv = "1,100,WWW,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect currency"));
        }
    }

    @Test
    public void currencyIsEmptyString() {
        String csv = "1,100,\"\",оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect currency"));
        }
    }

    @Test
    public void currencyIsNull() {
        String csv = "1,100,,оплата заказа";
        try {
            InputModel model = csvModelParser.parseString(csv);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Incorrect currency"));
        }
    }

    //---------------------------
    // Incorrect comment section
    //---------------------------

    /*
    I have no idea what comment could be wrong
     */

}