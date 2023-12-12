package com.sample.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import com.sample.app.enums.OrderStatus;
import com.sample.app.enums.PaymentType;
import com.sample.app.model.Order;

public class App {
	private static void printOrders(List<Order> orders, List<Integer> orderIds) {
		for (Order order : orders) {
			if (orderIds.contains(order.getOrderId())) {
				System.out.println(order);
			}
		}
	}

	private static List<Integer> getAllSetBits(BitSet bitSet) {
		List<Integer> setBits = new ArrayList<>();

		// Iterate through the set bits
		int nextSetBit = bitSet.nextSetBit(0);
		while (nextSetBit != -1) {
			setBits.add(nextSetBit);
			nextSetBit = bitSet.nextSetBit(nextSetBit + 1);
		}

		return setBits;
	}

	public static void main(String[] args) {
		Order order1 = new Order(1, 23, 1000.0, OrderStatus.SHIPPED, PaymentType.CASH);
		Order order2 = new Order(2, 43, 550.0, OrderStatus.DELIVERED, PaymentType.CASH);
		Order order3 = new Order(3, 1, 345.0, OrderStatus.RETURNED, PaymentType.ONLINE);
		Order order4 = new Order(4, 298, 10000.0, OrderStatus.CACNCELLED, PaymentType.ONLINE);
		Order order5 = new Order(5, 543, 45000.0, OrderStatus.SHIPPED, PaymentType.CASH);
		Order order6 = new Order(6, 987, 87000.0, OrderStatus.DELIVERED, PaymentType.ONLINE);
		Order order7 = new Order(7, 65, 450.0, OrderStatus.DELIVERED, PaymentType.ONLINE);

		List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5, order6, order7);

		BitSet orderShippedBitset = new BitSet();
		BitSet orderDeliverdBitset = new BitSet();
		BitSet orderReturnedBitset = new BitSet();
		BitSet orderCancelledBitset = new BitSet();
		BitSet paymentTypeCashBitset = new BitSet();
		BitSet paymentTypeOnineBitset = new BitSet();

		for (Order order : orders) {
			int orderId = order.getOrderId();
			if (order.getOrderStatus() == OrderStatus.SHIPPED) {
				orderShippedBitset.set(orderId);
			}

			if (order.getOrderStatus() == OrderStatus.DELIVERED) {
				orderDeliverdBitset.set(orderId);
			}

			if (order.getOrderStatus() == OrderStatus.RETURNED) {
				orderReturnedBitset.set(orderId);
			}

			if (order.getOrderStatus() == OrderStatus.CACNCELLED) {
				orderCancelledBitset.set(orderId);
			}

			if (order.getPaymentType() == PaymentType.CASH) {
				paymentTypeCashBitset.set(orderId);
			}

			if (order.getPaymentType() == PaymentType.ONLINE) {
				paymentTypeOnineBitset.set(orderId);
			}

		}
		
		// Select all the orders of payment_type is CASH
		System.out.println("Orders whose payment type is CASH");
		printOrders(orders, getAllSetBits(paymentTypeCashBitset));
		
		// Select all the orders which are RETURNED or CANCELLED
		System.out.println("\nSelect all the orders which are RETURNED or CANCELLED");
		BitSet result = new BitSet();
        result = (BitSet) orderReturnedBitset.clone(); // Clone to preserve the original bitSet1
        result.or(orderCancelledBitset);
        printOrders(orders, getAllSetBits(result));
		
	
	}

}
