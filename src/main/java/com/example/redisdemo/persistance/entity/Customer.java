package com.example.redisdemo.persistance.entity;

import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends TransactionalEntity<String> {
	private int customerId;
	private String firstName;
	private String lastName;
	private int age;
}

