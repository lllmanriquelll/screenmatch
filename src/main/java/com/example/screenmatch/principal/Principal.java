package com.example.screenmatch.principal;

import com.example.screenmatch.model.DadosEpisodio;
import com.example.screenmatch.model.DadosSerie;
import com.example.screenmatch.model.DadosTemporada;
import com.example.screenmatch.service.ConsumoApi;
import com.example.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f48b743";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca:");
        var nomeSerie = leitura.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");
        var json = consumo.obterDados(ENDERECO + nomeSerie + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        //System.out.println(dados);

        //List<DadosTemporada> temporadas = new ArrayList<>();


        for (int i=1; i<=dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
            System.out.println(dadosSerie.titulo() + " - Temporada " + i);
            //dadosTemporada.episodios().forEach(t -> System.out.println(t.numero() + " - " + t.titulo()));
            for (int j=1; j<=dadosTemporada.episodios().size(); j++) {
                json = consumo.obterDados(ENDERECO + nomeSerie + "&season=" + i + "&Episode=" + j + API_KEY);
                DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
                System.out.println(dadosEpisodio.avaliacao());
            }

            //temporadas.add(dadosTemporada);
        }
        //temporadas.forEach(System.out::println);

        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
