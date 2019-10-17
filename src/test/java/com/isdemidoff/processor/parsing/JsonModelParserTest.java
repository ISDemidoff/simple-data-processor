package com.isdemidoff.processor.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isdemidoff.processor.conf.RootTest;
import com.isdemidoff.processor.model.Currency;
import com.isdemidoff.processor.model.InputModel;
import com.isdemidoff.processor.model.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class JsonModelParserTest extends RootTest {

    // We got same objectMapper as it will be in real runtime!
    private final ObjectMapper mapper = beanCreator.objectMapper();

    // It was @Component, so we deal with it as usual
    private final ModelParser jsonModelParser = new JsonModelParser(mapper);

    //-------------------
    // Correctness tests
    //-------------------

    @Test
    public void correctJson() throws ParseException {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        InputModel model = jsonModelParser.parseString(json);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void orderIdIsNumberString() throws ParseException {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        InputModel model = jsonModelParser.parseString(json);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void amountIsNumberString() throws ParseException {
        String json = "{\"orderId\":\"1\",\"amount\":\"100\",\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        InputModel model = jsonModelParser.parseString(json);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void currencyIsLowercase() throws ParseException {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"usd\",\"comment\":\"оплата заказа\"}";
        InputModel model = jsonModelParser.parseString(json);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("оплата заказа", model.getComment());
    }

    @Test
    public void commentIsEmpty() throws ParseException {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"USD\",\"comment\":\"\"}";
        InputModel model = jsonModelParser.parseString(json);

        Assert.assertEquals(1L, model.getOrderId());
        Assert.assertEquals(100L, model.getAmount());
        Assert.assertEquals(Currency.USD, model.getCurrency());
        Assert.assertEquals("", model.getComment());
    }

    //------------------------------
    // Incorrect dto format section
    //------------------------------

    @Test
    public void tooFewFields() throws ParseException {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"USD\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("'comment'"));
        }
    }

    @Test
    public void tooMuchFields() throws ParseException {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\",\"field\":\"some value\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("Unrecognized field"));
        }
    }

    //---------------------------
    // Incorrect orderId section
    //---------------------------

    @Test
    public void orderIdIsNull() {
        String json = "{\"orderId\":null,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"orderId\"]"));
        }
    }

    @Test
    public void orderIdIsOverflowed() {
        String json = "{\"orderId\":1000000000000000000000000000,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"orderId\"]"));
        }
    }

    @Test
    public void orderIdIsString() {
        String json = "{\"orderId\":\"Some str!\",\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"orderId\"]"));
        }
    }

    @Test
    public void orderIdIsBoolean() {
        String json = "{\"orderId\":false,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"orderId\"]"));
        }
    }

    @Test
    public void orderIdIsDouble() {
        String json = "{\"orderId\":2.2,\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"orderId\"]"));
        }
    }

    @Test
    public void orderIdIsDoubleAsString() {
        String json = "{\"orderId\":\"2.2\",\"amount\":100,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"orderId\"]"));
        }
    }

    //--------------------------
    // Incorrect amount section
    //--------------------------

    @Test
    public void amountIsNull() {
        String json = "{\"orderId\":1,\"amount\":null,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"amount\"]"));
        }
    }

    @Test
    public void amountIsOverflowed() {
        String json = "{\"orderId\":1,\"amount\":1000000000000000000000000000,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"amount\"]"));
        }
    }

    @Test
    public void amountIsString() {
        String json = "{\"orderId\":1,\"amount\":\"string\",\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"amount\"]"));
        }
    }

    @Test
    public void amountIsBoolean() {
        String json = "{\"orderId\":1,\"amount\":true,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"amount\"]"));
        }
    }

    @Test
    public void amountIsDouble() {
        String json = "{\"orderId\":1,\"amount\":100.31,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"amount\"]"));
        }
    }

    @Test
    public void amountIsDoubleAsString() {
        String json = "{\"orderId\":1,\"amount\":\"100.53\",\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"amount\"]"));
        }
    }

    //----------------------------
    // Incorrect currency section
    //----------------------------

    @Test
    public void currencyIsNumber() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":1,\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"currency\"]"));
        }
    }

    @Test
    public void currencyIsDouble() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":1.3,\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"currency\"]"));
        }
    }

    @Test
    public void currencyIsNotEscaped() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":EUR,\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("'EUR'"));
        }
    }

    @Test
    public void currencyIsNotInEnum() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"WWW\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"currency\"]"));
        }
    }

    @Test
    public void currencyIsEmptyString() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"\",\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("[\"currency\"]"));
        }
    }

    @Test
    public void currencyIsNull() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":null,\"comment\":\"оплата заказа\"}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("'currency'") && e.getMessage().contains("Null"));
        }
    }

    //---------------------------
    // Incorrect comment section
    //---------------------------

    @Test
    public void commentIsNull() {
        String json = "{\"orderId\":1,\"amount\":100,\"currency\":\"USD\",\"comment\":null}";
        try {
            InputModel model = jsonModelParser.parseString(json);
            Assert.fail();
        } catch (ParseException e) {
            // Should blame on orderId
            Assert.assertTrue(e.getMessage().contains("'comment'"));
        }
    }

    /*
    I'm not sure whether I should off coercing values or not.
    May be there is would be more precise solution to write our own deseralizer for InputModel.
     */

}