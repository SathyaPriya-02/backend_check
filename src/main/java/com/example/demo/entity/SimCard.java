package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SimCard {
	
	 @ManyToOne
	    @JoinColumn(name = "email", referencedColumnName = "emailid")
	    private User user; // This associates the SimCard with a User using the email ID

	    @Id
	    @Column(unique = true)
	    private String phonenumber;
	    private String simcardnumber;
	    private String location;
	    private String status;
	    


}
