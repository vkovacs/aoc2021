import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GreeterTest {
    Greeter greeter = new Greeter();
    @Test
    void shouldGreet() {
        assertThat(greeter.greet("Zeti"), is("Hello World Zeti"));
    }
}