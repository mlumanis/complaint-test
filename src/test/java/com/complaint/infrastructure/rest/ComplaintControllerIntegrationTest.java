package com.complaint.infrastructure.rest;

import com.complaint.BaseIntegrationTest;
import com.complaint.infrastructure.config.TestConfig;
import com.complaint.infrastructure.rest.dto.ComplaintResponse;
import com.complaint.infrastructure.rest.dto.CreateComplaintRequest;
import com.complaint.infrastructure.rest.dto.UpdateComplaintRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class ComplaintControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateComplaint() {
        // given
        var request = new CreateComplaintRequest(
                "PROD123",
                "Product is defective",
                "john.doe@example.com"
        );
        mockGeoLocationApi("127.0.0.1", "Poland");

        // when
        ResponseEntity<ComplaintResponse> response = restTemplate.postForEntity(
                "/api/complaints",
                request,
                ComplaintResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().productId()).isEqualTo("PROD123");
        assertThat(response.getBody().content()).isEqualTo("Product is defective");
        assertThat(response.getBody().reportedBy()).isEqualTo("john.doe@example.com");
        assertThat(response.getBody().ipAddress()).isEqualTo("127.0.0.1");
        assertThat(response.getBody().counter()).isEqualTo(1);
    }

    @Test
    void shouldIncrementCounterForDuplicateComplaint() {
        // given
        var request = new CreateComplaintRequest(
                "PROD456",
                "Another defect",
                "jane.doe@example.com"
        );
        mockGeoLocationApi("127.0.0.1", "Germany");

        // when
        // First complaint
        ResponseEntity<ComplaintResponse> firstResponse = restTemplate.postForEntity(
                "/api/complaints",
                request,
                ComplaintResponse.class
        );

        // Second complaint with same productId and reporter
        ResponseEntity<ComplaintResponse> secondResponse = restTemplate.postForEntity(
                "/api/complaints",
                request,
                ComplaintResponse.class
        );

        // then
        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(secondResponse.getBody()).isNotNull();
        assertThat(secondResponse.getBody().counter()).isEqualTo(2);
        assertThat(secondResponse.getBody().id()).isEqualTo(firstResponse.getBody().id());
    }

    @Test
    void shouldUpdateComplaintContent() {
        // given
        var createRequest = new CreateComplaintRequest(
                "PROD789",
                "Initial content",
                "alice@example.com"
        );
        mockGeoLocationApi("127.0.0.1", "France");

        var response = restTemplate.postForEntity(
                "/api/complaints",
                createRequest,
                ComplaintResponse.class
        );
        assertThat(response.getBody()).isNotNull();

        var updateRequest = new UpdateComplaintRequest("Updated content");

        // when
        ResponseEntity<ComplaintResponse> updateResponse = restTemplate.exchange(
                "/api/complaints/" + response.getBody().id(),
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                ComplaintResponse.class
        );

        // then
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().content()).isEqualTo("Updated content");
    }

    @Test
    void shouldGetAllComplaints() {
        // given
        var request1 = new CreateComplaintRequest(
                "PROD111",
                "First complaint",
                "user1@example.com"
        );
        var request2 = new CreateComplaintRequest(
                "PROD222",
                "Second complaint",
                "user2@example.com"
        );
        mockGeoLocationApi("127.0.0.1", "Spain");

        restTemplate.postForEntity("/api/complaints", request1, ComplaintResponse.class);
        restTemplate.postForEntity("/api/complaints", request2, ComplaintResponse.class);

        // when
        ResponseEntity<ComplaintResponse[]> response = restTemplate.getForEntity(
                "/api/complaints",
                ComplaintResponse[].class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        List<ComplaintResponse> complaints = List.of(response.getBody());
        assertThat(complaints).hasSizeGreaterThanOrEqualTo(2);
    }
} 