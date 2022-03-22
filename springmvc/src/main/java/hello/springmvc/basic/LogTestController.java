package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Slf4j
// @Controller 는 반환 값이 String 이면 뷰 이름으로 인식하고 뷰를 찾은 후 랜더링 한다.
// @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메세지 바디에 바로 입력한다.
@RestController
public class LogTestController {

    // getClass() == LogTestController.class
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        // log level: TRACE > DEBUG > INFO > WARN > ERROR
        // 개발 서버: DEBUG
        // 운영 서버: INFO
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
