package com.esteticaAutomotiva.domain.agenda.utils;

import java.time.LocalDate;
import java.util.List;

public class CalcFeriados {
	
	public static List<LocalDate> obterFeriadosNacionais(int ano) {
	    LocalDate pascoa = calcularPascoa(ano);
	    return List.of(
	        LocalDate.of(ano, 1, 1),
	        LocalDate.of(ano, 4, 21),
	        LocalDate.of(ano, 5, 1),
	        LocalDate.of(ano, 9, 7),
	        LocalDate.of(ano, 10, 12),
	        LocalDate.of(ano, 11, 2),
	        LocalDate.of(ano, 11, 15),
	        LocalDate.of(ano, 12, 25),
	        pascoa.minusDays(47), // Carnaval
	        pascoa.minusDays(2),  // Sexta-feira Santa
	        pascoa,               // Domingo de Pascoa
	        pascoa.plusDays(60)   // Corpus Christi
	    );
	}
	
	public static LocalDate calcularPascoa(int ano) {
	    int a = ano % 19;
	    int b = ano / 100;
	    int c = ano % 100;
	    int d = b / 4;
	    int e = b % 4;
	    int f = (b + 8) / 25;
	    int g = (b - f + 1) / 3;
	    int h = (19 * a + b - d - g + 15) % 30;
	    int i = c / 4;
	    int k = c % 4;
	    int l = (32 + 2 * e + 2 * i - h - k) % 7;
	    int m = (a + 11 * h + 22 * l) / 451;
	    int mes = (h + l - 7 * m + 114) / 31;
	    int dia = ((h + l - 7 * m + 114) % 31) + 1;
	    return LocalDate.of(ano, mes, dia);
	}

}
