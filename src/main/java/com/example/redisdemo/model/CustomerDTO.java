package com.example.redisdemo.model;

import com.example.redisdemo.persistance.entity.TransactionalEntity;
import lombok.*;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {
	private int customerId;
	private String firstName;
	private String lastName;
	private int age;
}

