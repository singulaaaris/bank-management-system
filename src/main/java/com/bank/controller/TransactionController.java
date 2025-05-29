package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String listUserTransactions(Model model, Principal principal) {
        String username = principal.getName();
        List<Transaction> transactions = transactionService.getTransactionsByUsername(username);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

    @GetMapping("/transactions/pdf")
    public void downloadTransactionsPdf(HttpServletResponse response, Principal principal) throws IOException, DocumentException {
        String username = principal.getName();
        List<Transaction> transactions = transactionService.getTransactionsByUsername(username);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Transaction history", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("User:: " + username));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Data");
        table.addCell("Type");
        table.addCell("Sum");

        for (Transaction tx : transactions) {
            table.addCell(tx.getTimestamp().toString());
            table.addCell(tx.getTransactionType());
            table.addCell(String.valueOf(tx.getAmount()));
        }

        document.add(table);
        document.close();
    }
    @GetMapping("/transactions/filter")
    public String filterTransactions(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        List<Transaction> filtered = transactionService.getTransactionsByUsernameAndDateRange(username, startDate, endDate);
        model.addAttribute("transactions", filtered);
        return "transactions";
    }

}
