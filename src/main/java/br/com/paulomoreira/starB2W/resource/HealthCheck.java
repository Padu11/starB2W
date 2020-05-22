package br.com.paulomoreira.starB2W.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.Getter;

@RestController
@RequestMapping(path = "/health")
@Api(value = "/v1", tags = "Operations about Health Check.")
@Getter
public class HealthCheck {

		@GetMapping
		public ResponseEntity<?> healthcheck() {
			
			Map<String, String> status = new HashMap<String, String>();
			status.put("Status", "UP");
			return ResponseEntity.ok(status);
		}

	}

