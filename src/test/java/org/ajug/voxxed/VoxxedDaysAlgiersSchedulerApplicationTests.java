package org.ajug.voxxed;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VoxxedDaysAlgiersSchedulerApplication.class)
@WebAppConfiguration("server.port:9000")
public class VoxxedDaysAlgiersSchedulerApplicationTests {

	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	public void contextLoads() {
		final HttpEntity<String> parameters = new HttpEntity<>("parameters", buildHttpHeaders());
		final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:9000/hello", HttpMethod.GET, parameters, String.class);
		Assertions.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		Assertions.assertThat(responseEntity.getHeaders().getAccept()).isNotEmpty().containsExactly(buildHttpHeaders());
		Assertions.assertThat(responseEntity.getBody()).isNotEmpty().isEqualTo("Hello there !");
	}

	private HttpHeaders buildHttpHeaders() {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
		return httpHeaders;
	}

}
