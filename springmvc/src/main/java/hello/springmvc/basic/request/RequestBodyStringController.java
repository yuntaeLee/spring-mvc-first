package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("Ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("Ok");
    }

    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회
     * - 요청 파라미터를 조회하는 기능과 관계없음 (ex. @RequestParam, @ModelAttribute)
     *
     * HttpEntity 는 응답에도 사용가능
     * - 메시지 바디 정보 직접 반환
     * - 헤더 정보 포함 가능
     * - view 조회 X
     *
     * HttpEntity 를 상속받은 다음 객체들도 같은 기능 제공
     * RequestEntity: HttpMethod, url 정보가 추가, 요청에서 사용
     * ResponseEntity: HTTP 상태 코드 설정 가능, 응답에서 사용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("Ok");
    }

    /**
     * @RequestBody
     * - HTTP 메시지 바디 정보를 편리하게 조회할 수 있다.
     * - 만약 헤더 정보가 필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.
     *
     * @ReponseBody 를 사용하면 응답결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다. (view 를 사용하지 않음)
     *
     * 요청파라미터 vs HTTP 메시지 바디
     * - 요청 파라미터를 조회하는 기능:    @RequestParam, @ModelAttribute
     * - HTTP 메시지 바디를 조회하는 기능: @RequestBody
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);

        return "Ok";
    }
}
