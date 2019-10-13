package br.com.adenilsonpr.view;

import java.util.Timer;

import br.com.adenilsonpr.control.config.sheduler.DesativarMateriais;

public class App {

	public static void main(String[] args) {
		Timer agendador = new Timer();
		agendador.schedule(new DesativarMateriais(), 0, 10000);
	}
}
