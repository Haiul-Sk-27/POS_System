package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.payload.dto.BillingDto;
import com.StoreHub.StoreHub.pos.system.payload.dto.OrderDto;
import com.StoreHub.StoreHub.pos.system.payload.dto.OrderItemDto;
import com.StoreHub.StoreHub.pos.system.service.BillingService;
import com.StoreHub.StoreHub.pos.system.service.EmailService;
import com.StoreHub.StoreHub.pos.system.service.OrderService;
import com.StoreHub.StoreHub.pos.system.utility.BillingPdfGenerator;
import com.StoreHub.StoreHub.pos.system.utility.FileStorageUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final OrderService orderService;
    private final EmailService emailService;
    private static final double GST_PERCENT = 18.0;
    @Override
    public BillingDto generateBill(Long orderId) throws Exception {
        OrderDto order = orderService.getOrderById(orderId);
        List<OrderItemDto> items = order.getItems();

        double subTotal = calculateSubtotal(items);
        double taxAmount = calculateTax(subTotal);
        double discount = calculateDiscount(subTotal);

        double totalPayable = subTotal + taxAmount - discount;

        return BillingDto.builder()
                .orderId(order.getId())
                .branchId(order.getBranchId())
                .customerId(order.getCustomerId())
                .customerName(order.getCustomerName() != null ? order.getCustomerName() : null)
                .customerEmail(order.getCustomerEmail()!= null ? order.getCustomerEmail() : null)
                .items(items)
                .subTotal(subTotal)
                .taxAmount(taxAmount)
                .discount(discount)
                .totalPayable(totalPayable)
                .paymentType(order.getPaymentType())
                .billedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void generateBillAndSendEmail(Long orderId) throws Exception {

        BillingDto bill = generateBill(orderId);

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        String userPrefix = userId.length() >= 4
                ?userId.substring(0,4)
                :userId;
        String password = userPrefix + bill.getCustomerId().toString();

        byte[] pdf = BillingPdfGenerator.generate(bill, password);
        FileStorageUtil.savePdf(pdf, "bill_" + bill.getOrderId() + ".pdf");

        System.out.println("Customer Email = " + bill.getCustomerEmail());

        emailService.sendEmailWithAttachment(
                bill.getCustomerEmail(),
                "Your StoreHub Invoice – Order #" + bill.getOrderId(),
                "Dear " + bill.getCustomerName() + ",\n\n"
                        + "Thank you for shopping with StoreHub.\n\n"
                        + "Please find attached the invoice for your recent order "
                        + "(Order ID: " + bill.getOrderId() + ").\n\n"
                        + "🔐 PDF Password: First 4 letters of your username followed by your Customer ID\n"
                        + "Example: John + 1025 → John1025\n\n"
                        + "If you need any help, please contact our support team.\n\n"
                        + "Warm regards,\n"
                        + "StoreHub Support Team",
                pdf,
                "StoreHub_Invoice_" + bill.getOrderId() + ".pdf"
        );
        // 3️⃣ Send email (attachment)
        emailService.sendEmailWithAttachment(
                bill.getCustomerEmail(),
                "Your StoreHub Bill - Order #" + bill.getOrderId(),
                "PDF Password: " + password,
                pdf,
                "bill_" + bill.getOrderId() + ".pdf"
        );
    }

    private double calculateSubtotal(List<OrderItemDto> items){
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    private double calculateTax(double subTotal) {
        return (subTotal * GST_PERCENT) / 100;
    }

    private double calculateDiscount(double subTotal) {
        if (subTotal >= 5000) {
            return subTotal * 0.10;
        }
        if (subTotal >= 2000) {
            return subTotal * 0.05;
        }
        return 0;
    }
}
