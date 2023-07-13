import com.ecommerce.tracker.publisher.Driver;
import com.ecommerce.tracker.publisher.entities.Event;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DriverTest {

    @Test
    public void testGenerateEventsSize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodGenerateEvents = Driver.class.getDeclaredMethod("generateEvents");
        methodGenerateEvents.setAccessible(true);
        Driver driver = new Driver(100);
        List<Event> events = (List<Event>) methodGenerateEvents.invoke(driver);
        Assert.assertTrue(events.size()<7);

    }
}
