package ru.sberbank.sberconvert;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import ru.sberbank.sberconvert.parser.SimpleDateTransformer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by kenneth on 30.05.17.
 */

public class SimpleDateTransformerTest {

    private SimpleDateTransformer transformer;

    @Before
    public void setup(){
        transformer = new SimpleDateTransformer();
    }

    @Test
    public void testRead() throws Exception {
        assertTrue(transformer.read("29.05.2017") instanceof Date);
    }

    @Test
    public void testWrite() throws Exception {
        Calendar calendar = Calendar.getInstance();

        //месяц считается с 0
        calendar.set(2017, 4, 29, 23, 59, 59);
        Date date = calendar.getTime();
        assertEquals("29.05.2017", transformer.write(date));
    }
}
