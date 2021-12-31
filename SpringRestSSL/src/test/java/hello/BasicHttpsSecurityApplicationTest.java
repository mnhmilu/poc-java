package hello;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BasicHttpsSecurityApplicationTest {

	private SSLContext defaultContext;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Before
	public void setUp() throws Exception {
		this.defaultContext = SSLContext.getDefault();
	}

	@After
	public void reset() throws Exception {
		SSLContext.setDefault(this.defaultContext);
	}

	@Test
	public void testAuth() throws Exception {

		TestRestTemplate restTemplate = new TestRestTemplate();
		restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(
				HttpClients.custom().setSSLSocketFactory(socketFactory()).build()));

		ResponseEntity<String> httpsEntity = restTemplate.getForEntity("https://localhost:8443/greeting",
				String.class);

		assertThat(httpsEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(httpsEntity.getBody()).containsIgnoringCase("nahid");

	}

	private SSLConnectionSocketFactory socketFactory() throws Exception {
		char[] password = "changeit".toCharArray();
		//KeyStore truststore = KeyStore.getInstance("PKCS12");
		//truststore.load(new ClassPathResource("cid.p12").getInputStream(), password);
		
		KeyStore truststore = KeyStore.getInstance("JKS");
		truststore.load(new ClassPathResource("truststore.jks").getInputStream(), password);
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadKeyMaterial(truststore, password);
		builder.loadTrustMaterial(truststore, new TrustSelfSignedStrategy());
		return new SSLConnectionSocketFactory(builder.build(), new NoopHostnameVerifier());
	}

}
