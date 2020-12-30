package com.carros.domain;
import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@Entity
@Data
public class Carro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String tipo;
	
	private String descricao;
	private String urlfoto;
	private String urlvideo;
	private String latitude;
	private String longitude;
	
}


