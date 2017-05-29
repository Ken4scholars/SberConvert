package ru.sberbank.sberconvert;


import org.junit.Before;
import org.junit.Test;

import ru.sberbank.sberconvert.parser.CommaDoubleTransformer;

import static junit.framework.Assert.assertEquals;

/**
 * Created by kenneth on 29.05.17.
 */

public class CommaDoubleTransformerTest {

    private CommaDoubleTransformer transformer;

    @Before
    public void setUp(){
        transformer = new CommaDoubleTransformer();
    }

    @Test
    public void testRead() throws Exception {
        assertEquals(3.14, transformer.read("3,14"));
    }

    @Test
    public void testWrite() throws Exception {
        assertEquals( "3,14", transformer.write(3.14));
    }


}
