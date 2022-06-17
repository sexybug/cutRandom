import lombok.extern.slf4j.Slf4j;
import org.example.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestMain {
    @Test
    public void testMain() {
        Main.main(new String[]{});
        assertEquals("Hello world!", "Hello world!");
    }

    @Test
    public void testCutOne() {
        assertEquals(0, Main.cutOne("samples/1.bin", Main.getZonedDateTime() + ".bin", 125000));
    }

    @Test
    public void testGetZonedDateTime() {
        assertEquals("20190101000000", Main.getZonedDateTime());
        log.debug("testGetZonedDateTime success");
    }

    @Test
    public void testGetFileName() {
        assertEquals("[1.bin, 2.bin]", Main.getAllFileName("samples/").toString());
    }

    @Test
    public void testCutAll() {
        assertEquals(0, Main.cutAll("samples/", 125000));
        log.debug("testCutAll success");
    }

}
