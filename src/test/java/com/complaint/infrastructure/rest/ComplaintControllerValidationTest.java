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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class ComplaintControllerValidationTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldRejectComplaintWithInvalidProductId() {
        // given
        var request = new CreateComplaintRequest(
                "invalid-product-id",
                "Valid content that is long enough",
                "valid@email.com"
        );

        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.postForEntity(
                "/api/complaints",
                request,
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors())
                .containsKey("productId")
                .containsValue("Product ID must start with 'PROD' followed by 3-6 digits");
    }

    @Test
    void shouldRejectComplaintWithShortContent() {
        // given
        var request = new CreateComplaintRequest(
                "PROD123",
                "Too short",
                "valid@email.com"
        );

        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.postForEntity(
                "/api/complaints",
                request,
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors())
                .containsKey("content")
                .containsValue("Content must be between 10 and 1000 characters");
    }

    @Test
    void shouldRejectComplaintWithInvalidEmail() {
        // given
        var request = new CreateComplaintRequest(
                "PROD123",
                "Valid content that is long enough",
                "invalid-email"
        );

        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.postForEntity(
                "/api/complaints",
                request,
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors())
                .containsKey("reportedBy")
                .containsValue("Invalid email format");
    }

    @Test
    void shouldRejectUpdateWithShortContent() {
        // given
        var createRequest = new CreateComplaintRequest(
                "PROD123",
                "Initial valid content that is long enough",
                "valid@email.com"
        );
        mockGeoLocationApi("127.0.0.1", "Poland");

        var createResponse = restTemplate.postForEntity(
                "/api/complaints",
                createRequest,
                ComplaintResponse.class
        );
        assertThat(createResponse.getBody()).isNotNull();

        var updateRequest = new UpdateComplaintRequest("Too short");

        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.exchange(
                "/api/complaints/" + createResponse.getBody().id(),
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors())
                .containsKey("content")
                .containsValue("Content must be between 10 and 1000 characters");
    }

    @Test
    void shouldRejectGetComplaintWithBlankId() {
        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.getForEntity(
                "/api/complaints/ ",
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors())
                .containsKey("id")
                .containsValue("Complaint ID cannot be blank");
    }

    @Test
    void shouldReturn404ForNonExistentComplaint() {
        // when
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = restTemplate.getForEntity(
                "/api/complaints/non-existent-id",
                GlobalExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Complaint not found");
        assertThat(response.getBody().errors()).containsKey("error");
        assertThat(response.getBody().errors().get("error")).contains("non-existent-id");
    }
} 