package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("Ok");
    }

    @ResponseBody // @RestController 와 같은 효과
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "Ok";
    }

    // Http Parameter 이름이 변수 이름과 같으면 @RequestParam(name = "xx") 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);
        return "Ok";
    }

    // String, int, Integer 등의 단순 타입이면 @RequestParam 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "Ok";
    }

    /**
     * required 는 파라미터 필수 여부
     * required default value = false
     * required 가 true 면 오류가 발생하지 않는다.
     * 파라미터 이름만 있고 값이 없는 경우 --> 빈문자로 통과
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {

        log.info("username={}, age={}", username, age);
        return "Ok";
    }

    /**
     * defaultValue 는 빈문자의 경우에도 설정한 기본 값이 적용된다.
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);
        return "Ok";
    }

    /**
     * 파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만,
     * 그렇지 않다면, MultiValueMap 사용
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "Ok";
    }

    /**
     * @ModelAttribute 가 있으면 HelloData 객체를 생성하하고,
     * 요청 파라미터 이름으로 HelloData 객체의 프로퍼티를 찾는다.
     * 그리고 해당 프로퍼티의 setter 를 호출해서 파라미터의 값을 바인딩한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "Ok";
    }

    /**
     * String, int, Integer --> @RequestParam
     * 나머지 --> @ModelAttribute (argument resolver 로 지정해둔 타입 제외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "Ok";
    }
}
