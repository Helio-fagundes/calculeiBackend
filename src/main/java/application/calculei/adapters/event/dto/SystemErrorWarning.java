package application.calculei.adapters.event.dto;

import jakarta.servlet.http.HttpServletRequest;

public record SystemErrorWarning(Exception ex, HttpServletRequest request) {
}
