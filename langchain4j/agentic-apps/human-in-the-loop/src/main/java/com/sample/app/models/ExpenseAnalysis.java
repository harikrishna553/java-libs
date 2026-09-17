package com.sample.app.models;

public record ExpenseAnalysis(
        String item,
        double amount,
        String category,
        String riskLevel,
        String justification
) {
}