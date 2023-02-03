package com.student.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.entity.Address;
import com.student.repository.AddressRepository;
import com.student.service.AddressService;
import com.student.util.AddressConverter;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	AddressConverter addressConverter;
	
	@Override
	public String createAddress(Address address) {
		String message=null;
		addressRepository.save(address);
		if(address!=null)
		{
			message="New address saved successfully!!";
		}
		else
		{
			message="Address was not saved!!";
		}
		return message;
	}
	

}
