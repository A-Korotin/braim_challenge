package org.simbir_soft.braim_challenge;

import org.junit.jupiter.api.Test;
import org.simbir_soft.braim_challenge.validation.internal.Point;
import org.simbir_soft.braim_challenge.validation.internal.Segment;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BraimChallengeApplicationTests {

    @Test
    void contextLoads() throws Throwable {
        Point start = new Point(0, 0),
                end = new Point(50, 0);

        Segment segment = new Segment(start, end);

        Point p = new Point(25, 0);
        Point far = new Point(25, 1000);
        Segment s = new Segment(p, far);
        System.out.println(s.hasIntersection(s,false));
    }

}
